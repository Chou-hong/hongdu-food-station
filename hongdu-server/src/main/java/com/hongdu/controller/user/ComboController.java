package com.hongdu.controller.user;

import com.hongdu.constant.StatusConstant;
import com.hongdu.entity.Combo;
import com.hongdu.result.Result;
import com.hongdu.service.ComboService;
import com.hongdu.vo.ProductItemVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userComboController")
@RequestMapping("/user/combo")
@Tag(name = "C端-套餐浏览接口")
public class ComboController {
    @Autowired
    private ComboService comboService;

    /**
     * 条件查询
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "根据分类id查询套餐")
    @Cacheable(cacheNames = "comboCache",key = "#categoryId") //key: comboCache::100
    public Result<List<Combo>> list(Long categoryId) {
        Combo combo = new Combo();
        combo.setCategoryId(categoryId);
        combo.setStatus(StatusConstant.ENABLE);

        List<Combo> list = comboService.list(combo);
        return Result.success(list);
    }

    /**
     * 根据套餐id查询包含的菜品列表
     *
     * @param id
     * @return
     */
    @GetMapping("/product/{id}")
    @Operation(summary = "根据套餐id查询包含的菜品列表")
    public Result<List<ProductItemVO>> productList(@PathVariable("id") Long id) {
        List<ProductItemVO> list = comboService.getProductItemById(id);
        return Result.success(list);
    }
}
