package com.dr.feigns;

import com.dr.entities.dto.RatingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("RATINGSERVICE")
//@RequestMapping("/rating") //If I enable this one then I am getting exception
public interface RatingFeign {

    //Declare the methods to call required APIs from RatingService
    @GetMapping("/rating/customer/{customerId}")
    List<RatingDto> getAllRatingsByCustomerId(@PathVariable String customerId);

}
