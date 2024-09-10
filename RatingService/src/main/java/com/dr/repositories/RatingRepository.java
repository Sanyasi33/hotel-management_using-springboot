package com.dr.repositories;

import com.dr.entities.db.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String> {

    //Find ratings by customerId
    List<Rating> findRatingsByCustomerId(String customerId);

    //Find ratings by hotelId
    List<Rating> findRatingsByHotelId(String hotelId);

    @Transactional
    void deleteByHotelId(String hotelId);

}
