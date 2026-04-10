package com.priya.depra.Wishlist;

import com.priya.depra.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

   // List<Wishlist> findByWishlistId(Integer id);

    // This was missing! The service needs this to fetch a user's list.
    List<Wishlist> findByUser(User user);

    boolean existsByUserAndProduct_Id(User user, Long productId);

}
