package com.priya.depra.Order;

import com.priya.depra.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);

    @Query("""
SELECT o FROM Order o 
LEFT JOIN FETCH o.items i 
LEFT JOIN FETCH i.product 
WHERE o.id = :orderId
""")
    Optional<Order> findByIdWithItems(@Param("orderId") Long orderId);


}
