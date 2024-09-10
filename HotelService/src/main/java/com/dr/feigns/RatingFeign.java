package com.dr.feigns;

import com.dr.entities.dto.RatingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("RATINGSERVICE")
public interface RatingFeign {

    // Call RatingService's API to get rating (This api & method present in RatingService project)
    @GetMapping("/rating/hotel/{hotelId}")
    List<RatingDto> getAllRatingsByHotelId(@PathVariable String hotelId);

    @PostMapping("/rating")
    RatingDto giveRating(@RequestBody RatingDto ratingDto);

    @DeleteMapping("/rating/hotel/{hotelId}")
    void deleteRatingsByHotelId(@PathVariable String hotelId);

}
