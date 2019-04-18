package com.johnlw.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFilter("ProductFilter")
public class Product {

    private String productId;
    private String title;
    private ColorSwatches[] colorSwatches;
    private Price price;
    private String nowPrice;
    private String priceLabel;

    public ColorSwatches[] getColorSwatches() {
        return colorSwatches;
    }

    public void setColorSwatches(ColorSwatches[] colorSwatches) {
        this.colorSwatches = colorSwatches;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(String nowPrice) {
        this.nowPrice = nowPrice;
    }

    public String getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(String priceLabel) {
        this.priceLabel = priceLabel;
    }
}
