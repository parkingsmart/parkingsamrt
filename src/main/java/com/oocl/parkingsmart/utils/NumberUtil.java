package com.oocl.parkingsmart.utils;

import java.util.Random;

public class NumberUtil {

    public static String createPwd(int digit) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < digit; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
