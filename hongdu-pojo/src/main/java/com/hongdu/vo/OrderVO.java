package com.hongdu.vo;

import com.hongdu.entity.OrderDetail;
import com.hongdu.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO extends Orders implements Serializable {

    //订单菜品信息
    private String orderProductes;

    //订单详情
    private List<OrderDetail> orderDetailList;

}
