package com.learnwiremock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Movie {
    private Long movie_id;
    private String name;
    private String cast;
    private Integer year;
    private LocalDate release_date;
}
