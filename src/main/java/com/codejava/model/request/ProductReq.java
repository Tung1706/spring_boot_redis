package com.codejava.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductReq {

    private String name;
    private String description;
    private Integer price;
    private Integer quantity;

}
