package com.pickfit.pickfit.oauth2.model.entity;

import com.pickfit.pickfit.multipartupload.entity.UploadEntity;
import com.pickfit.pickfit.oauth2.model.enums.AuthProvider;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadEntity> uploadedImages = new ArrayList<>();

    // provider 필드의 getter와 setter
    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    // uploadedImages 리스트에 추가하는 메서드
    public void addUploadedImage(UploadEntity uploadEntity) {
        uploadedImages.add(uploadEntity);
        uploadEntity.setUser(this); // 양방향 관계 설정
    }

    // Getter 및 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UploadEntity> getUploadedImages() {
        return uploadedImages;
    }
}