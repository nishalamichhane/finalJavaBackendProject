package com.finalProject.nisha.services;

import com.finalProject.nisha.dtos.ProductDto;
import com.finalProject.nisha.exceptions.RecordNotFoundException;
import com.finalProject.nisha.models.Product;
import com.finalProject.nisha.repositories.ProductRepository;
import com.finalProject.nisha.util.ImageUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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


    public ProductDto getProduct(Long id) throws RecordNotFoundException {
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

    public ProductDto updateProduct(Long id, ProductDto productDto) throws RecordNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()) {
            throw new RecordNotFoundException("Product didn't find with this id: " + id);
        }

        //Product updateProduct = transferDtoToProduct(productDto);
        Product updateProduct = productOptional.get();
        updateProduct.setId(id);
        updateProduct.setProductName(productDto.productName);
        if(productDto.description!=null)
        updateProduct.setDescription(productDto.description);
        if(productDto.name!=null)
        updateProduct.setName(productDto.name);
        if(productDto.type!=null)
        updateProduct.setType(productDto.type);
        if(productDto.imageData!=null)
        updateProduct.setImageData(productDto.imageData);
        updateProduct.setUnitPrice(productDto.unitPrice);
        updateProduct.setCategory(productDto.category);
        productRepository.save(updateProduct);
        return transferProductToDto(updateProduct);
    }

    public void deleteProduct(Long id) throws RecordNotFoundException {
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
        productDto.category = product.getCategory();
        productDto.description = product.getDescription();
        productDto.orderlines = product.getOrderlines();

        return productDto;
    }

    public Product transferDtoToProduct(ProductDto productDto) {
        Product product = new Product();

        // we don't need setId , that generates in the database or will be in de URL
        product.setProductName(productDto.productName);
        product.setUnitPrice(productDto.unitPrice);
        product.setCategory(productDto.category);
        product.setDescription(productDto.description);
        product.setName(productDto.name);
        product.setImageData(productDto.imageData);
        product.setType(productDto.type);
        product.setOrderlines(productDto.orderlines);

        return product;
    }
    public String uploadImage(MultipartFile file, Long id) throws IOException {
        Optional<Product> optionalProduct1 = productRepository.findById(id);
        Product product = optionalProduct1.get();
        Product imageData = productRepository.save(Product.builder().id(product.getId())
                        .productName(product.getProductName())
                        .description(product.getDescription())
                        .id(product.getId())
                        .unitPrice(product.getUnitPrice())
                        .category(product.getCategory())
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    @Transactional
    public byte[] downloadImage(Long id){
        Optional<Product> dbImageData = productRepository.findById(id);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

}
