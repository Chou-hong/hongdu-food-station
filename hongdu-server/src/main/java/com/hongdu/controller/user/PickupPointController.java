package com.hongdu.controller.user;

import com.hongdu.context.BaseContext;
import com.hongdu.entity.PickupPoint;
import com.hongdu.result.Result;
import com.hongdu.service.PickupPointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user/pickupPoint")
@Tag(name = "C端地址簿接口")
public class PickupPointController {

    @Autowired
    private PickupPointService pickupPointService;

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "查询当前登录用户的所有地址信息")
    public Result<List<PickupPoint>> list() {
        PickupPoint pickupPoint = new PickupPoint();
        pickupPoint.setUserId(BaseContext.getCurrentId());
        List<PickupPoint> list = pickupPointService.list(pickupPoint);
        return Result.success(list);
    }

    /**
     * 新增地址
     *
     * @param pickupPoint
     * @return
     */
    @PostMapping
    @Operation(summary = "新增地址")
    public Result save(@RequestBody PickupPoint pickupPoint) {
        pickupPointService.save(pickupPoint);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询地址")
    public Result<PickupPoint> getById(@PathVariable Long id) {
        PickupPoint pickupPoint = pickupPointService.getById(id);
        return Result.success(pickupPoint);
    }

    /**
     * 根据id修改地址
     *
     * @param pickupPoint
     * @return
     */
    @PutMapping
    @Operation(summary = "根据id修改地址")
    public Result update(@RequestBody PickupPoint pickupPoint) {
        pickupPointService.update(pickupPoint);
        return Result.success();
    }

    /**
     * 设置默认地址
     *
     * @param pickupPoint
     * @return
     */
    @PutMapping("/default")
    @Operation(summary = "设置默认地址")
    public Result setDefault(@RequestBody PickupPoint pickupPoint) {
        pickupPointService.setDefault(pickupPoint);
        return Result.success();
    }

    /**
     * 根据id删除地址
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @Operation(summary = "根据id删除地址")
    public Result deleteById(Long id) {
        pickupPointService.deleteById(id);
        return Result.success();
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    @Operation(summary = "查询默认地址")
    public Result<PickupPoint> getDefault() {
        //SQL:select * from pickup_point where user_id = ? and is_default = 1
        PickupPoint pickupPoint = new PickupPoint();
        pickupPoint.setIsDefault(1);
        pickupPoint.setUserId(BaseContext.getCurrentId());
        List<PickupPoint> list = pickupPointService.list(pickupPoint);

        if (list != null && list.size() == 1) {
            return Result.success(list.get(0));
        }

        return Result.error("没有查询到默认地址");
    }

}
