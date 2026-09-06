package com.hongdu.service;

import com.hongdu.dto.ProductDTO;
import com.hongdu.dto.ProductPageQueryDTO;
import com.hongdu.entity.Product;
import com.hongdu.result.PageResult;
import com.hongdu.vo.ProductVO;

import java.util.List;

public interface ProductService {

    /**
     * 新增菜品和对应的口味
     *
     * @param productDTO
     */
    public void saveWithFlavor(ProductDTO productDTO);

    /**
     * 菜品分页查询
     *
     * @param productPageQueryDTO
     * @return
     */
    PageResult pageQuery(ProductPageQueryDTO productPageQueryDTO);

    /**
     * 菜品批量删除
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询菜品和对应的口味数据
     *
     * @param id
     * @return
     */
    ProductVO getByIdWithFlavor(Long id);

    /**
     * 根据id修改菜品基本信息和对应的口味信息
     *
     * @param productDTO
     */
    void updateWithFlavor(ProductDTO productDTO);

    /**
     * 菜品起售停售
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    List<Product> list(Long categoryId);

    /**
     * 条件查询菜品和口味
     * @param product
     * @return
     */
    List<ProductVO> listWithFlavor(Product product);
}
