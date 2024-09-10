package com.dr.services;

import com.dr.entities.db.Rating;
import com.dr.entities.dto.RatingDto;
import com.dr.repositories.RatingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingService implements IRatingService{

    @Autowired
    private RatingRepository ratingRepo;


    @Override
    public Rating createRating(RatingDto ratingDto) {
        Rating rating = new Rating();
        BeanUtils.copyProperties(ratingDto, rating);
        rating.setRatingId(UUID.randomUUID().toString());
        return ratingRepo.save(rating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepo.findAll();
    }

    @Override
    public List<Rating> getAllRatingsByCustomerId(String customerId) {
        return ratingRepo.findRatingsByCustomerId(customerId);
    }

    @Override
    public List<Rating> getAllRatingsByHotelId(String hotelId) {
        return ratingRepo.findRatingsByHotelId(hotelId);
    }

    @Override
    public void deleteRatingsByHotelId(String hotelId) {
        ratingRepo.deleteByHotelId(hotelId);
    }
}
