package com.bitoasis.assignment.util;

import java.util.Base64;

public class Decoding {

    public static String decode(String value) {
        byte[] decodedBytes = Base64.getDecoder().decode(value);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
