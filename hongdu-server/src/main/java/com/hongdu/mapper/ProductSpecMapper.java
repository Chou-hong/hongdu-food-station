package com.hongdu.mapper;

import com.hongdu.entity.ProductSpec;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductSpecMapper {
    /**
     * 批量插入口味数据
     * @param flavors
     */
    void insertBatch(List<ProductSpec> flavors);

    /**
     * 根据菜品id删除对应的口味数据
     * @param productId
     */
    @Delete("delete from product_spec where product_id = #{productId}")
    void deleteByProductId(Long productId);

    /**
     * 根据菜品id查询对应的口味数据
     * @param productId
     * @return
     */
    @Select("select * from product_spec where product_id = #{productId}")
    List<ProductSpec> getByProductId(Long productId);
}
