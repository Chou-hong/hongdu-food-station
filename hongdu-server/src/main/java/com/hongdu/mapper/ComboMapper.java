package com.hongdu.mapper;

import com.github.pagehelper.Page;
import com.hongdu.annotation.AutoFill;
import com.hongdu.dto.ComboPageQueryDTO;
import com.hongdu.entity.Combo;
import com.hongdu.enumeration.OperationType;
import com.hongdu.vo.ProductItemVO;
import com.hongdu.vo.ComboVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ComboMapper {

    /**
     * 根据分类id查询套餐的数量
     *
     * @param id
     * @return
     */
    @Select("select count(id) from combo where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 根据id修改套餐
     *
     * @param combo
     */
    @AutoFill(OperationType.UPDATE)
    void update(Combo combo);

    /**
     * 新增套餐
     *
     * @param combo
     */
    @AutoFill(OperationType.INSERT)
    void insert(Combo combo);

    /**
     * 分页查询
     * @param comboPageQueryDTO
     * @return
     */
    Page<ComboVO> pageQuery(ComboPageQueryDTO comboPageQueryDTO);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Select("select * from combo where id = #{id}")
    Combo getById(Long id);

    /**
     * 根据id删除套餐
     * @param comboId
     */
    @Delete("delete from combo where id = #{id}")
    void deleteById(Long comboId);

    /**
     * 根据id查询套餐和套餐菜品关系
     * @param id
     * @return
     */
    ComboVO getByIdWithProduct(Long id);

    /**
     * 动态条件查询套餐
     * @param combo
     * @return
     */
    List<Combo> list(Combo combo);

    /**
     * 根据套餐id查询菜品选项
     * @param comboId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from combo_item sd left join product d on sd.product_id = d.id " +
            "where sd.combo_id = #{comboId}")
    List<ProductItemVO> getProductItemByComboId(Long comboId);

    /**
     * 根据条件统计套餐数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
