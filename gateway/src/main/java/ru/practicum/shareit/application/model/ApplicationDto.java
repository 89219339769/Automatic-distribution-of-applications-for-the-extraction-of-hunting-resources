package ru.practicum.shareit.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationDto {


    private Long id;


    private String fullName;


    private ApplicationType type;



    private LocalDate dateOfIssue;


    private Integer series;


    private Integer number;


    private RequestedResources requestedResources;


    private ApplicationStatus status;




}
