package com.cydeo.fintracker.controller;


import com.cydeo.fintracker.dto.ProductDto;
import com.cydeo.fintracker.enums.ProductUnit;
import com.cydeo.fintracker.service.CategoryService;
import com.cydeo.fintracker.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;





    @GetMapping("/list")
    public String getProductList(Model model){

        model.addAttribute("products",productService.getProducts());

        return "product/product-list";

    }


    @GetMapping("/update/{id}")
    public String updateProduct(@PathVariable ("id") Long id, Model model){

        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories",categoryService.listAllCategories());
        model.addAttribute("productUnits",ProductUnit.values());

        return "product/product-update";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute ("product") ProductDto productDto){

        productService.updateProduct(productDto);

        return "redirect:/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable ("id") Long id){

        productService.delete(id);

        return "redirect:/products/list";
    }
}
