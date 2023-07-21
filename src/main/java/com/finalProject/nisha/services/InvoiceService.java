package com.finalProject.nisha.services;

import com.finalProject.nisha.dtos.InvoiceDto;
import com.finalProject.nisha.exceptions.RecordNotFoundException;
import com.finalProject.nisha.models.Invoice;
import com.finalProject.nisha.repositories.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, JavaMailSender mailSender) {
        this.invoiceRepository = invoiceRepository;
        this.mailSender = mailSender;
    }

    public List<InvoiceDto> getAllInvoice() {
        Iterable<Invoice> invoice = invoiceRepository.findAll();
        List<InvoiceDto> invoiceDtos = new ArrayList<>();

        for (Invoice inv: invoice) {
            invoiceDtos.add(transferInvoiceToDto(inv));
        }

        return invoiceDtos;
    }


    public InvoiceDto getInvoice(Long id) throws RecordNotFoundException {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);

        if(invoiceOptional.isEmpty()) {
            throw new RecordNotFoundException("Invoice didn't find with this id: " + id);
        }
        Invoice invoice = invoiceOptional.get();
        return transferInvoiceToDto(invoice);
    }

    public void getInvoiceDetails(Long id){
        Optional<Invoice> optional = invoiceRepository.findById(id);
        Invoice invoice = optional.get();

        double amount = invoice.getOrder().getTotalAmount();
        Long orderid = invoice.getOrder().getId();
        Date date = invoice.getInvoiceDate();
        String username = invoice.getOrder().getUser().getUsername();
        String subject = "This is the invoice date for your order" + date + " ," + username + ".";
                String email = invoice.getOrder().getUser().getEmail();
        String body = username + "," +
                " This is your email of Invoice. " +
                "Order Id is " + orderid + "," +
                "Total Amount is "+ amount + ".";
        sendSimpleEmail(email, subject, body);
    }

    public InvoiceDto addInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = transferDtoToInvoice(invoiceDto);
        invoiceRepository.save(invoice);

        return transferInvoiceToDto(invoice);
    }

    public InvoiceDto updateInvoice(Long id, InvoiceDto invoiceDto) throws RecordNotFoundException {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if(invoiceOptional.isEmpty()) {
            throw new RecordNotFoundException("Invoice didn't find with this id: " + id);
        }

        Invoice updateInvoice = transferDtoToInvoice(invoiceDto);
        updateInvoice.setId(id);
        invoiceRepository.save(updateInvoice);

        return transferInvoiceToDto(updateInvoice);
    }

    public void deleteInvoice(Long id) throws RecordNotFoundException {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if(optionalInvoice.isEmpty()) {
            throw new RecordNotFoundException("Invoice didn't find with this id: " + id);
        }
        invoiceRepository.deleteById(id);
    }

    public InvoiceDto transferInvoiceToDto(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();

        invoiceDto.id = invoice.getId();
        invoiceDto.invoiceDate = invoice.getInvoiceDate();
        invoiceDto.user = invoice.getOrder().getUser();
        invoiceDto.order = invoice.getOrder();
        return invoiceDto;
    }

    public Invoice transferDtoToInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();

        // we don't need setId , that generates in the database or will be in de URL
        invoice.setInvoiceDate(invoiceDto.invoiceDate);
        //invoice.setUserId(invoiceDto.userId);
        invoice.setOrder(invoiceDto.order);
        return invoice;
    }
    public InvoiceDto getInvoicePerUser(long id) throws RecordNotFoundException {
        Optional<Invoice> ipu = invoiceRepository.findById(id);
        if (ipu.isPresent()) {
            Invoice i = ipu.get();
            InvoiceDto invoiceDto = transferInvoiceToDto(i);
            return invoiceDto;
        }
        else {
            throw new RecordNotFoundException("There is no invoice present with id: " + id);
        }
    }
    private JavaMailSender mailSender;
    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nishaonlineshoppingjava@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Sent successfully...");
    }

}