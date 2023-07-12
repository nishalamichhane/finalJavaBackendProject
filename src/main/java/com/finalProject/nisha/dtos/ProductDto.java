package com.finalProject.nisha.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalProject.nisha.models.Category;
import com.finalProject.nisha.models.Orderline;
import jakarta.persistence.Lob;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.*;

import java.util.List;

public class ProductDto {
    public long id;
    @NotBlank
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    public String productName;
    @Digits(integer=3, fraction=2)
    public double unitPrice;
    @NotNull(message = "Category may not be null and it should be one of these category names " +
            "ELECTRONICS,LADIES_CLOTHING,GENTS_CLOTHING,JEWELERY because this is Enumeration")
    public Category category;
    @NotBlank
    public String description;

    public String name;
    public String type;
    @Lob
    @JsonIgnore
    public byte[] imageData;
    public List<Orderline> orderlines;
}
