package com.dr.controllers;

import com.dr.entities.db.Hotel;
import com.dr.entities.dto.HotelDto;
import com.dr.entities.dto.RatingDto;
import com.dr.services.HotelService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody HotelDto hotelDto){
        Hotel hotel = hotelService.createHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    //int retryCount = 1;
    @GetMapping("/{hotelId}")
    //@Retry(name = "ratingService", fallbackMethod = "ratingFallbackMethod")
    @RateLimiter(name = "ratingService", fallbackMethod = "ratingFallbackMethod")
    public ResponseEntity<Hotel> getHotel(@PathVariable String hotelId){
        //log.info("Retry count: {}", retryCount);
        //retryCount++;
        Hotel hotel = hotelService.getHotel(hotelId);
        return ResponseEntity.ok(hotel);
    }
    //Fallback Method
    public ResponseEntity<Hotel> ratingFallbackMethod(@PathVariable String hotelId, Exception e){
        log.error("Fallback method invoked due to: {}", e.getMessage());
        Hotel hotel = new Hotel();
        hotel.setHotelId("Dummy Id");
        hotel.setName("Dummy Name");
        hotel.setLocation("Dummy Location");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(hotel);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels(){
        List<Hotel> hotels = hotelService.getAllHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable String hotelId, @RequestBody HotelDto hotelDto){
        Hotel hotel = hotelService.updateHotel(hotelId, hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String hotelId){
        hotelService.deleteHotel(hotelId);
        //return ResponseEntity.noContent().build();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint to give rating
    @PostMapping("/giveRating/{hotelId}")
    public ResponseEntity<RatingDto> giveRating(@PathVariable String hotelId, @RequestBody RatingDto ratingDto){
        RatingDto savedRatingDto = hotelService.giveRating(hotelId, ratingDto);
        return new ResponseEntity<>(savedRatingDto, HttpStatus.OK);
    }

}
