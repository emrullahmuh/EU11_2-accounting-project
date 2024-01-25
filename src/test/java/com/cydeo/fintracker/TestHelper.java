package com.cydeo.fintracker;

import com.cydeo.fintracker.dto.CategoryDto;
import com.cydeo.fintracker.entity.Category;

public class TestHelper {


    /*
    - add dto and entity objects here to use on test
     */

    // todo Role

    //todo add Address

    // todo add Company

    // todo add User

    //todo add ClientVendors

    //todo add Category

    //todo add Product

    // Todo add Invoice

    // todo add Invoice Products


    public Category getCategory() {
        return Category.builder().description("description").company(null).build();
    }

    public CategoryDto getCategoryDto() {
        return CategoryDto.builder().description("description").company(null).build();
    }
}
