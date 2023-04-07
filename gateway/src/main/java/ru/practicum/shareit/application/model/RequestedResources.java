package ru.practicum.shareit.application.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestedResources {


    private Long id;


    private String district;


    private String name;

    private Integer quantity;
    private ResourceStatus resourcesStatus;


}
