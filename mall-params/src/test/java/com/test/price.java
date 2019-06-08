package com.test;

import java.math.BigDecimal;

public class price {


    public static void main(String[] args) {

        BigDecimal totalPrice = new BigDecimal(0);
        Integer buyNum = 3;
        BigDecimal goodsPrice = new BigDecimal(3.23);
        for(int i = 0; i < buyNum ; i++){
            totalPrice = totalPrice.add(goodsPrice);
        }


        System.out.println(totalPrice.floatValue());
    }

}
