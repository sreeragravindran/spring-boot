package com.springboot.demo.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class StringTrimModule extends SimpleModule {

    /**
     * Helper to trim strings during serialization
     */
    public StringTrimModule() {
        addDeserializer(String.class, new StdScalarDeserializer<String>(String.class) {
            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
                return StringUtils.trim(jsonParser.getValueAsString());
            }
        });
    }
}