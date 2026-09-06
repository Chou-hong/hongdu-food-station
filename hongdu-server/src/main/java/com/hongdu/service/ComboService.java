package com.hongdu.service;

import com.hongdu.dto.ComboDTO;
import com.hongdu.dto.ComboPageQueryDTO;
import com.hongdu.entity.Combo;
import com.hongdu.result.PageResult;
import com.hongdu.vo.ProductItemVO;
import com.hongdu.vo.ComboVO;

import java.util.List;

public interface ComboService {

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     *
     * @param comboDTO
     */
    void saveWithProduct(ComboDTO comboDTO);

    /**
     * 分页查询
     *
     * @param comboPageQueryDTO
     * @return
     */
    PageResult pageQuery(ComboPageQueryDTO comboPageQueryDTO);

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询套餐和关联的菜品数据
     *
     * @param id
     * @return
     */
    ComboVO getByIdWithProduct(Long id);

    /**
     * 修改套餐
     *
     * @param comboDTO
     */
    void update(ComboDTO comboDTO);

    /**
     * 套餐起售、停售
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 条件查询
     * @param combo
     * @return
     */
    List<Combo> list(Combo combo);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<ProductItemVO> getProductItemById(Long id);
}
