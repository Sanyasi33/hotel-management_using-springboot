package com.dr.controllers;

import com.dr.entities.db.Rating;
import com.dr.entities.dto.RatingDto;
import com.dr.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody RatingDto ratingDto){
        Rating rating = ratingService.createRating(ratingDto);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings(){
        List<Rating> ratings = ratingService.getAllRatings();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Rating>> getAllRatingsByCustomerId(@PathVariable String customerId){
        List<Rating> ratings = ratingService.getAllRatingsByCustomerId(customerId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Rating>> getAllRatingsByHotelId(@PathVariable String hotelId){
        List<Rating> ratings = ratingService.getAllRatingsByHotelId(hotelId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @DeleteMapping("/hotel/{hotelId}")
    public ResponseEntity<Void> deleteRatingsByHotelId(@PathVariable String hotelId){
        ratingService.deleteRatingsByHotelId(hotelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
