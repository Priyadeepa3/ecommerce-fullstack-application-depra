package com.priya.depra.Notification;

import com.priya.depra.User.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class Notificationdto {
    private Long id;

    private String title;

    private String message;

    private boolean isRead = false;

    private LocalDateTime createdAt;

    
}
