package com.hongdu.mapper;

import com.github.pagehelper.Page;
import com.hongdu.annotation.AutoFill;
import com.hongdu.dto.StaffPageQueryDTO;
import com.hongdu.entity.Staff;
import com.hongdu.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StaffMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from staff where username = #{username}")
    Staff getByUsername(String username);

    /**
     * 插入员工数据
     * @param staff
     */
    @Insert("insert into staff (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user,status) " +
            "values " +
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Staff staff);

    /**
     * 分页查询
     * @param staffPageQueryDTO
     * @return
     */
    Page<Staff> pageQuery(StaffPageQueryDTO staffPageQueryDTO);

    /**
     * 根据主键动态修改属性
     * @param staff
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Staff staff);

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @Select("select * from staff where id = #{id}")
    Staff getById(Long id);
}
