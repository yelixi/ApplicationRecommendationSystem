package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.model.UserInformation;
import com.example.demo.param.ChangeForgotPasswordParam;
import com.example.demo.param.UserRegisterParam;
import com.example.demo.service.MailService;
import com.example.demo.service.RedisService;
import com.example.demo.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
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

    private final Integer EXPIRE_DATE = 10*60*60;

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
     *
     * @param userRegisterParam 封装的注册参数
     * @return 是否成功
     */
    @Override
    public boolean register(UserRegisterParam userRegisterParam) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterParam, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("0");
        return this.userDao.insert(user) == 1;
    }


    /**
     * 忘记密码接口
     * @param email 验证的邮箱
     * @return 是否成功
     */
    @Override
    public boolean forgotPassword(String email) {
        User user = userDao.queryByEmail(email);
        if(user==null)
            throw new RuntimeException("邮箱不存在");
        String code = RandomStringUtils.randomNumeric( 6 );
        redisService.set("code",code,EXPIRE_DATE);
        String text = "您的验证码为"+code+"有效时间为"+EXPIRE_DATE/(60*60)+"如非本人操作请忽略";
        mailService.sendMail(email,text);
        return true;
    }

    /**
     * 验证忘记密码接口
     * @param passwordParam 验证忘记密码表单
     * @return 是否成功
     */
    @Override
    public boolean changeForgotPassword(ChangeForgotPasswordParam passwordParam) {
        String code = (String) redisService.get("code");
        if (code.isEmpty())
            throw new RuntimeException("此验证码已过期");
        else if (!code.equals(passwordParam.getCode()))
            throw new RuntimeException("验证码错误");
        User user = userDao.queryByEmail(passwordParam.getEmail());
        user.setPassword(passwordEncoder.encode(passwordParam.getPassword()));
        return userDao.update(user) == 1;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userDao.queryByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new UserInformation(user);
    }
}
