package ru.practicum.shareit.application;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.application.model.Application;


public interface ApplicationRepository extends JpaRepository<Application, Long> {


}
