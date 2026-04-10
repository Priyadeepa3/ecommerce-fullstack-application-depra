package com.priya.depra.Address;

import com.priya.depra.User.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AddressService {


    Address addAddress(User user, Address address);

    List<Address> getUserAddresses(User user);

    Address updateAddress(Long id, Address address, User user);

    void deleteAddress(Long id, User user);


}

