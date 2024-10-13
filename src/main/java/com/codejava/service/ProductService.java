package com.codejava.service;

import com.codejava.entity.Product;
import com.codejava.model.request.ProductReq;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductReq productReq);

    List<Product> getProducts();

    Product getProduct(Integer id);

    Product updateProduct(Integer id, ProductReq productReq);

    void deleteProduct(Integer id);

}
