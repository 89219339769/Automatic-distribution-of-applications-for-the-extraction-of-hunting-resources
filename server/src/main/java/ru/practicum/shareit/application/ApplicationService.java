package ru.practicum.shareit.application;


import ru.practicum.shareit.application.model.Application;
import ru.practicum.shareit.application.model.ApplicationDto;

public interface ApplicationService {
    ApplicationDto save(Application application);
}
