package com.finalProject.nisha.services;

import com.finalProject.nisha.dtos.ProductDto;
import com.finalProject.nisha.exceptions.RecordNotFoundException;
import com.finalProject.nisha.models.Category;
import com.finalProject.nisha.models.Product;
import com.finalProject.nisha.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProductService.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Captor
    private ArgumentCaptor<Product> productCaptor;
    @InjectMocks
    private ProductService productService;
    private Product product1;
    ProductDto productDto;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto();
        product1 = new Product(1L, "Laptop", 50, "Samsung", null, "jas.png", "image/png", null, null);
    }

    @Test
    void getAllProducts() {
        // Arrange
        ArrayList<Product> productList = new ArrayList<>();
        Product product = new Product(1L, "Laptop", 50, "Samsung", null, "jas.png", "image/png", null, null);
        productList.add(product);
        when(productRepository.findAll()).thenReturn(productList);

        // Act
        List<ProductDto> actualAllProducts = productService.getAllProducts();

        // Assert
        assertEquals(1, actualAllProducts.size());
        ProductDto getResult = actualAllProducts.get(0);
        assertEquals(product.getCategory(), getResult.getCategory());
        assertEquals(product.getDescription(), getResult.getDescription());
        assertEquals(product.getProductName(), getResult.getProductName());
        assertEquals(product.getUnitPrice(), getResult.getUnitPrice());
        assertEquals(product.getImageData(), getResult.getImageData());
        verify(productRepository).findAll();

    }

    @Test
    void getProduct() {
        // Arrange
        Product product = new Product(1L, "Laptop", 50, "Samsung", null, "jas.png", "image/png", null, null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        // Act
        ProductDto actualProductId = productService.getProduct(1L);
        // Assert
        assertEquals(null, actualProductId.getCategory());
        assertEquals("Samsung", actualProductId.getDescription());
        assertEquals("Laptop", actualProductId.getProductName());
        assertEquals(50, actualProductId.getUnitPrice());
        assertEquals(null, actualProductId.getImageData());

        verify(productRepository).findById(1L);
    }
    @Test
    void testGetByIdWhenNotFound() {
        // Arrange
        Long Nid = 10L;
        when(productRepository.findById(Nid)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> productService.getProduct(Nid));
        verify(productRepository).findById(Nid);
    }

    @Test

    public void testAddProduct() {

    }

    @Test
    void updateProduct() {
        Long pid = 1L;
        Product exProduct = new Product(1L, "Laptop", 50, "Samsung", null, "jas.png", "image/png", null, null);

        ProductDto newProductData = new ProductDto();
        newProductData.setProductName("Laptop1");
        newProductData.setUnitPrice(51);
        newProductData.setDescription("Samsung1");
        newProductData.setName("jas.png");
        newProductData.setType("image/png");

        when(productRepository.findById(pid)).thenReturn(Optional.of(exProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ProductDto updatedProduct = productService.updateProduct(pid, newProductData);

        // Assert
        assertEquals(newProductData.getProductName(), updatedProduct.getProductName());
        assertEquals(newProductData.getUnitPrice(), updatedProduct.getUnitPrice());
        assertEquals(newProductData.getDescription(), updatedProduct.getDescription());

        verify(productRepository).findById(pid);
        verify(productRepository).save(productCaptor.capture());

        Product savedProduct = productCaptor.getValue();
        assertEquals(newProductData.getProductName(), savedProduct.getProductName());
        assertEquals(newProductData.getUnitPrice(), savedProduct.getUnitPrice());
        assertEquals(newProductData.getDescription(), savedProduct.getDescription());
        assertEquals(newProductData.getName(), savedProduct.getName());
        assertEquals(newProductData.getType(), savedProduct.getType());
    }
    @Test
    void testUpdateNonExistingProduct() {
        Long uid = 10L;
        when(productRepository.findById(uid)).thenReturn(Optional.empty());

        ProductDto productDto = new ProductDto();
        productDto.setProductName("Laptop");
        productDto.setUnitPrice(50);
        productDto.setDescription("Samsung");
        productDto.setName("jas.png");
        productDto.setType("image/png");

        assertThrows(RecordNotFoundException.class, () -> productService.updateProduct(uid, productDto));
        verify(productRepository).findById(uid);
    }

    @Test
    void deleteProduct() {
        Long did = 1L;
        Product product = new Product();
        when(productRepository.findById(did)).thenReturn(Optional.of(product));

        // Act
        productService.deleteProduct(did);

        // Assert
        verify(productRepository).delete(product);

    }
    @Test
    void testDeleteProductNotFound() {
        // Arrange
        Long id = 10L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> productService.deleteProduct(id));
    }

    @Test
    void transferProductToDto() {
        Product product1 = new Product();
        product1.setProductName("Laptop");
        product1.setUnitPrice(50);
        product1.setDescription("Samsung");
        product1.setName("jas.png");
        product1.setType("image/png");
        ProductDto actualProductToDtoResult = productService.transferProductToDto(product1);

        // Assert
        assertEquals("Laptop", actualProductToDtoResult.getProductName());
        assertEquals(50, actualProductToDtoResult.getUnitPrice());
        assertEquals("Samsung", actualProductToDtoResult.getDescription());
        assertEquals("jas.png", actualProductToDtoResult.getName());
        assertEquals("image/png", actualProductToDtoResult.getType());
    }

    @Test
    void transferDtoToProduct() {
        // Arrange
        productDto.setProductName("Laptop");
        productDto.setUnitPrice(50);
        productDto.setDescription("Samsung");
        productDto.setName("jas.png");
        productDto.setType("image/png");

        // Act
        Product actualTransferDtoToProductResult = productService.transferDtoToProduct(productDto);

        // Assert
        assertEquals("Laptop", actualTransferDtoToProductResult.getProductName());
        assertEquals(50, actualTransferDtoToProductResult.getUnitPrice());
        assertEquals("Samsung", actualTransferDtoToProductResult.getDescription());
        assertEquals("jas.png", actualTransferDtoToProductResult.getName());
        assertEquals("image/png", actualTransferDtoToProductResult.getType());
    }

    @Test
    void uploadImage() {


    }
    @Test
    public void testUploadImage() throws IOException, IOException {
        // Create a sample product and file
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setProductName("Test Product");
        product.setDescription("Test Description");
        product.setUnitPrice(100.0);
        product.setCategory(Category.ELECTRONICS);

        MockMultipartFile file = new MockMultipartFile(
                "file", "test_image.jpg", "image/jpeg", "Test Image Data".getBytes());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class))).thenReturn(product);

        String result = productService.uploadImage(file, productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));

        String expectedMessage = "file uploaded successfully : test_image.jpg";
        assertEquals(expectedMessage, result);
    }

    @Test
    void downloadImage() {
    }
}