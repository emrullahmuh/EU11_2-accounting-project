package com.cydeo.fintracker.controller;

import com.cydeo.fintracker.dto.CategoryDto;
import com.cydeo.fintracker.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listAllCategories(Model model){

        model.addAttribute("categories", categoryService.listAllCategories());

        return "category/category-list";
    }

    @GetMapping("/update/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model)  {

        model.addAttribute("category", categoryService.getById(id));

        return "category/category-update";

    }

    @PostMapping("/update/{id}")
    public String updateCategory(@Valid @ModelAttribute("category") CategoryDto category, BindingResult bindingResult,
                                 @PathVariable ("id") Long id)  {

        if(categoryService.hasProducts(category)){

         bindingResult.rejectValue("description", " ","This category already has product/products! Make sure the new description that will be provided is proper.");

        }

        if(bindingResult.hasErrors()){
            return "category/category-update";
        }

        categoryService.update(category,id);

        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id)  {

        categoryService.delete(id);

        return "redirect:/categories/list";
    }

    @GetMapping("/create")
    public String createCategory(Model model){

        model.addAttribute("newCategory", new CategoryDto());

        return "category/category-create";
    }

    @PostMapping("/create")
    public String insertCategory(@Valid @ModelAttribute("newCategory") CategoryDto category, BindingResult bindingResult) {

        boolean categoryDescriptionUnique = categoryService.isCategoryDescriptionUnique(category.getDescription());

        if (!categoryDescriptionUnique) {
            bindingResult.rejectValue("description", " ", "This category description is already exists");
        }

        if (bindingResult.hasErrors()) {
            return "category/category-create";
        }

        categoryService.save(category);

        return "redirect:/categories/list";
    }



}
