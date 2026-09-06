package com.hongdu.controller.admin;

import com.hongdu.dto.ComboDTO;
import com.hongdu.dto.ComboPageQueryDTO;
import com.hongdu.result.PageResult;
import com.hongdu.result.Result;
import com.hongdu.service.ComboService;
import com.hongdu.vo.ComboVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/admin/combo")
@Tag(name = "套餐相关接口")
@Slf4j
public class ComboController {

    @Autowired
    private ComboService comboService;

    /**
     * 新增套餐
     *
     * @param comboDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "新增套餐")
    @CacheEvict(cacheNames = "comboCache",key = "#comboDTO.categoryId")//key: comboCache::100
    public Result save(@RequestBody ComboDTO comboDTO) {
        comboService.saveWithProduct(comboDTO);
        return Result.success();
    }

    /**
     * 分页查询
     *
     * @param comboPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询")
    public Result<PageResult> page(ComboPageQueryDTO comboPageQueryDTO) {
        PageResult pageResult = comboService.pageQuery(comboPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @Operation(summary = "批量删除套餐")
    @CacheEvict(cacheNames = "comboCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        comboService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据id查询套餐，用于修改页面回显数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询套餐")
    public Result<ComboVO> getById(@PathVariable Long id) {
        ComboVO comboVO = comboService.getByIdWithProduct(id);
        return Result.success(comboVO);
    }

    /**
     * 修改套餐
     *
     * @param comboDTO
     * @return
     */
    @PutMapping
    @Operation(summary = "修改套餐")
    @CacheEvict(cacheNames = "comboCache",allEntries = true)
    public Result update(@RequestBody ComboDTO comboDTO) {
        comboService.update(comboDTO);
        return Result.success();
    }

    /**
     * 套餐起售停售
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "套餐起售停售")
    @CacheEvict(cacheNames = "comboCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status, Long id) {
        comboService.startOrStop(status, id);
        return Result.success();
    }
}
