package ru.practicum.shareit.application;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.application.model.Application;
import ru.practicum.shareit.application.model.ApplicationDto;
import ru.practicum.shareit.application.model.ApplicationStatus;
import ru.practicum.shareit.application.model.ApplicationType;
import ru.practicum.shareit.application.model.RequestedResources;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class Generation {


    public Application generate() {


        return Application.builder()
                          .id((long) getRandomNumber())
                          .fullName(generateStr())
                          .type(ApplicationType.MASS_SPECIES)
                          .dateOfIssue(LocalDate.from(LocalDateTime.now()))
                          .series((int) getRandomNumber())
                          .number((int) getRandomNumber())
                          .requestedResources(requestedResources)
                          .status(ApplicationStatus.APPROVED)

                          .build();


    }


    RequestedResources requestedResources = new RequestedResources();


    public static int getRandomNumber() {
        int x = (int) Math.random();
        return x;
    }

    public String generateStr() {

        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }
}
