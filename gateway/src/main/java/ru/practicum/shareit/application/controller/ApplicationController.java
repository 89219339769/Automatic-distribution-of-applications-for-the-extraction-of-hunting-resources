package ru.practicum.shareit.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.shareit.application.ApplicationClient;
import ru.practicum.shareit.application.Generation;
import ru.practicum.shareit.application.Validation;
import ru.practicum.shareit.application.model.Application;
import ru.practicum.shareit.application.model.ApplicationStatus;


@Controller
@RequestMapping(path = "/application")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ApplicationController {
    private final Validation validation;
    private final ApplicationClient appClient;

    private final Generation generation;

    @PostMapping
    public ResponseEntity<Object> postUser(@RequestBody Application application) {

        if (validation.validate(application)) {
            application.setStatus(ApplicationStatus.APPROVED);
            appClient.postApplication(application);
            return appClient.postApplication(application);
        } else {
            throw new RuntimeException("заявка не одобрена");
        }
    }
}
