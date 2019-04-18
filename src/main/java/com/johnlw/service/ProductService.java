package com.johnlw.service;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.johnlw.model.ColorSwatches;
import com.johnlw.model.Product;
import com.johnlw.model.Products;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    public MappingJacksonValue getProducts(String labelPrice) {

        RestTemplate restTemplate = new RestTemplate();
        Products products = restTemplate.getForObject("https://jl-nonprod-syst.apigee.net/v1/categories/600001506/products?key=2ALHCAAs6ikGRBoy6eTHA58RaG097Fma", Products.class);

        Products filteredP = setProductValues(products, labelPrice);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("productId", "title",
                "colorSwatches", "nowPrice", "priceLabel");
        SimpleBeanPropertyFilter clorSwatchesFilter = SimpleBeanPropertyFilter.filterOutAllExcept("color",
                "rgbColor", "skuId");
        FilterProvider filters = new SimpleFilterProvider().addFilter("ProductFilter", filter)
                .addFilter("ColorSwatchesFilter", clorSwatchesFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(filteredP);

        mapping.setFilters(filters);

        return mapping;
    }

    private Products setProductValues(Products products, String labelPrice) {

        List<Product> finalPList = new ArrayList<>();
        Float was = 0.0f;
        Float now = 0.0f;

        for (Product product : products.getProduct()) {

            if (product.getPrice().getWas() != null && !product.getPrice().getWas().isEmpty())
                was = Float.valueOf(product.getPrice().getWas());
            if (product.getPrice().getWas() != null && !product.getPrice().getWas().isEmpty())
                now = Float.valueOf(product.getPrice().getNow());

            Product finalP = null;

            if ((was - now) > 0) {

                finalP = new Product();

                finalP.setProductId(product.getProductId());
                finalP.setTitle(product.getTitle());

                for (ColorSwatches colorSwatches : product.getColorSwatches()) {
                    colorSwatches.setRgbColor(ProductUtils.getHexColor(colorSwatches.getBasicColor()));
                }

                finalP.setColorSwatches(product.getColorSwatches());
                finalP.setNowPrice(
                        ProductUtils.getPrice(product.getPrice().getNow()));
                finalP.setPriceLabel(setPriceLabel(product, labelPrice));

            }
            if (!(finalP == null))
                finalPList.add(finalP);
        }

        return new Products(finalPList);

    }


    private String setPriceLabel(Product product, String labelType) {

        Double difference = 0.0;
        if (labelType == null || labelType.trim().equals("\"ShowWasNow\"")) {
            return "Was " + ProductUtils.getPrice(product.getPrice().getWas()) + ","
                    + "now " + ProductUtils.getPrice(product.getPrice().getNow());
        }
        else if (labelType.equals("\"ShowWasThenNow\"")) {
            String then = "";
            if (!product.getPrice().getThen2().isEmpty())
                then = product.getPrice().getThen2();
            else if (!product.getPrice().getThen1().isEmpty())
                then = product.getPrice().getThen1();
            if (!then.isEmpty())
                return "Was " + ProductUtils.getPrice(product.getPrice().getWas())
                        + "," + "then "
                        + ProductUtils.getPrice(product.getPrice().getWas()) + ","
                        + "now " + ProductUtils.getPrice(product.getPrice().getNow());
            else
                return "Was " + ProductUtils.getPrice(product.getPrice().getWas())
                        + "," + "now "
                        + ProductUtils.getPrice(product.getPrice().getNow());
        } else if (labelType.equals("\"ShowPercDscount\"")) {
            if (product.getPrice().getWas() != null && !product.getPrice().getWas().isEmpty() && product.getPrice().getNow() != null
                /*&& !product.getPrice().getNow().isEmpty()*/)
                difference = Double.parseDouble(product.getPrice().getWas())
                        - Double.parseDouble(product.getPrice().getNow());
            return difference * 10 + "% off -" + "now "
                    + ProductUtils.getPrice(product.getPrice().getNow());
        }

        return "";
    }


}
