package com.johnlw.controller;

import com.johnlw.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(path = "/products")
    public MappingJacksonValue getAllProducts(@DefaultValue("ShowWasNow") @QueryParam("labelType") String labelType) {

        return productService.getProducts(labelType);
    }
}
