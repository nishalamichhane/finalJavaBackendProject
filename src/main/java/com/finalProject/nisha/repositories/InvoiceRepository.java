package com.finalProject.nisha.repositories;

import com.finalProject.nisha.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


}
