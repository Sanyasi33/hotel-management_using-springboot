package com.dr.entities.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Rating {

    @Id
    private String ratingId;
    private String customerId;
    private String hotelId;
    private int rating;
    private String feedback;

}
