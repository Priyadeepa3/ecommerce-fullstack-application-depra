package com.priya.depra.Shipping;

import com.priya.depra.User.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "ShipmentAddress")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ShipmentAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String zip;
    private String postalcode;
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
