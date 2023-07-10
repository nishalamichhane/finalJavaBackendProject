package com.finalProject.nisha.controllers;

import com.finalProject.nisha.dtos.ProductDto;
import com.finalProject.nisha.exceptions.RecordNotFoundException;
import com.finalProject.nisha.repositories.ProductRepository;
import com.finalProject.nisha.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String>SingleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        Product uploadFile = new Product();
        uploadFile.setFilename("newFile");
        uploadFile.setDocfile(file.getBytes());
        productRepository.save(uploadFile);
        return ResponseEntity.ok("Image has been uploaded");
    }

    @GetMapping("{fileId}")
    public ResponseEntity<byte[]> downloadSingleFile(@PathVariable Long fileId) {

        Product file = productRepository.findById(fileId).orElseThrow(() -> new RuntimeException());
        byte[] docFile = file.getDocfile();

        if (docFile == null) {
            throw new RuntimeException("there is no file yet.");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
//        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "file" + file.getFilename() + ".png");
        headers.setContentLength(docFile.length);

        return new ResponseEntity<>(docFile, headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) throws RecordNotFoundException {
        return ResponseEntity.ok().body(productService.getProduct(id));
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
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) throws RecordNotFoundException{
        ProductDto updateProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok().body(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) throws RecordNotFoundException  {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}