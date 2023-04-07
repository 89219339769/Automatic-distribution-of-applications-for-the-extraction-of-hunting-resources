package ru.practicum.shareit.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.application.model.Application;
import ru.practicum.shareit.application.model.ApplicationDto;
import ru.practicum.shareit.application.model.ApplicationMapper;


@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    private final ResourceRepository resourceRepository;
    @Override
    public ApplicationDto save(Application application) {

        System.out.println(application);

        resourceRepository.save(application.getRequestedResources());
        applicationRepository.save(application);


        return ApplicationMapper.toApplication(application);
    }
}
