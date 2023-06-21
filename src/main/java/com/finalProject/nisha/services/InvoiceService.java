package com.finalProject.nisha.services;

import com.finalProject.nisha.dtos.InvoiceDto;
import com.finalProject.nisha.exceptions.RecordNotFoundException;
import com.finalProject.nisha.models.Invoice;
import com.finalProject.nisha.repositories.InvoiceRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<InvoiceDto> getAllInvoice() {
        Iterable<Invoice> invoice = invoiceRepository.findAll();
        List<InvoiceDto> invoiceDtos = new ArrayList<>();

        for (Invoice inv: invoice) {
            invoiceDtos.add(transferInvoiceToDto(inv));
        }

        return invoiceDtos;
    }

    public InvoiceDto getInvoice(Long id) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);

        if(invoiceOptional.isEmpty()) {
            throw new RecordNotFoundException("Invoice didn't find with this id: " + id);
        }

        Invoice invoice = invoiceOptional.get();

        return transferInvoiceToDto(invoice);
    }

    public InvoiceDto addInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = transferDtoToInvoice(invoiceDto);
        invoiceRepository.save(invoice);

        return transferInvoiceToDto(invoice);
    }

    public InvoiceDto updateInvoice(Long id, InvoiceDto invoiceDto) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if(invoiceOptional.isEmpty()) {
            throw new RecordNotFoundException("Invoice didn't find with this id: " + id);
        }

        Invoice updateInvoice = transferDtoToInvoice(invoiceDto);
        updateInvoice.setId(id);
        invoiceRepository.save(updateInvoice);

        return transferInvoiceToDto(updateInvoice);
    }

    public void deleteInvoice(Long id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if(optionalInvoice.isEmpty()) {
            throw new RecordNotFoundException("Invoice didn't find with this id: " + id);
        }
        invoiceRepository.deleteById(id);
    }

    public InvoiceDto transferInvoiceToDto(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();

        invoiceDto.id = invoice.getId();
        invoiceDto.totalAmount = invoice.getTotalAmount();
        invoiceDto.invoiceDate = invoice.getInvoiceDate();
        invoiceDto.userId = invoice.getUserId();
        invoiceDto.orderId = invoice.getOrderId();
        return invoiceDto;
    }

    public Invoice transferDtoToInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();

        // we don't need setId , that generates in the database or will be in de URL
        invoice.setTotalAmount(invoiceDto.totalAmount);
        invoice.setInvoiceDate(invoiceDto.invoiceDate);
        invoice.setUserId(invoiceDto.userId);
        invoice.setOrderId(invoiceDto.orderId);
        return invoice;
    }
}
