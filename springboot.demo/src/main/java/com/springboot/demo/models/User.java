package com.springboot.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Positive(message = "id should be a positive integer")
    private Integer id;

    @Size(min = 3, message = "name should be atleast 3 characters long")
    private String name;

    @Positive(message = "age should be a positive integer")
    private int age;

    @Past
    private Date dateOfBirth;

    //TODO: how can we apply validations dynamically?
    @AssertFalse(message = "the person should be above 18") // this is useful to assert complex conditions on the object
    @JsonIgnore
    public boolean isMinor(){
        return this.age < 18;
    }

}
