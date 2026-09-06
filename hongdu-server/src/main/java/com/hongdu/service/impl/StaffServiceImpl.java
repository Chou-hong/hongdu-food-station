package com.hongdu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hongdu.constant.MessageConstant;
import com.hongdu.constant.PasswordConstant;
import com.hongdu.constant.StatusConstant;
import com.hongdu.context.BaseContext;
import com.hongdu.dto.PasswordEditDTO;
import com.hongdu.dto.StaffDTO;
import com.hongdu.dto.StaffLoginDTO;
import com.hongdu.dto.StaffPageQueryDTO;
import com.hongdu.entity.Staff;
import com.hongdu.exception.AccountLockedException;
import com.hongdu.exception.AccountNotFoundException;
import com.hongdu.exception.PasswordErrorException;
import com.hongdu.mapper.StaffMapper;
import com.hongdu.result.PageResult;
import com.hongdu.service.StaffService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffMapper staffMapper;

    /**
     * 员工登录
     *
     * @param staffLoginDTO
     * @return
     */
    public Staff login(StaffLoginDTO staffLoginDTO) {
        String username = staffLoginDTO.getUsername();
        String password = staffLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Staff staff = staffMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (staff == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //对前端传过来的明文密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(staff.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (staff.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return staff;
    }

    /**
     * 新增员工
     *
     * @param staffDTO
     */
    public void save(StaffDTO staffDTO) {
        Staff staff = new Staff();

        //对象属性拷贝
        BeanUtils.copyProperties(staffDTO, staff);

        //设置账号的状态，默认正常状态 1表示正常 0表示锁定
        staff.setStatus(StatusConstant.ENABLE);

        //设置密码，默认密码123456
        staff.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //设置当前记录的创建时间和修改时间
        //staff.setCreateTime(LocalDateTime.now());
        //staff.setUpdateTime(LocalDateTime.now());

        //设置当前记录创建人id和修改人id
        //staff.setCreateUser(BaseContext.getCurrentId());
        //staff.setUpdateUser(BaseContext.getCurrentId());

        staffMapper.insert(staff);
    }

    /**
     * 分页查询
     *
     * @param staffPageQueryDTO
     * @return
     */
    public PageResult pageQuery(StaffPageQueryDTO staffPageQueryDTO) {
        // select * from staff limit 0,10
        //开始分页查询
        PageHelper.startPage(staffPageQueryDTO.getPage(), staffPageQueryDTO.getPageSize());

        Page<Staff> page = staffMapper.pageQuery(staffPageQueryDTO);

        long total = page.getTotal();
        List<Staff> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * 启用禁用员工账号
     *
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        // update staff set status = ? where id = ?

        /*Staff staff = new Staff();
        staff.setStatus(status);
        staff.setId(id);*/

        Staff staff = Staff.builder()
                .status(status)
                .id(id)
                .build();

        staffMapper.update(staff);
    }

    /**
     * 根据id查询员工
     *
     * @param id
     * @return
     */
    public Staff getById(Long id) {
        Staff staff = staffMapper.getById(id);
        staff.setPassword("****");
        return staff;
    }

    /**
     * 编辑员工信息
     *
     * @param staffDTO
     */
    public void update(StaffDTO staffDTO) {
        Staff staff = new Staff();
        BeanUtils.copyProperties(staffDTO, staff);

        //staff.setUpdateTime(LocalDateTime.now());
        //staff.setUpdateUser(BaseContext.getCurrentId());

        staffMapper.update(staff);
    }

    /**
     * 修改密码
     * @param passwordEditDTO
     */
    public void editPassword(PasswordEditDTO passwordEditDTO) {
        Long empId = BaseContext.getCurrentId();
        passwordEditDTO.setEmpId(empId);

        Staff staff = staffMapper.getById(empId);
        String oldPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getOldPassword().getBytes());
        if (!staff.getPassword().equals(oldPassword)) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_EDIT_FAILED);
        }

        String newPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getNewPassword().getBytes());
        staff.setPassword(newPassword);
        staffMapper.update(staff);
    }
}