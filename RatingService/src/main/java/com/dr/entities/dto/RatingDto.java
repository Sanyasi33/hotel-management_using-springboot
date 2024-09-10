package com.dr.entities.dto;

import lombok.Data;

@Data
public class RatingDto {

    private String customerId;
    private String hotelId;
    private int rating;
    private String feedback;
}
