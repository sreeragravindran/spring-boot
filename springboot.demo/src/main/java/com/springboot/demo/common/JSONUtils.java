package com.springboot.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class JSONUtils {

    private static ObjectMapper OBJECT_MAPPER = null;

    private static ObjectMapper getObjectMapper() {
        if (OBJECT_MAPPER == null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.registerModule(new StringTrimModule());
            OBJECT_MAPPER = mapper;
        }
        return OBJECT_MAPPER;

    }

    /**
     * Converts an object to its json string
     *
     * @param data Object
     * @return JSON string
     */
    public static <T> String toJson(T data) {
        if (data == null) {
            return StringUtils.EMPTY;
        }
        try {
            return getObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    /**
     * Converts a json string to its POJO
     *
     * @param json  JSON String
     * @param clazz Class type of the POJO
     * @return POJO
     */
    public static <T> T fromJsonToObject(String json, Class<T> clazz)  {
        try {
            return getObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Json", e);
        }
    }

    /**
     * Converts String to Object
     *
     * @param json          String
     * @param typeReference Type reference
     * @return
     */
    public static <T> T fromJsonToObject(String json, TypeReference<T> typeReference) {
        try {
            return getObjectMapper().readValue(json, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Json", e);
        }
    }

//    /**
//     * Converts a Couchbase JsonObejct to POJO
//     *
//     * @param json  JSONObject
//     * @param clazz Class type of the pojo
//     * @return POJO
//     */
//    public static <T> T fromJsonObjectToObject(JsonObject json, Class<T> clazz) {
//        return fromJsonToObject(toJson(json.toMap()), clazz);
//    }
//
//    /**
//     * Convert object to Couchbase JSONObject
//     *
//     * @param data Object
//     * @return Json object
//     */
//    public static <T> JsonObject toJsonObject(T data) {
//        return JsonObject.fromJson(JsonUtils.toJson(data));
//    }
//
//    /**
//     * Converts object to Couchbase document with id
//     *
//     * @param id   ID
//     * @param data Object
//     * @return JsonDocument
//     */
//    public static <T> JsonDocument toJsonDocument(String id, T data) {
//
//        return JsonDocument.create(id, toJsonObject(data));
//    }
//
//    /**
//     * Converts object to Couchbase document with id
//     *
//     * @param id   ID
//     * @param data Object
//     * @param cas
//     * @return JsonDocument
//     */
//    public static <T> JsonDocument toJsonDocument(String id, T data, long cas) {
//        return JsonDocument.create(id, toJsonObject(data), cas);
//    }

    /**
     * Method to convert object to byte[]
     *
     * @param object Object
     * @return byte[]
     * @throws IOException Exception
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = getObjectMapper();
        return mapper.writeValueAsBytes(object);
    }

}
