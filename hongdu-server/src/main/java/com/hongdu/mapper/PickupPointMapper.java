package com.hongdu.mapper;

import com.hongdu.entity.PickupPoint;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PickupPointMapper {

    /**
     * 条件查询
     * @param pickupPoint
     * @return
     */
    List<PickupPoint> list(PickupPoint pickupPoint);

    /**
     * 新增
     * @param pickupPoint
     */
    @Insert("insert into pickup_point" +
            "        (user_id, consignee, phone, sex, province_code, province_name, city_code, city_name, district_code," +
            "         district_name, detail, label, is_default)" +
            "        values (#{userId}, #{consignee}, #{phone}, #{sex}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}," +
            "                #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void insert(PickupPoint pickupPoint);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from pickup_point where id = #{id}")
    PickupPoint getById(Long id);

    /**
     * 根据id修改
     * @param pickupPoint
     */
    void update(PickupPoint pickupPoint);

    /**
     * 根据 用户id修改 是否默认地址
     * @param pickupPoint
     */
    @Update("update pickup_point set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefaultByUserId(PickupPoint pickupPoint);

    /**
     * 根据id删除地址
     * @param id
     */
    @Delete("delete from pickup_point where id = #{id}")
    void deleteById(Long id);

}
