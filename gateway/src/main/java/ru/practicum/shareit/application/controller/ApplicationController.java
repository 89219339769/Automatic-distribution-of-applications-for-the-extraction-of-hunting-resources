
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
import ru.practicum.shareit.application.model.Application;


@Controller
@RequestMapping(path = "/application")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ApplicationController {

    private final ApplicationClient appClient;
    @PostMapping
    public ResponseEntity<Object> postUser(@RequestBody Application application) {

        return appClient.postApplication(application);
    }
}