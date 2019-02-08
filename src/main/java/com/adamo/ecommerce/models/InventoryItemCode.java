package com.adamo.ecommerce.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InventoryItemCode {

    private String code;

    public static InventoryItemCode create() {
        InventoryItemCode code = new InventoryItemCode();

        String alphabet = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        Integer codeLength = 6;

        String repeated = new String(new char[codeLength]).replace("\0", alphabet);
        List<String> split = Arrays.asList(repeated.split(""));
        Collections.shuffle(split);

        code.code = "";
        for (int i = 0; i < 6; i++) {
            code.code += split.get(i);
        }
        // concatenate times length
        // shuffle around
        return code;
    }

    public String toString() {
        return code;
    }
}
