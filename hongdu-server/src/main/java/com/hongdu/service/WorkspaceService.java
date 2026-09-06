package com.hongdu.service;

import com.hongdu.vo.BusinessDataVO;
import com.hongdu.vo.ProductOverViewVO;
import com.hongdu.vo.OrderOverViewVO;
import com.hongdu.vo.ComboOverViewVO;
import java.time.LocalDateTime;

public interface WorkspaceService {

    /**
     * 根据时间段统计营业数据
     * @param begin
     * @param end
     * @return
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * 查询订单管理数据
     * @return
     */
    OrderOverViewVO getOrderOverView();

    /**
     * 查询菜品总览
     * @return
     */
    ProductOverViewVO getProductOverView();

    /**
     * 查询套餐总览
     * @return
     */
    ComboOverViewVO getComboOverView();

}
