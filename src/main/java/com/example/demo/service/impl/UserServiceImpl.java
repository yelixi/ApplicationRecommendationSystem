package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.dao.UserInfoDao;
import com.example.demo.entity.User;
import com.example.demo.entity.UserInfo;
import com.example.demo.model.SocketConstant;
import com.example.demo.model.UserInformation;
import com.example.demo.param.ChangeForgotPasswordParam;
import com.example.demo.param.ChangePasswordParam;
import com.example.demo.param.UserRegisterParam;
import com.example.demo.service.MailService;
import com.example.demo.service.RedisService;
import com.example.demo.service.UserService;
import com.example.demo.util.SocketClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2021-03-08 23:39:18
 */
@Service("userService")
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
    @Transactional
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
        if (user == null)
            throw new RuntimeException("邮箱不存在");
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
        if (code.isEmpty())
            throw new RuntimeException("此验证码已过期");
        else if (!code.equals(passwordParam.getCode()))
            throw new RuntimeException("验证码错误");
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
        if (code.isEmpty())
            throw new RuntimeException("此验证码已过期");
        else if (!code.equals(param.getCode()))
            throw new RuntimeException("验证码错误");
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
        if (!passwordEncoder.matches(param.getOldPassword(), user.getPassword()))
            throw new RuntimeException("旧密码错误,请重试");
        else if (!param.getNewPassword().equals(param.getConfirmPassword()))
            throw new RuntimeException("确认密码与新密码不同,请重试");
        user.setPassword(passwordEncoder.encode(param.getNewPassword()));
        return this.userDao.update(user) == 1;
    }

    /**
     * 登陆接口
     *
     * @param s 登陆传递的参数
     * @return 构造返回session
     * @throws UsernameNotFoundException 用户不存在则抛出
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userDao.queryByUsername(s);
        String param=null;


        Object obj=SocketClient.socketHandle(SocketConstant.SMART_FILL, param);


        if (user == null) {
            throw new UsernameNotFoundException(s);
        } else if (user.getState() == 0)
            throw new RuntimeException("此用户未激活，请先激活");
        return new UserInformation(user);
    }
}
