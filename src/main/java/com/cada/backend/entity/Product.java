package com.cada.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Base64;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Product name cannot be empty")
    private String name;

    private String description;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @JsonIgnore
    private Store s;

    // JSON response should include store name
    @JsonProperty("storeName")
    public String getStoreName() {
        return s != null ? s.getName() : null;
    }

    @Getter @Setter
    @JsonIgnore
    private byte[] photo;

    @JsonIgnore
    public String getPhotoBase64() {
        return photo != null ? Base64.getEncoder().encodeToString(photo) : null;
    }
}
