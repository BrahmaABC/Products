package com.johnlw.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Products {

    public Products(){
    }

    public Products(List<Product> products) {
        this.products = products;
    }

    @JsonProperty
    private List<Product> products = new ArrayList<>();

    public List<Product> getProduct() {
        return products;
    }

    public void setProduct(List<Product> product) {
        this.products = product;
    }

    @Override
    public String toString() {
        return "Products [products=" + products + "]";
    }
}
