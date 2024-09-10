package com.dr.services;

import com.dr.entities.db.Hotel;
import com.dr.entities.dto.HotelDto;
import com.dr.entities.dto.RatingDto;
import com.dr.exceptions.ResourceNotFoundException;
import com.dr.feigns.CustomerFeign;
import com.dr.feigns.RatingFeign;
import com.dr.repositories.HotelRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelService implements IHotelService{

    @Autowired
    private HotelRepository hotelRepo;

    @Autowired
    private RatingFeign ratingFeign;

    @Autowired
    private CustomerFeign customerFeign;

    @Override
    public Hotel createHotel(HotelDto hotelDto) {
        Hotel hotel = new Hotel();
        BeanUtils.copyProperties(hotelDto, hotel);
        String randomId = UUID.randomUUID().toString();
        hotel.setHotelId(randomId);
        return hotelRepo.save(hotel);
    }

    /*@Override   //Without Ratings ***************************
    public Hotel getHotel(String hotelId) {
        Optional<Hotel> opt = hotelRepo.findById(hotelId);
        return opt.orElseThrow(ResourceNotFoundException::new);
    }*/
    @Override   //Getting Ratings using FeignClient ***************************
    public Hotel getHotel(String hotelId) {
        Optional<Hotel> opt = hotelRepo.findById(hotelId);
        if (opt.isPresent()){
            Hotel hotel = opt.get();
            hotel.setRatings(ratingFeign.getAllRatingsByHotelId(hotelId));
            return hotel;
        }
        throw new ResourceNotFoundException("Hotel not found with id: " + hotelId);
    }

    /*@Override   //Without Ratings ***************************
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }*/
    @Override   //Getting Ratings using FeignClient ***************************
    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = hotelRepo.findAll();
        hotels.forEach(hotel->hotel.setRatings(ratingFeign.getAllRatingsByHotelId(hotel.getHotelId())));
        return hotels;
    }

    @Override
    public Hotel updateHotel(String hotelId, HotelDto hotelDto) {
        Optional<Hotel> opt = hotelRepo.findById(hotelId);
        if (opt.isPresent()){
            Hotel hotel = opt.get();
            BeanUtils.copyProperties(hotelDto, hotel);
            return hotelRepo.save(hotel);
        }
        throw new ResourceNotFoundException("Hotel not found with id: " + hotelId);
    }

    @Override
    public void deleteHotel(String hotelId) {
        Optional<Hotel> opt = hotelRepo.findById(hotelId);
        if (opt.isPresent()){
            hotelRepo.deleteById(hotelId);
            ratingFeign.deleteRatingsByHotelId(hotelId);    //Delete ratings of this hotel
        }
        else {
            throw new ResourceNotFoundException("Hotel not found with id: " + hotelId);
        }
    }

    @Override
    public RatingDto giveRating(String hotelId, RatingDto ratingDto) {
        Optional<Hotel> opt = hotelRepo.findById(hotelId);
        try{
            // Checking customer exist or not. If not exist then Customer service throw exception
            customerFeign.getCustomer(ratingDto.getCustomerId());
        }
        catch (Exception e){
            throw new ResourceNotFoundException("Customer not found with id: "+ratingDto.getCustomerId());
        }
        if (opt.isPresent()){
            ratingDto.setHotelId(hotelId);
            return ratingFeign.giveRating(ratingDto);
        }
        throw new ResourceNotFoundException("Hotel not found with id: "+hotelId+" You can not rate");
    }
}
