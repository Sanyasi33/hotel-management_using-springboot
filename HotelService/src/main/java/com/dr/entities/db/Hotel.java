package com.dr.entities.db;

import com.dr.entities.dto.RatingDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Hotel {

    @Id
    private String hotelId;
    private String name;
    private String location;

    @Transient
    private List<RatingDto> ratings = new ArrayList<>();
}
