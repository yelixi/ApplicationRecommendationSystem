package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.Vo.UserFocusVo;
import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.model.UserInformation;
import com.example.demo.param.ChangeForgotPasswordParam;
import com.example.demo.param.ChangePasswordParam;
import com.example.demo.param.UserRegisterParam;
import com.example.demo.service.MailService;
import com.example.demo.service.RedisService;
import com.example.demo.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Entity;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2021-03-08 23:39:18
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService , UserDetailsService {
    @Resource
    private UserDao userDao;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisService redisService;

    @Resource
    private MailService mailService;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private CollegesFocusMapper collegesFocusMapper;

    @Resource
    private ProfessionsFocusMapper professionsFocusMapper;

    @Resource
    private PapersFocusMapper papersFocusMapper;

    private final Integer EXPIRE_DATE = 10 * 60 * 60;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    @Override
    public User queryByOpenId(String openId) {
        return userDao.queryByOpenId(openId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }


    /**
     * @param userRegisterParam 封装的注册参数
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean register(UserRegisterParam userRegisterParam) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterParam, user);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("0");
        user.setState(0);
        this.userDao.insert(user);

        //创建用户信息并存入数据库
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(this.userDao.queryByEmail(user.getEmail()).getId());
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        boolean result = userInfoDao.insert(userInfo) == 1;

        //生成验证码
        String code = RandomStringUtils.randomNumeric(6);
        //生成唯一key索引
        String key = userRegisterParam.getUsername() + "_" + userRegisterParam.getEmail();
        //将验证码存入redis
        redisService.set(key, code, EXPIRE_DATE);
        String text = userRegisterParam.getUsername() + "您好," + "您的验证码为" + code + ",有效时间为" + EXPIRE_DATE / (60 * 60) + "分钟,如非本人操作请忽略";
        String subject = "【高考智能推荐系统】验证用户";
        //发送验证码
        mailService.sendMail(subject, userRegisterParam.getEmail(), text);
        return result;
    }


    /**
     * 忘记密码接口
     *
     * @param email 验证的邮箱
     * @return 是否成功
     */
    @Override
    public boolean forgotPassword(String email) {
        User user = userDao.queryByEmail(email);
        if (user == null) {
            throw new RuntimeException("邮箱不存在");
        }
        String code = RandomStringUtils.randomNumeric(6);
        String key = user.getUsername() + "_" + user.getEmail();
        redisService.set(key, code, EXPIRE_DATE);
        String text = user.getUsername() + "您好," + "您的验证码为" + code + ",有效时间为" + EXPIRE_DATE / (60 * 60) + "分钟,如非本人操作请忽略";
        String subject = "【高考智能推荐系统】忘记密码";
        mailService.sendMail(subject, email, text);
        return true;
    }

    /**
     * 验证忘记密码接口
     *
     * @param passwordParam 验证忘记密码表单
     * @return 是否成功
     */
    @Override
    public boolean changeForgotPassword(ChangeForgotPasswordParam passwordParam) {
        User user = userDao.queryByEmail(passwordParam.getEmail());
        //生成唯一key索引
        String key = user.getUsername() + "_" + user.getEmail();
        String code = (String) redisService.get(key);
        if (code.isEmpty()) {
            throw new RuntimeException("此验证码已过期");
        } else if (!code.equals(passwordParam.getCode())) {
            throw new RuntimeException("验证码错误");
        }
        user.setPassword(passwordEncoder.encode(passwordParam.getPassword()));
        return userDao.update(user) == 1;
    }

    /**
     * 解除未验证的状态的接口
     *
     * @param param 注册表单
     * @return 是否成功
     */
    @Override
    public boolean unlock(UserRegisterParam param) {
        //生成唯一key索引
        String key = param.getUsername() + "_" + param.getEmail();
        String code = (String) redisService.get(key);
        if (code.isEmpty()) {
            throw new RuntimeException("此验证码已过期");
        } else if (!code.equals(param.getCode())) {
            throw new RuntimeException("验证码错误");
        }
        User user = userDao.queryByEmail(param.getEmail());
        user.setState(1);
        return userDao.update(user) == 1;
    }

    /**
     * 更新密码
     *
     * @param param 更新密码实体类
     * @param userInformation 用户信息
     * @return 是否成功
     */
    @Override
    public boolean changePassword(ChangePasswordParam param, UserInformation userInformation) {
        User user = this.userDao.queryById(userInformation.getId());
        if (!passwordEncoder.matches(param.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("旧密码错误,请重试");
        } else if (!param.getNewPassword().equals(param.getConfirmPassword())) {
            throw new RuntimeException("确认密码与新密码不同,请重试");
        }
        user.setPassword(passwordEncoder.encode(param.getNewPassword()));
        return this.userDao.update(user) == 1;
    }

    @Override
    public UserFocusVo getFocusInfo(String openId) {
        List<CollegesFocus> collegesFocusList = collegesFocusMapper.findAll(openId);
        List<ProfessionsFocus> professionsFocusList = professionsFocusMapper.findAll(openId);
        List<PapersFocus> papersFocusList = papersFocusMapper.findAll(openId);
        List<Colleges> colleges = new ArrayList<>();
        List<Professions> professions = new ArrayList<>();
        List<Papers> papers = new ArrayList<>();
        for(CollegesFocus c:collegesFocusList){
            Colleges college = new Colleges();
            college.setId(c.getId());
            college.setName(c.getColleges());
            college.setOpenId(c.getOpenId().toString());
            college.setCompetentDepartment("教育局");
            college.setMinimumRank(10000);
            college.setMinimumScore(600);
            college.setProvince("江苏");
            college.setSchoolCord("10001");
            college.setUneven("双一流");
            colleges.add(college);
        }
        for(ProfessionsFocus p:professionsFocusList){
            Professions profession = new Professions();
            profession.setId(p.getId());
            profession.setOpenId(p.getOpenId().toString());
            profession.setName(p.getProfessions());
            profession.setMajorIntroduction("专业的具体介绍");
            professions.add(profession);
        }
        for(PapersFocus p:papersFocusList){
            Papers paper = new Papers();
            paper.setId(p.getId());
            paper.setName(p.getPapers());
            paper.setDetail("高考资讯");
            paper.setPublishTime(new Date());
            paper.setReadTimes(3);
            paper.setThumbUpTimes(3);
            paper.setDetail("小标题");
            paper.setSource("https://gaokao.chsi.com.cn/wap/zxdy/zxs");
            paper.setOpenId(p.getOpenId().toString());
            papers.add(paper);
        }
        UserFocusVo u = new UserFocusVo();
        u.setOpenId(openId);
        u.setColleges(colleges);
        u.setPapers(papers);
        u.setProfessions(professions);
        return u;
    }

    @Override
    public User updateByOpenId(User user) {
        User u = userDao.queryByOpenId(user.getOpenId());
        user.setId(u.getId());
        userDao.update(user);
        return userDao.queryById(user.getId());
    }

    /**
     * 登陆接口
     *
     * @param s 登陆传递的参数
     * @return 构造返回session
     * @throws UsernameNotFoundException 用户不存在则抛出
     */
    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=wx1a18d6da107f66e4";//自己的appid
        url += "&secret=bc3b4be5eb3382b44d8603b8e988b0b9";//自己的appSecret
        url += "&js_code=" + s;
        url += "&grant_type=authorization_code";
        url += "&connect_redirect=1";
        //String url = "http://127.0.0.1:8080/userInfo/findInfo?userId=7";
        String res;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);    //GET方式
        log.warn("请求成功");
        CloseableHttpResponse response;
        // 配置信息
        RequestConfig requestConfig = RequestConfig.custom()          // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)                    // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)             // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)                    // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(false).build();           // 将上面的配置信息 运用到这个Get请求里
        httpget.setConfig(requestConfig);                         // 由客户端执行(发送)Get请求
        response = httpClient.execute(httpget);                   // 从响应模型中获取响应实体
        log.warn("返回成功");
        //HttpEntity responseEntity = (HttpEntity) response.getEntity();
        log.warn("响应状态为:" + response.getStatusLine());
        HttpEntity entity = response.getEntity();
        res = EntityUtils.toString(entity);
        //System.out.println("响应内容长度为:" + responseEntity.getContentLength());
        log.warn("响应内容为:" + res);
        // 释放资源
        httpClient.close();
        response.close();
        JSONObject jo = JSON.parseObject(res);
        String openId = jo.getString("openid");
        //String openId = "2";
        User user = this.userDao.queryByOpenId(openId);
        //String param=null;


        //Object obj=SocketClient.socketHandle(SocketConstant.SMART_FILL, param);


        if (user == null&&openId!=null) {
            User user1 = new User();
            user1.setOpenId(openId);
            user1.setUsername("wx"+LocalDateTime.now().toString());
            user1.setPassword(passwordEncoder.encode(""));
            user1.setRole("0");
            user1.setState(1);
            userDao.insert(user1);
            return  new UserInformation(user1);
        } else {
            assert user != null;
            if (user.getState() == 0) {
                throw new RuntimeException("此用户未激活，请先激活");
            }
        }
        return new UserInformation(user);
    }
}