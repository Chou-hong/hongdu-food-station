package com.hongdu.controller.admin;

import com.hongdu.constant.JwtClaimsConstant;
import com.hongdu.dto.PasswordEditDTO;
import com.hongdu.dto.StaffDTO;
import com.hongdu.dto.StaffLoginDTO;
import com.hongdu.dto.StaffPageQueryDTO;
import com.hongdu.entity.Staff;
import com.hongdu.properties.JwtProperties;
import com.hongdu.result.PageResult;
import com.hongdu.result.Result;
import com.hongdu.service.StaffService;
import com.hongdu.utils.JwtUtil;
import com.hongdu.vo.StaffLoginVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/staff")
@Slf4j
@Tag(name = "员工相关接口")
public class StaffController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param staffLoginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "员工登录")
    public Result<StaffLoginVO> login(@RequestBody StaffLoginDTO staffLoginDTO) {
        log.info("员工登录：{}", staffLoginDTO);

        Staff staff = staffService.login(staffLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, staff.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        StaffLoginVO staffLoginVO = StaffLoginVO.builder()
                .id(staff.getId())
                .userName(staff.getUsername())
                .name(staff.getName())
                .token(token)
                .build();

        return Result.success(staffLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @Operation(summary = "员工退出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * @param staffDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "新增员工")
    public Result save(@RequestBody StaffDTO staffDTO){
        log.info("新增员工：{}",staffDTO);
        staffService.save(staffDTO);
        return Result.success();
    }

    /**
     * 员工分页查询
     * @param staffPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "员工分页查询")
    public Result<PageResult> page(StaffPageQueryDTO staffPageQueryDTO){
        log.info("员工分页查询，参数为：{}", staffPageQueryDTO);
        PageResult pageResult = staffService.pageQuery(staffPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启用禁用员工账号")
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("启用禁用员工账号：{},{}",status,id);
        staffService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询员工信息")
    public Result<Staff> getById(@PathVariable Long id){
        Staff staff = staffService.getById(id);
        return Result.success(staff);
    }

    /**
     * 编辑员工信息
     * @param staffDTO
     * @return
     */
    @PutMapping
    @Operation(summary = "编辑员工信息")
    public Result update(@RequestBody StaffDTO staffDTO){
        log.info("编辑员工信息：{}", staffDTO);
        staffService.update(staffDTO);
        return Result.success();
    }

    /**
     * 修改密码
     * @param passwordEditDTO
     * @return
     */
    @PutMapping("/editPassword")
    @Operation(summary = "修改密码")
    public Result editPassword(@RequestBody PasswordEditDTO passwordEditDTO){
        log.info("员工修改密码：{}", passwordEditDTO);
        staffService.editPassword(passwordEditDTO);
        return Result.success();
    }
}