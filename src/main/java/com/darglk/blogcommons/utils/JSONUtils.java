package com.darglk.blogcommons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

@Slf4j
public class JSONUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private JSONUtils() { }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Failed to parse JSON value: {}", json);
        }
        return null;
    }

    public static <T> String toJson(T value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert object to JSON string");
            e.printStackTrace();
        }
        return Strings.EMPTY;
    }
}
