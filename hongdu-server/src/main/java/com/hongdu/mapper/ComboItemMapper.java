package com.hongdu.mapper;

import com.hongdu.entity.ComboItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ComboItemMapper {
    /**
     * 根据菜品id查询对应的套餐id
     *
     * @param productIds
     * @return
     */
    //select combo_id from combo_item where product_id in (1,2,3,4)
    List<Long> getComboIdsByProductIds(List<Long> productIds);

    /**
     * 批量保存套餐和菜品的关联关系
     *
     * @param comboItemes
     */
    void insertBatch(List<ComboItem> comboItemes);

    /**
     * 根据套餐id删除套餐和菜品的关联关系
     *
     * @param comboId
     */
    @Delete("delete from combo_item where combo_id = #{comboId}")
    void deleteByComboId(Long comboId);
}
