package ru.practicum.shareit.application;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.application.model.RequestedResources;

public interface ResourceRepository extends JpaRepository<RequestedResources, Long> {


}
