package com.cada.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Base64;


@Entity
@Data // Lombok: Generates getters, setters, equals, hashCode, and toString
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

    
     // Prevent sending raw bytes in JSON response
    @Getter @Setter
    @JsonIgnore
    private byte[] photo;
    @JsonIgnore
    public String getPhotoBase64() {
        return photo != null ? Base64.getEncoder().encodeToString(photo) : null;
    }

    
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)  // Foreign key linking to Store
    @JsonIgnore
    private Store s;

    public String getStoreName() {
        return s != null ? s.getName() : null;
    }

    public String store = getStoreName();
}
