package com.dr.services;

import com.dr.entities.db.Rating;
import com.dr.entities.dto.RatingDto;

import java.util.List;

public interface IRatingService {

    //Crate rating
    Rating createRating(RatingDto ratingDto);

    //Get all ratings
    List<Rating> getAllRatings();

    //Get all ratings by customerId
    List<Rating> getAllRatingsByCustomerId(String customerId);

    //Get all ratings by hotelId
    List<Rating> getAllRatingsByHotelId(String hotelId);

    //Delete ratings by hotelId
    void deleteRatingsByHotelId(String hotelId);

}
