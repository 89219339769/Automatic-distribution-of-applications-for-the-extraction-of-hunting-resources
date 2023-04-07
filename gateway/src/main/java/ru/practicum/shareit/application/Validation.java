package ru.practicum.shareit.application;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.application.model.Application;

@Component
public class Validation {
    private static final long QUOTUM = 100;
    private static final String district = "Московский";


    public boolean validate(Application application) {
        if (application.getRequestedResources().getQuantity() < QUOTUM &&
                application.getRequestedResources().getDistrict().equals(district)) {
            return true;
        } else return false;
    }

}
