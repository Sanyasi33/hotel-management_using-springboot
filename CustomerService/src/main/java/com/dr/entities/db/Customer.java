package com.dr.entities.db;

import com.dr.entities.dto.RatingDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Customer {

    @Id
    private String customerId;
    private String name;
    private String email;

    @Transient
    private List<RatingDto> ratings = new ArrayList<>();

}
