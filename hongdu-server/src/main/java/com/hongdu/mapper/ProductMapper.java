package com.hongdu.mapper;

import com.github.pagehelper.Page;
import com.hongdu.annotation.AutoFill;
import com.hongdu.dto.ProductPageQueryDTO;
import com.hongdu.entity.Product;
import com.hongdu.enumeration.OperationType;
import com.hongdu.vo.ProductVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from product where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品数据
     *
     * @param product
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Product product);

    /**
     * 菜品分页查询
     *
     * @param productPageQueryDTO
     * @return
     */
    Page<ProductVO> pageQuery(ProductPageQueryDTO productPageQueryDTO);

    /**
     * 根据主键查询菜品
     *
     * @param id
     * @return
     */
    @Select("select * from product where id = #{id}")
    Product getById(Long id);

    /**
     * 根据主键删除菜品数据
     *
     * @param id
     */
    @Delete("delete from product where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据id动态修改菜品数据
     *
     * @param product
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Product product);

    /**
     * 动态条件查询菜品
     *
     * @param product
     * @return
     */
    List<Product> list(Product product);

    /**
     * 根据套餐id查询菜品
     * @param comboId
     * @return
     */
    @Select("select a.* from product a left join combo_item b on a.id = b.product_id where b.combo_id = #{comboId}")
    List<Product> getByComboId(Long comboId);

    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
