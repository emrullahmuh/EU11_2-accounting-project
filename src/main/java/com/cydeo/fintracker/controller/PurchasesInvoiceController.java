package com.cydeo.fintracker.controller;


import com.cydeo.fintracker.dto.InvoiceDto;
import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.enums.ClientVendorType;
import com.cydeo.fintracker.enums.InvoiceType;
import com.cydeo.fintracker.service.ClientVendorService;
import com.cydeo.fintracker.service.ProductService;
import com.cydeo.fintracker.service.InvoiceProductService;
import com.cydeo.fintracker.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchasesInvoiceController {

    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;

    public PurchasesInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, InvoiceProductService invoiceProductService, ProductService productService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public String listAllPurchaseInvoices(Model model) {

        model.addAttribute("invoices", invoiceService.listAllInvoices(InvoiceType.PURCHASE));

        return "invoice/purchase-invoice-list";

    }

    @GetMapping("/create")
    public String cratePurchaseInvoices(Model model) {
        model.addAttribute("newPurchaseInvoice", invoiceService.createNewPurchaseInvoice());
        model.addAttribute("vendors", clientVendorService.getAllClientVendors(ClientVendorType.VENDOR));
        return "invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String savePurchaseInvoice(@ModelAttribute("newPurchaseInvoice") InvoiceDto invoice, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("vendors", clientVendorService.getAllClientVendors(ClientVendorType.VENDOR));
            return "/invoice/purchase-invoice-create";
        }

        invoiceService.save(invoice, InvoiceType.PURCHASE);
        String id = invoiceService.findInvoiceId();
        return "redirect:/purchaseInvoices/update/" + id;
    }

    @GetMapping("/update/{id}")
    public String editPurchaseInvoice(@PathVariable("id") Long id, Model model) {

        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("vendors", clientVendorService.getAllClientVendors(ClientVendorType.VENDOR));
        model.addAttribute("newInvoiceProduct", new InvoiceProductDto());
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(id));

        return "invoice/purchase-invoice-update";
    }

    @PostMapping("/update/{id}")
    public String updatePurchaseInvoice(@PathVariable("id") Long id, @ModelAttribute("newPurchaseInvoice") InvoiceDto invoiceDto) {
        invoiceService.save(invoiceDto, InvoiceType.PURCHASE);
        invoiceService.createNewPurchaseInvoice();
        return "invoice/purchase-invoice-update";
    }

    @GetMapping("/delete/{id}")
    public String deletePurchaseInvoice(@PathVariable("id") Long id) {
        invoiceService.delete(id);
        return "redirect:/purchaseInvoices/list";
    }

    @GetMapping("/approve/{id}")
    public String getApproved(@PathVariable("id") Long id) {
        invoiceService.approve(id);
        return "redirect:/purchaseInvoices/list";
    }

}