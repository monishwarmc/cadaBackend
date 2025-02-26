package com.cada.backend.controller;

import com.cada.backend.entity.Product;
import com.cada.backend.entity.Store;
import com.cada.backend.repository.ProductRepository;
import com.cada.backend.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    // Get all products
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    // Get all products of a specific store
    @GetMapping("store/{storeId}")
    public ResponseEntity<List<Product>> getProductsByStore(@PathVariable Long storeId) {
        if (!storeRepository.existsById(storeId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Product> products = productRepository.findByStoreId(storeId);
        return ResponseEntity.ok(products);
    }

    // Get a single product by ID
    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Add a new product to a store
    @PostMapping("add/{storeId}")
    public ResponseEntity<?> addProduct(@PathVariable Long storeId,
                                        @RequestParam("name") String name,
                                        @RequestParam(value = "description", required = false) String description,
                                        @RequestParam("price") double price,
                                        @RequestParam(value = "photo", required = false) MultipartFile photo) {
        Optional<Store> storeOpt = storeRepository.findById(storeId);
        if (storeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Store not found");
        }

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStore(storeOpt.get());

        try {
            if (photo != null && !photo.isEmpty()) {
                product.setPhoto(photo.getBytes());
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing photo upload");
        }

        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    // Update a product
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "description", required = false) String description,
                                           @RequestParam(value = "price", required = false) Double price,
                                           @RequestParam(value = "photo", required = false) MultipartFile photo) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        Product product = productOpt.get();

        if (name != null) product.setName(name);
        if (description != null) product.setDescription(description);
        if (price != null) product.setPrice(price);

        try {
            if (photo != null && !photo.isEmpty()) {
                product.setPhoto(photo.getBytes());
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing photo update");
        }

        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfully.");
    }


    @GetMapping("photo/{id}")
    public ResponseEntity<byte[]> getProductPhoto(@PathVariable Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty() || productOpt.get().getPhoto() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    
        byte[] imageBytes = productOpt.get().getPhoto();
        MediaType mediaType = MediaType.IMAGE_JPEG; // Default media type
    
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes)) {
            String mediaTypeValue = URLConnection.guessContentTypeFromStream(byteArrayInputStream);
            if (mediaTypeValue != null) {
                mediaType = MediaType.parseMediaType(mediaTypeValue); // Ensure it's from org.springframework.http
            }
        } catch (IOException e) {
            System.err.println("Error determining media type: " + e.getMessage());
        }
    
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imageBytes);
    }


}
