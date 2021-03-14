package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.param.ChangeForgotPasswordParam;
import com.example.demo.param.UserRegisterParam;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2021-03-08 23:39:18
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * @param userRegisterParam 封装的注册参数
     * @return 是否成功
     */
    boolean register(UserRegisterParam userRegisterParam);

    /**
     * 忘记密码接口
     *
     * @param email 验证的邮箱
     * @return 是否成功
     */
    boolean forgotPassword(String email);

    /**
     * 验证忘记密码接口
     *
     * @param passwordParam 验证忘记密码表单
     * @return 是否成功
     */
    boolean changeForgotPassword(ChangeForgotPasswordParam passwordParam);

    /**
     * 解除未验证的状态的接口
     *
     * @param param 注册表单
     * @return 是否成功
     */
    boolean unlock(UserRegisterParam param);
}
