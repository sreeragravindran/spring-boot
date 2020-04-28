package com.springboot.demo.rest.controllers;


/*
    example of dynamically filtering properties during jackson serialization
*/

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.springboot.demo.models.SomeModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamicFilterController {

    @GetMapping("/dynamicfilter")
    public ResponseEntity<?> filter(@RequestParam String fields) {


        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(","));

        SimpleFilterProvider filterProvider =
                new SimpleFilterProvider().addFilter("someModelFilter", simpleBeanPropertyFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(new SomeModel());
        mapping.setFilters(filterProvider);

//      mapping.getSerializationView()

        return ResponseEntity.ok(mapping);

    }

}
