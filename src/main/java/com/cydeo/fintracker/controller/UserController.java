package com.cydeo.fintracker.controller;

import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.service.CompanyService;
import com.cydeo.fintracker.service.RoleService;
import com.cydeo.fintracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final CompanyService companyService;

    public UserController(UserService userService, RoleService roleService, CompanyService companyService) {
        this.userService = userService;
        this.roleService = roleService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listAllUsers(Model model) {


        model.addAttribute("users", userService.listAllUsers());

        return "user/user-list";

    }

    @GetMapping("/create")
    public String createUser(Model model) {

        model.addAttribute("newUser", new UserDto());
        model.addAttribute("userRoles", roleService.listAllRoles());
        model.addAttribute("companies", companyService.getCompanies());

        return "user/user-create";

    }


    @PostMapping("/create")
    public String insertUser(@ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("user", new UserDto());
            model.addAttribute("userRoles", roleService.listAllRoles());
            model.addAttribute("companies", companyService.getCompanies());

            return "user/user-create";

        }

        userService.save(user);

        return "redirect:/users/list";

    }

    @GetMapping("/update/{userid}")
    public String editUser(@PathVariable("userid") long userId, Model model) {

        model.addAttribute("user", userService.findUserById(userId));
        model.addAttribute("userRoles", roleService.listAllRoles());
        model.addAttribute("companies", companyService.getCompanies());

        return "user/user-update";

    }

    @PostMapping("/update")
    public String updateUser( @ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("userRoles", roleService.listAllRoles());
            model.addAttribute("companies", companyService.getCompanies());

            return "user/user-update";

        }

        userService.update(user);

        return "redirect:/users/list";

    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.delete(userId);

        return "redirect:/users/list";
    }

}
