package ru.practicum.shareit.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.application.ApplicationService;
import ru.practicum.shareit.application.model.Application;
import ru.practicum.shareit.application.model.ApplicationDto;
import ru.practicum.shareit.application.model.ApplicationStatus;


@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ApplicationDto create(@RequestBody Application application) {


        applicationService.save(application);
        return applicationService.save(application);

    }
}
