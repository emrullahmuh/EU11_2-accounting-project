package com.cydeo.fintracker.controller;


import com.cydeo.fintracker.dto.ProductDto;
import com.cydeo.fintracker.enums.ProductUnit;
import com.cydeo.fintracker.service.CategoryService;
import com.cydeo.fintracker.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


    @GetMapping("/list")
    public String getProductList(Model model) {

        model.addAttribute("products", productService.getProducts());

        return "product/product-list";

    }

    @GetMapping("/create")
    public String createProduct(Model model) {

        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("categories", categoryService.listAllCategories());
        model.addAttribute("productUnits", ProductUnit.values());


        return "product/product-create";
    }

    @PostMapping("/create")
    public String insertProduct(@Valid @ModelAttribute("newProduct") ProductDto product, BindingResult bindingResult, Model model) {
        bindingResult = productService.uniqueName(product, bindingResult);
        if (bindingResult.hasFieldErrors()) {

            model.addAttribute("categories", categoryService.listAllCategories());
            model.addAttribute("productUnits", ProductUnit.values());

            return "product/product-create";

        }

        productService.save(product);

        return "redirect:/products/list";
    }


    @GetMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, Model model) {

        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.listAllCategories());
        model.addAttribute("productUnits", ProductUnit.values());

        return "product/product-update";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@ModelAttribute("product") ProductDto product, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("product", productService.findById(product.getId()));
            model.addAttribute("categories", categoryService.listAllCategories());
            model.addAttribute("productUnits", ProductUnit.values());

            return "product/product-update";

        }

        productService.updateProduct(product);

        return "redirect:/products/list";

    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {

        ProductDto existingProduct = productService.findById(id);

        if (existingProduct.getQuantityInStock() > 0) {

            return "redirect:/products/list";
        }


        productService.delete(id);

        return "redirect:/products/list";
    }
}
