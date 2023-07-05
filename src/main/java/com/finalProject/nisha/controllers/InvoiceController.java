package com.finalProject.nisha.controllers;

import com.finalProject.nisha.dtos.InvoiceDto;
import com.finalProject.nisha.dtos.OrderDto;
import com.finalProject.nisha.exceptions.RecordNotFoundException;
import com.finalProject.nisha.services.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoice() {
        return ResponseEntity.ok().body(invoiceService.getAllInvoice());
    }

    @GetMapping("{id}")
    public ResponseEntity<InvoiceDto> getInvoice(@PathVariable Long id) throws RecordNotFoundException {
        return ResponseEntity.ok().body(invoiceService.getInvoice(id));
    }

    @PostMapping
    public ResponseEntity<Object> addInvoice(@Valid @RequestBody InvoiceDto invoiceDto, BindingResult br) {
        if(br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            InvoiceDto addedInvoice = invoiceService.addInvoice(invoiceDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedInvoice.id)));
            return ResponseEntity.created(uri).body(addedInvoice);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateInvoice(@PathVariable Long id, @RequestBody InvoiceDto invoiceDto) throws RecordNotFoundException {
        InvoiceDto updateInvoice = invoiceService.updateInvoice(id, invoiceDto);
        return ResponseEntity.ok().body(updateInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInvoice(@PathVariable Long id) throws RecordNotFoundException {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/invoice")
    public ResponseEntity<InvoiceDto> getInvoicePerUser(@PathVariable Long id) throws RecordNotFoundException {
        InvoiceDto invoiceDto = invoiceService.getInvoicePerUser(id);
        return new ResponseEntity<>(invoiceDto, HttpStatus.OK);
    }

}