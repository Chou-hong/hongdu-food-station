package com.hongdu.service.impl;

import com.hongdu.context.BaseContext;
import com.hongdu.entity.PickupPoint;
import com.hongdu.mapper.PickupPointMapper;
import com.hongdu.service.PickupPointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
public class PickupPointServiceImpl implements PickupPointService {
    @Autowired
    private PickupPointMapper pickupPointMapper;

    /**
     * 条件查询
     *
     * @param pickupPoint
     * @return
     */
    public List<PickupPoint> list(PickupPoint pickupPoint) {
        return pickupPointMapper.list(pickupPoint);
    }

    /**
     * 新增地址
     *
     * @param pickupPoint
     */
    public void save(PickupPoint pickupPoint) {
        pickupPoint.setUserId(BaseContext.getCurrentId());
        pickupPoint.setIsDefault(0);
        pickupPointMapper.insert(pickupPoint);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public PickupPoint getById(Long id) {
        PickupPoint pickupPoint = pickupPointMapper.getById(id);
        return pickupPoint;
    }

    /**
     * 根据id修改地址
     *
     * @param pickupPoint
     */
    public void update(PickupPoint pickupPoint) {
        pickupPointMapper.update(pickupPoint);
    }

    /**
     * 设置默认地址
     *
     * @param pickupPoint
     */
    @Transactional
    public void setDefault(PickupPoint pickupPoint) {
        //1、将当前用户的所有地址修改为非默认地址 update pickup_point set is_default = ? where user_id = ?
        pickupPoint.setIsDefault(0);
        pickupPoint.setUserId(BaseContext.getCurrentId());
        pickupPointMapper.updateIsDefaultByUserId(pickupPoint);

        //2、将当前地址改为默认地址 update pickup_point set is_default = ? where id = ?
        pickupPoint.setIsDefault(1);
        pickupPointMapper.update(pickupPoint);
    }

    /**
     * 根据id删除地址
     *
     * @param id
     */
    public void deleteById(Long id) {
        pickupPointMapper.deleteById(id);
    }

}
