package com.finalProject.nisha.controllers;

import com.finalProject.nisha.dtos.ProductDto;
import com.finalProject.nisha.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok().body(ProductService.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<Object> addProduct(@Valid @RequestBody ProductDto productDto, BindingResult br) {
        if(br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            ProductDto addedProduct = productService.addProduct(productDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedProduct.id)));
            return ResponseEntity.created(uri).body(addedProduct);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto updateProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok().body(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}