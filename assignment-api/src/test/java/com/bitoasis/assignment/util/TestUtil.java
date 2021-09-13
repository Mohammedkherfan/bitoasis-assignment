package com.bitoasis.assignment.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;

public class TestUtil {

    private ObjectMapper mapper = new ObjectMapper();

    public static String decode(String email, String password) {
        return Base64.getEncoder().encodeToString((email+":"+password).getBytes());
    }

    public <T> String toJson(T entity) throws IOException {
        return mapper.writeValueAsString(entity);
    }

    public <T> T fromJson(String json, Class<T> classType) throws IOException {
        return mapper.readValue(json, classType);
    }
}
