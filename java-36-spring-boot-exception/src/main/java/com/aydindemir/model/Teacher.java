package com.aydindemir.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Ad alanı boş bırakılamaz.")
    @Size(
        min = 2,
        max = 100,
        message = "Ad 2 ile 100 karakter arasında olmalıdır."
    )
    @Column(
        name = "FIRST_NAME",
        length = 100,
        nullable = false
    )
    private String firstName;

    @NotBlank(message = "Soyad alanı boş bırakılamaz.")
    @Size(
        min = 2,
        max = 150,
        message = "Soyad 2 ile 150 karakter arasında olmalıdır."
    )
    @Column(
        name = "LAST_NAME",
        length = 150,
        nullable = false
    )
    private String lastName;

    @NotBlank(message = "Email alanı boş bırakılamaz.")
    @Email(message = "Geçerli bir email adresi giriniz.")
    @Size(
        max = 150,
        message = "Email en fazla 150 karakter olabilir."
    )
    @Column(
        name = "EMAIL",
        length = 150,
        nullable = false,
        unique = true
    )
    private String email;

    @NotBlank(message = "Telefon alanı boş bırakılamaz.")
    @Pattern(
        regexp = "^\\+?[0-9]{10,15}$",
        message = "Telefon numarası 10-15 rakamdan oluşmalıdır."
    )
    @Column(
        name = "PHONE",
        length = 20,
        nullable = false
    )
    private String phone;
}