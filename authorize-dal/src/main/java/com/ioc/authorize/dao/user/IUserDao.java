package com.ioc.authorize.dao.user;

import com.ioc.authorize.model.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入用户信息
     * @param user 用户
     * @return
     */
    int insert(User user);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated
     */
    int insertSelective(User record);

    /**
     * 根据ID查询用户信息
     * @param id 用户ID
     * @return
     */
     User getUserById(String id);

    /**
     * 根据userNo查询用户信息
     * @param userNo 用户编码
     * @return
     */
     User getUserByUserNo(String userNo);

    /**
     * 按条件筛选出用户列表
     * @param user
     * @return
     */
     List<User> getAllByUser(User user);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(User record);
}