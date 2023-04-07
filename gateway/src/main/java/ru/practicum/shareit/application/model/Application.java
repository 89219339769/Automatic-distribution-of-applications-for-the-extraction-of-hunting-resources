package ru.practicum.shareit.application.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    private Long id;


    private String fullName;


    private ApplicationType type;



    private LocalDate dateOfIssue;


    private Integer series;


    private Integer number;



    private RequestedResources requestedResources;



    private ApplicationStatus status;

}