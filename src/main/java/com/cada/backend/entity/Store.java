package com.cada.backend.entity;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity @ToString @AllArgsConstructor @NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;
    
    @Column(nullable = false)
    @Getter @Setter
    @NotBlank(message = "Store name cannot be empty")
    private String name;

    @Column(nullable = false)
    @Getter @Setter
    @NotBlank(message = "Store location cannot be empty")
    private String location;

    @Column(nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Getter @Setter
    private String phoneNumber;

    
    @Getter @Setter
    private byte[] photo;
    @JsonIgnore
    public String getPhotoBase64() {
        return photo != null ? Base64.getEncoder().encodeToString(photo) : null;
    }


    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter
    private List<Product> products = new ArrayList<>();

    public Store(String name, String location, String phoneNumber) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

}
