package com.ioc.authorize.dao.authority;


import com.ioc.authorize.model.authority.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_permission
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_permission
     *
     * @mbg.generated
     */
    int insert(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_permission
     *
     * @mbg.generated
     */
    int insertSelective(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_permission
     *
     * @mbg.generated
     */
    Permission selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_permission
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_permission
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Permission record);
}