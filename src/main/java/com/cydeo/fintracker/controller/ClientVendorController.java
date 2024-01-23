package com.cydeo.fintracker.controller;


import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.enums.ClientVendorType;
import com.cydeo.fintracker.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

        List<ClientVendorDto> clientVendors = clientVendorService.getAll();
        model.addAttribute("clientVendors",clientVendors);
        return "clientVendor/clientVendor-list";
    }

    @GetMapping("/create")
    public String showCreateVendor(Model model){

        List<String> countries = new ArrayList<>(Arrays.asList("UK", "USA"));

        model.addAttribute("newClientVendor", new ClientVendorDto());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        model.addAttribute("countries", countries);

        return "clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String editCreateVendor(@Valid @ModelAttribute("newClientVendor") ClientVendorDto newClientVendor,
                                   BindingResult bindingResult, Model model){

        if (bindingResult.hasFieldErrors()){
            model.addAttribute("countries",List.of("USA","UK"));
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
            return "clientVendor/clientVendor-create";
        }
        clientVendorService.saveClientVendor(newClientVendor);

        return "redirect:/clientVendors/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateClientVendor(@PathVariable("id") Long id, Model model){

        ClientVendorDto clientVendor=clientVendorService.findById(id);
        List<String> countries = new ArrayList<>(Arrays.asList("UK", "USA"));

        model.addAttribute("countries", countries);
        model.addAttribute("clientVendor", clientVendor);
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

        return "clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{id}")
    public String updateClientVendor(@PathVariable("id") Long id, @ModelAttribute("clientVendor")
    ClientVendorDto clientVendor,BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
            model.addAttribute("countries", Arrays.asList("USA", "UK"));
            return "clientVendor/clientVendor-update";
        }

        clientVendorService.update(id,clientVendor);

        return "redirect:/clientVendors/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteClientVendor(@PathVariable("id") Long id){

        clientVendorService.delete(id);
        return  "redirect:/clientVendors/list";
    }


}
