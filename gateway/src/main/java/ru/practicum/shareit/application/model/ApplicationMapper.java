package ru.practicum.shareit.application.model;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class ApplicationMapper {


    public static ApplicationDto toApplication(Application application) {
        return ApplicationDto.builder()
                          .id(application.getId())
                          .fullName(application.getFullName())
                          .type(application.getType())
                          .dateOfIssue(application.getDateOfIssue())
                          .series(application.getSeries())
                          .number(application.getNumber())
                          .requestedResources(application.getRequestedResources())
                          .status(application.getStatus())

                          .build();
    }


}
