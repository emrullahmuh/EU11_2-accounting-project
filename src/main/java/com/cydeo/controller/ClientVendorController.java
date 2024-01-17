package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.service.ClientVendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/clientVendor")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;
    public ClientVendorController(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String listClientVendor(Model model){

        List<ClientVendorDto> clientVendor = clientVendorService.listAllClientVendor();
        model.addAttribute("clientVendor",clientVendor);
        return "/clientVendor_list";
    }

    @GetMapping("/create")
    public String showCreateVendor(Model model){

        model.addAttribute("newClientVendor", new ClientVendorDto());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

        return "/clientVendor_create";
    }

    @PostMapping("/create")
    public String editCreateVendor(@ModelAttribute("newClientVendor") ClientVendorDto newClientVendor){

        clientVendorService.saveClientVendor(newClientVendor);

        return "redirect:/clientVendor_list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateClientVendor(@PathVariable("id") Long id, Model model){

        ClientVendorDto clientVendor=clientVendorService.findById(id);
        model.addAttribute("clientVendor", clientVendor);
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

        return "/clientVendor_update";
    }

    @PostMapping("/update/{id}")
    public String updateClientVendor(@PathVariable("id") Long id, @ModelAttribute("clientVendor") ClientVendorDto clientVendor){

        clientVendorService.update(id,clientVendor);

        return "redirect:/clientVendor_list";
    }

    public String delete(@PathVariable("id") Long id){

        clientVendorService.delete(id);
        return  "redirect:/clientVendor_list";
    }


}
