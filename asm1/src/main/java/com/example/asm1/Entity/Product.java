package com.example.asm1.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; //
    private String category; //
    private Double price; //
    private String gender; // 
    
    @Column(name = "image_url")
    private String imageUrl; // Đây chính là biến để thay đường dẫn ảnh

    @Column(name = "is_featured")
    private Boolean isFeatured;

    // Getters and Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Boolean getIsFeatured() {
        return isFeatured;
    }
    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }
    public String getGender() {
        return gender;
    }

}