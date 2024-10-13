package com.codejava.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.codejava.common.RedisKey;
import com.codejava.common.RedisService;
import com.codejava.entity.Product;
import com.codejava.exception.NotFoundException;
import com.codejava.model.request.ProductReq;
import com.codejava.pubsub.publisher.MessagePublisher;
import com.codejava.repository.ProductRepository;
import com.codejava.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    Gson gson = new Gson();

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MessagePublisher messagePublisher;

    @Autowired
    private ChannelTopic createTopic;

    @Autowired
    private ChannelTopic deleteTopic;

    @Override
    public Product createProduct(ProductReq productReq) {
        Product product = new Product();
        product.setName(productReq.getName());
        product.setDescription(product.getDescription());
        product.setPrice(productReq.getPrice());
        product.setQuantity(productReq.getQuantity());
        messagePublisher.publish(createTopic.getTopic(), String.valueOf(gson.toJsonTree(product)));
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        String products = redisService.getValue(RedisKey.LIST_PRODUCT);
        log.info("get value redis:{}", products);

        if (products != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<Product> productList = objectMapper.readValue(products, new TypeReference<List<Product>>() {
                });
                log.info("Data fetched from Redis");
                return productList;
            } catch (Exception e) {
                log.error("Error parsing product list from Redis", e);
            }
        }

        log.info("Fetching data from database");
        List<Product> productList = productRepository.findAll();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String productListJson = objectMapper.writeValueAsString(productList);
            redisService.setValue(RedisKey.LIST_PRODUCT, productListJson, 300);
        } catch (Exception e) {
            log.error("Error serializing product list", e);
        }

        return productList;
    }

    @Override
    public Product getProduct(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found product with id:" + id));
    }

    @Override
    public Product updateProduct(Integer id, ProductReq productReq) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found product with id:" + id));
        product.setName(productReq.getName());
        product.setDescription(productReq.getDescription());
        product.setPrice(productReq.getPrice());
        product.setQuantity(productReq.getQuantity());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found product with id:" + id));
        productRepository.delete(product);
        messagePublisher.publish(deleteTopic.getTopic(), "Deleted product with id:" + id);
    }
}
