package com.priya.depra.Review;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@Setter
@AllArgsConstructor
public class ReviewResponsedto {

    private Long id;
    private String userName;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

}
