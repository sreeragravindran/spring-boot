package com.springboot.demo.models;


import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("someModelFilter")
public class SomeModel {
    private String field1 = "field1";
    private String field2 = "field2";
    private String field3 = "field3";
    private String field4 = "field4";
    private String field5 = "field5";
}
