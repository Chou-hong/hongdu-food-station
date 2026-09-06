package com.hongdu.controller.user;

import com.hongdu.constant.StatusConstant;
import com.hongdu.entity.Product;
import com.hongdu.result.Result;
import com.hongdu.service.ProductService;
import com.hongdu.vo.ProductVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userProductController")
@RequestMapping("/user/product")
@Slf4j
@Tag(name = "C端-菜品浏览接口")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "根据分类id查询菜品")
    public Result<List<ProductVO>> list(Long categoryId) {

        //构造redis中的key，规则：product_分类id（首因）
        String key = "product_" + categoryId;

        //查询redis中是否存在菜品数据
        List<ProductVO> list = (List<ProductVO>) redisTemplate.opsForValue().get(key);
        if(list != null && list.size() > 0){
            //如果存在，直接返回，无须查询数据库
            return Result.success(list);
        }

        Product product = new Product();//先组装查询条件实体对象，再传给 service，用来做条件查询
        product.setCategoryId(categoryId);
        product.setStatus(StatusConstant.ENABLE);//查询起售中的菜品

        //如果不存在，查询数据库，将查询到的数据放入redis中
        list = productService.listWithFlavor(product);
        redisTemplate.opsForValue().set(key, list);

        return Result.success(list);
    }

}
