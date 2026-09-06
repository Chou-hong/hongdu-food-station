package com.hongdu.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class ShoppingCartDTO implements Serializable {

    private Long productId;
    private Long comboId;
    private String productSpec;

}
