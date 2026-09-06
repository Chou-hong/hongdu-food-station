package com.hongdu.service;

import com.hongdu.entity.PickupPoint;
import java.util.List;

public interface PickupPointService {

    List<PickupPoint> list(PickupPoint pickupPoint);

    void save(PickupPoint pickupPoint);

    PickupPoint getById(Long id);

    void update(PickupPoint pickupPoint);

    void setDefault(PickupPoint pickupPoint);

    void deleteById(Long id);

}
