package com.hongdu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hongdu.constant.MessageConstant;
import com.hongdu.constant.StatusConstant;
import com.hongdu.dto.ComboDTO;
import com.hongdu.dto.ComboPageQueryDTO;
import com.hongdu.entity.Product;
import com.hongdu.entity.Combo;
import com.hongdu.entity.ComboItem;
import com.hongdu.exception.DeletionNotAllowedException;
import com.hongdu.exception.ComboEnableFailedException;
import com.hongdu.mapper.ProductMapper;
import com.hongdu.mapper.ComboItemMapper;
import com.hongdu.mapper.ComboMapper;
import com.hongdu.result.PageResult;
import com.hongdu.service.ComboService;
import com.hongdu.vo.ProductItemVO;
import com.hongdu.vo.ComboVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 套餐业务实现
 */
@Service
@Slf4j
public class ComboServiceImpl implements ComboService {

    @Autowired
    private ComboMapper comboMapper;
    @Autowired
    private ComboItemMapper comboItemMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     *
     * @param comboDTO
     */
    @Transactional
    public void saveWithProduct(ComboDTO comboDTO) {
        Combo combo = new Combo();
        BeanUtils.copyProperties(comboDTO, combo);

        //向套餐表插入数据
        comboMapper.insert(combo);

        //获取生成的套餐id
        Long comboId = combo.getId();

        List<ComboItem> comboItemes = comboDTO.getComboItemes();
        comboItemes.forEach(comboItem -> {
            comboItem.setComboId(comboId);
        });

        //保存套餐和菜品的关联关系
        comboItemMapper.insertBatch(comboItemes);
    }

    /**
     * 分页查询
     *
     * @param comboPageQueryDTO
     * @return
     */
    public PageResult pageQuery(ComboPageQueryDTO comboPageQueryDTO) {
        int pageNum = comboPageQueryDTO.getPage();
        int pageSize = comboPageQueryDTO.getPageSize();

        PageHelper.startPage(pageNum, pageSize);
        Page<ComboVO> page = comboMapper.pageQuery(comboPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {
        ids.forEach(id -> {
            Combo combo = comboMapper.getById(id);
            if (StatusConstant.ENABLE == combo.getStatus()) {
                //起售中的套餐不能删除
                throw new DeletionNotAllowedException(MessageConstant.COMBO_ON_SALE);
            }
        });

        ids.forEach(comboId -> {
            //删除套餐表中的数据
            comboMapper.deleteById(comboId);
            //删除套餐菜品关系表中的数据
            comboItemMapper.deleteByComboId(comboId);
        });
    }

    /**
     * 根据id查询套餐和套餐菜品关系
     *
     * @param id
     * @return
     */
    public ComboVO getByIdWithProduct(Long id) {
        ComboVO comboVO = comboMapper.getByIdWithProduct(id);
        return comboVO;
    }

    /**
     * 修改套餐
     *
     * @param comboDTO
     */
    @Transactional
    public void update(ComboDTO comboDTO) {
        Combo combo = new Combo();
        BeanUtils.copyProperties(comboDTO, combo);

        //1、修改套餐表，执行update
        comboMapper.update(combo);

        //套餐id
        Long comboId = comboDTO.getId();

        //2、删除套餐和菜品的关联关系，操作combo_item表，执行delete
        comboItemMapper.deleteByComboId(comboId);

        List<ComboItem> comboItemes = comboDTO.getComboItemes();
        comboItemes.forEach(comboItem -> {
            comboItem.setComboId(comboId);
        });
        //3、重新插入套餐和菜品的关联关系，操作combo_item表，执行insert
        comboItemMapper.insertBatch(comboItemes);
    }

    /**
     * 套餐起售、停售
     *
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        //起售套餐时，判断套餐内是否有停售菜品，有停售菜品提示"套餐内包含未启售菜品，无法启售"
        if (status == StatusConstant.ENABLE) {
            //select a.* from product a left join combo_item b on a.id = b.product_id where b.combo_id = ?
            List<Product> productList = productMapper.getByComboId(id);
            if (productList != null && productList.size() > 0) {
                productList.forEach(product -> {
                    if (StatusConstant.DISABLE == product.getStatus()) {
                        throw new ComboEnableFailedException(MessageConstant.COMBO_ENABLE_FAILED);
                    }
                });
            }
        }

        Combo combo = Combo.builder()
                .id(id)
                .status(status)
                .build();
        comboMapper.update(combo);
    }

    /**
     * 条件查询
     * @param combo
     * @return
     */
    public List<Combo> list(Combo combo) {
        List<Combo> list = comboMapper.list(combo);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<ProductItemVO> getProductItemById(Long id) {
        return comboMapper.getProductItemByComboId(id);
    }
}
