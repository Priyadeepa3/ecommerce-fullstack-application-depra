package com.priya.depra.Review;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class CreateReviewdto {

    private Long productId;
    private int rating;
    private String comment;

}
