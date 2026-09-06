package com.hongdu.service;

import com.hongdu.dto.PasswordEditDTO;
import com.hongdu.dto.StaffDTO;
import com.hongdu.dto.StaffLoginDTO;
import com.hongdu.dto.StaffPageQueryDTO;
import com.hongdu.entity.Staff;
import com.hongdu.result.PageResult;

public interface StaffService {

    /**
     * 员工登录
     * @param staffLoginDTO
     * @return
     */
    Staff login(StaffLoginDTO staffLoginDTO);

    /**
     * 新增员工
     * @param staffDTO
     */
    void save(StaffDTO staffDTO);

    /**
     * 分页查询
     * @param staffPageQueryDTO
     * @return
     */
    PageResult pageQuery(StaffPageQueryDTO staffPageQueryDTO);

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Staff getById(Long id);

    /**
     * 编辑员工信息
     * @param staffDTO
     */
    void update(StaffDTO staffDTO);

    /**
     * 修改密码
     * @param passwordEditDTO
     */
    void editPassword(PasswordEditDTO passwordEditDTO);
}
