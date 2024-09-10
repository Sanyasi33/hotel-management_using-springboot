package com.dr.entities.dto;

import lombok.Data;

@Data
public class RatingDto {

    /****** Field names must be same with Rating entity field names of RatingService project ******/

    private String ratingId;
    private String hotelId;
    private int rating;
    private String feedback;
}
