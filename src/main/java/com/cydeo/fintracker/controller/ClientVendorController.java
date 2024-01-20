package com.cydeo.fintracker.controller;


import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.enums.ClientVendorType;
import com.cydeo.fintracker.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;
    public ClientVendorController(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String listClientVendors(Model model){

      //  List<ClientVendorDto> clientVendors = clientVendorService.getAllClientVendors();
        model.addAttribute("clientVendor", clientVendorService.getAllClientVendors());
        return "/clientVendor/clientVendor-list";
    }

    @GetMapping("/create")
    public String showCreateVendor(Model model){

        model.addAttribute("newClientVendor", new ClientVendorDto());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

        return "/clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String editCreateVendor(@ModelAttribute("newClientVendor") ClientVendorDto newClientVendor){

        clientVendorService.saveClientVendor(newClientVendor);

        return "redirect:/clientVendors/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateClientVendor(@PathVariable("id") Long id, Model model){

        ClientVendorDto clientVendor=clientVendorService.findById(id);
        model.addAttribute("clientVendor", clientVendor);
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

        return "/clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{id}")
    public String updateClientVendor(@PathVariable("id") Long id, @ModelAttribute("clientVendor") ClientVendorDto clientVendor){

        clientVendorService.update(id,clientVendor);

        return "redirect:/clientVendors/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        clientVendorService.delete(id);
        return  "redirect:/clientVendors/list";
    }


}
