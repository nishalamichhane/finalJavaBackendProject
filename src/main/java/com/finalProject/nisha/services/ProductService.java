package com.finalProject.nisha.services;

import com.finalProject.nisha.dtos.ProductDto;
import com.finalProject.nisha.exceptions.RecordNotFoundException;
import com.finalProject.nisha.models.Product;
import com.finalProject.nisha.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        Iterable<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product t: products) {
            productDtos.add(transferProductToDto(t));
        }

        return productDtos;
    }

    public ProductDto getProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()) {
            throw new RecordNotFoundException("Product didn't find with this id: " + id);
        }

        Product product = productOptional.get();

        return transferProductToDto(product);
    }

    public ProductDto addProduct(ProductDto productDto) {
        Product product = transferDtoToProduct(productDto);
        productRepository.save(product);

        return transferProductToDto(product);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()) {
            throw new RecordNotFoundException("Product didn't find with this id: " + id);
        }

        Product updateProduct = transferDtoToProduct(productDto);
        updateProduct.setId(id);
        productRepository.save(updateProduct);

        return transferProductToDto(updateProduct);
    }

    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) {
            throw new RecordNotFoundException("Product didn't find with this id: " + id);
        }
        productRepository.deleteById(id);
    }

    public ProductDto transferProductToDto(Product product) {
        ProductDto productDto = new ProductDto();

        productDto.id = product.getId();
        productDto.productName = product.getProductName();
        productDto.unitPrice = product.getUnitPrice();
        productDto.categoryId = product.getCategoryId();
        productDto.description = product.getDescription();

        return productDto;
    }

    public Product transferDtoToProduct(ProductDto productDto) {
        Product product = new Product();

        // we don't need setId , that generates in the database or will be in de URL
        product.setProductName(productDto.productName);
        product.setUnitPrice(productDto.unitPrice);
        product.setCategoryId(productDto.categoryId);
        product.setDescription(productDto.description);

        return product;
    }
}
