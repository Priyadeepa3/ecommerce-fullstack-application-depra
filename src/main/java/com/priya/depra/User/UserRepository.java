package com.priya.depra.User;

import org.springframework.data.jpa.repository.JpaRepository;
import com.priya.depra.User.User;
import java.util.Optional;


public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);

    boolean existsByEmail(String email);
}
