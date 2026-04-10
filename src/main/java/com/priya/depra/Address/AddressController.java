package com.priya.depra.Address;

import com.priya.depra.Security.CustomUserDetailsService;
import com.priya.depra.User.User;
import com.priya.depra.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Address> addAddress(
            Principal principal,
            @RequestBody Address request) {

        String email = principal.getName();

        User user =
                userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        request.setUser(user);

        return ResponseEntity.ok(addressRepository.save(request));
    }

    @GetMapping
    public List<Address> getAddresses(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return addressService.getUserAddresses(user);
    }

    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable Long id,
                                 @RequestBody Address address,
                                 Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return addressService.updateAddress(id, address, user);
    }


    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id,
                                 @RequestBody Address address,
                                 Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        addressService.deleteAddress(id, user);
    }

}