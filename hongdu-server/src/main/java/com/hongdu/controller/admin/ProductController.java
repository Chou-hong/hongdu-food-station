package com.hongdu.controller.admin;

import com.hongdu.dto.ProductDTO;
import com.hongdu.dto.ProductPageQueryDTO;
import com.hongdu.entity.Product;
import com.hongdu.result.PageResult;
import com.hongdu.result.Result;
import com.hongdu.service.ProductService;
import com.hongdu.vo.ProductVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/admin/product")
@Tag(name = "菜品相关接口")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     *
     * @param productDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "新增菜品")
    public Result save(@RequestBody ProductDTO productDTO) {
        log.info("新增菜品：{}", productDTO);
        productService.saveWithFlavor(productDTO);

        //清理缓存数据
        String key = "product_" + productDTO.getCategoryId();
        cleanCache(key);
        return Result.success();
    }

    /**
     * 菜品分页查询
     *
     * @param productPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "菜品分页查询")
    public Result<PageResult> page(ProductPageQueryDTO productPageQueryDTO) {
        log.info("菜品分页查询:{}", productPageQueryDTO);
        PageResult pageResult = productService.pageQuery(productPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 菜品批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @Operation(summary = "菜品批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("菜品批量删除：{}", ids);
        productService.deleteBatch(ids);

        //将所有的菜品缓存数据清理掉，所有以product_开头的key
        cleanCache("product_*");

        return Result.success();
    }

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询菜品")
    public Result<ProductVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品：{}", id);
        ProductVO productVO = productService.getByIdWithFlavor(id);
        return Result.success(productVO);
    }

    /**
     * 修改菜品
     *
     * @param productDTO
     * @return
     */
    @PutMapping
    @Operation(summary = "修改菜品")
    public Result update(@RequestBody ProductDTO productDTO) {
        log.info("修改菜品：{}", productDTO);
        productService.updateWithFlavor(productDTO);

        //将所有的菜品缓存数据清理掉，所有以product_开头的key
        cleanCache("product_*");

        return Result.success();
    }

    /**
     * 菜品起售停售
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "菜品起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        productService.startOrStop(status, id);

        //将所有的菜品缓存数据清理掉，所有以product_开头的key
        cleanCache("product_*");

        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "根据分类id查询菜品")
    public Result<List<Product>> list(Long categoryId) {
        List<Product> list = productService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 清理缓存数据
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
