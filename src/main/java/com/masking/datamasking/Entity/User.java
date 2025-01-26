package com.masking.datamasking.Entity;

import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.annotations.ValueGenerationType;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.masking.datamasking.Custom.Mask;
import com.masking.datamasking.Custom.MaskSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 50) 
    private String username;

    @JsonSerialize(using = MaskSerializer.class)
    @Mask
    @Column(nullable = false, unique = true, length = 80)  
    private String email;

    @Column(nullable = false) 
	private String password;

    @Column(nullable = false, length = 10) 
	private String roles;


    @Column(nullable = false) 
    private boolean validated;

    // @JsonSerialize(using = MaskSerializer.class)
    @Mask
    @Column(nullable = true, length = 15) 
    private String mobile;

}
