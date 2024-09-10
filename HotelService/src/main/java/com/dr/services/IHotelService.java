package com.dr.services;

import com.dr.entities.db.Hotel;
import com.dr.entities.dto.HotelDto;
import com.dr.entities.dto.RatingDto;

import java.util.List;

public interface IHotelService {

    //Create hotel
    Hotel createHotel(HotelDto hotelDto);

    //Get hotel
    Hotel getHotel(String hotelId);

    //Get all hotel
    List<Hotel> getAllHotels();

    //Update hotel
    Hotel updateHotel(String hotelId, HotelDto hotelDto);

    //Delete hotel
    void deleteHotel(String hotelId);

    //Give rating
    RatingDto giveRating(String hotelId, RatingDto ratingDto);
}
