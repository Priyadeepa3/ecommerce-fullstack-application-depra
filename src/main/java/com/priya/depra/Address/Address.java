package com.priya.depra.Address;

import com.priya.depra.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "Address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "full_name")
    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 10, max = 10)
    private String phone;

    @NotBlank
    private String street;   // House No, Area

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    @Size(min = 6, max = 6)
    private String pincode;

    private boolean isDefault;



}

