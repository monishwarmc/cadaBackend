package com.cada.backend.controller;

import com.cada.backend.entity.Store;
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
import java.util.Optional;/*  */

import org.springframework.http.MediaType;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("store")
public class StoreController {
    
    @Autowired
    private StoreRepository storeRepository;

    @GetMapping()
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        return storeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("add")
    public ResponseEntity<Store> addStore(@RequestParam("name") String name,
                                          @RequestParam("location") String location,
                                          @RequestParam("phoneNumber") String phoneNumber,
                                          @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            Store store = new Store(name, location, phoneNumber);
            
            if (photo != null && !photo.isEmpty()) {
                store.setPhoto(photo.getBytes()); // Convert MultipartFile to byte array
            }
    
            return ResponseEntity.status(HttpStatus.CREATED).body(storeRepository.save(store));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

    @PutMapping("update/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id,
                                             @RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "location", required = false) String location,
                                             @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                             @RequestParam(value = "photo", required = false) MultipartFile photo) {
        return storeRepository.findById(id).map(store -> {
            try {
                if (name != null) store.setName(name);
                if (location != null) store.setLocation(location);
                if (phoneNumber != null) store.setPhoneNumber(phoneNumber);
                if (photo != null && !photo.isEmpty()) {
                    store.setPhoto(photo.getBytes()); // ✅ Now this works
                }
                return ResponseEntity.ok(storeRepository.save(store));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(store); // ✅ Ensure consistent return type
            }
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        if (!storeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        storeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("photo/{id}")
    public ResponseEntity<byte[]> getStorePhoto(@PathVariable Long id) {
        Optional<Store> storeOpt = storeRepository.findById(id);
        if (storeOpt.isEmpty() || storeOpt.get().getPhoto() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    
        byte[] imageBytes = storeOpt.get().getPhoto();
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
