package com.cydeo.fintracker.controller;

import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.service.CompanyService;
import com.cydeo.fintracker.service.RoleService;
import com.cydeo.fintracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

        model.addAttribute("roles", roleService.listAllRoles());
        model.addAttribute("users", userService.listAllUsers());

        return "user/user-list";

    }

    @GetMapping("/create")
    public String createUser(Model model) {

        model.addAttribute("newUser", new UserDto());
        model.addAttribute("userRoles", roleService.getAllRolesForLoggedInUser());
        model.addAttribute("companies", companyService.getCompanyDtoByLoggedInUser());

        return "user/user-create";

    }


    @PostMapping("/create")
    public String insertUser(@Valid @ModelAttribute("newUser") UserDto user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("userRoles", roleService.getAllRolesForLoggedInUser());
            model.addAttribute("companies", companyService.getCompanyDtoByLoggedInUser());

            return "user/user-create";

        }

        userService.save(user);

        return "redirect:/users/list";

    }

    @GetMapping("/update/{userid}")
    public String editUser(@PathVariable("userid") long userId, Model model) {

        model.addAttribute("user", userService.findUserById(userId));
        model.addAttribute("userRoles", roleService.getAllRolesForLoggedInUser());
        model.addAttribute("companies", companyService.getCompanyDtoByLoggedInUser());

        return "user/user-update";

    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("userRoles", roleService.getAllRolesForLoggedInUser());
            model.addAttribute("companies", companyService.getCompanyDtoByLoggedInUser());

            return "user/user-update";

        }

        userService.update(user);

        return "redirect:/users/list";

    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users/list";
    }

}
