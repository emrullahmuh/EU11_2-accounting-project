package com.cydeo.fintracker.controller;

import com.cydeo.fintracker.dto.InvoiceDto;
import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.enums.ClientVendorType;
import com.cydeo.fintracker.enums.InvoiceType;
import com.cydeo.fintracker.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final ProductService productService;
    private final InvoiceProductService invoiceProductService;
    private final CompanyService companyService;

    public SalesInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, ProductService productService, InvoiceProductService invoiceProductService, CompanyService companyService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.productService = productService;
        this.invoiceProductService = invoiceProductService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listAllSalesInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.listAllInvoices(InvoiceType.SALES));
        return "invoice/sales-invoice-list";
    }

    @GetMapping("/create")
    public String createSalesInvoice(Model model) {
        model.addAttribute("newSalesInvoice", invoiceService.createNewSalesInvoice());
        model.addAttribute("clients", clientVendorService.getAllClientVendors(ClientVendorType.CLIENT));
        return "invoice/sales-invoice-create";
    }

    @PostMapping("/create")
    public String saveSalesInvoice(@Valid @ModelAttribute("newSalesInvoice") InvoiceDto invoice, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", clientVendorService.getAllClientVendors(ClientVendorType.CLIENT));
            return "invoice/sales-invoice-create";
        }
        invoiceService.save(invoice, InvoiceType.SALES);
        String id = invoiceService.findInvoiceId();
        return "redirect:/salesInvoices/update/" + id;
    }

    @GetMapping("/update/{id}")
    public String editSalesInvoice(@PathVariable Long id, Model model) {
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("clients", clientVendorService.getAllClientVendors(ClientVendorType.CLIENT));
        model.addAttribute("newInvoiceProduct", new InvoiceProductDto());
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(id));
        return "invoice/sales-invoice-update";
    }

    @PostMapping("/update/{id}")
    public String updateSalesInvoice(@PathVariable("id") Long id, @ModelAttribute("newPurchaseInvoice") InvoiceDto invoice) {
        invoiceService.save(invoice, InvoiceType.SALES);
        invoiceService.createNewSalesInvoice();
        return "redirect:/salesInvoices/list";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct(@PathVariable("id") Long id,
                                     @Valid @ModelAttribute("newInvoiceProduct") InvoiceProductDto invoiceProductDto,
                                     BindingResult bindingResult, Model model) {
        if (productService.checkInventory(invoiceProductDto)) {
            bindingResult.rejectValue("quantity", "",
                    "Not enough " + "<" + invoiceProductDto.getProduct().getName() + ">" + " quantity to sell...");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("invoice", invoiceService.findById(id));
            model.addAttribute("clients", clientVendorService.getAllClientVendors(ClientVendorType.CLIENT));
            model.addAttribute("products", productService.getProducts());
            model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(id));
            return "invoice/sales-invoice-update";
        }
        invoiceProductService.save(invoiceProductDto, id);
        model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(id));
        return "redirect:/salesInvoices/update/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteSalesInvoiceById(@PathVariable("id") Long id) {
        invoiceService.delete(id);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/approve/{id}")
    public String approveSalesInvoiceById(@PathVariable Long id) {
        invoiceService.approve(id);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/print/{invoiceId}")
    public String printInvoice(@PathVariable("invoiceId") Long invoiceId, Model model){
        model.addAttribute("invoice",invoiceService.findById(invoiceId));
        model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(invoiceId));
        model.addAttribute("company", companyService.getCompanyDtoByLoggedInUser().get(0));
        return "invoice/invoice_print";
    }

    @GetMapping("/removeInvoiceProduct/{invoiceId1}/{invoiceProductId}")
    public String removeInvoiceProduct(@PathVariable("invoiceId1") Long id, @PathVariable("invoiceProductId") Long invoiceProductId) {
        invoiceProductService.delete(invoiceProductId);
        return "redirect:/salesInvoices/update/"+id;
    }


}
