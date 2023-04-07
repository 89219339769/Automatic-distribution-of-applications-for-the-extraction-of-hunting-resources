package ru.practicum.shareit.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.application.model.Application;
import ru.practicum.shareit.client.BaseClient;


@Service
public class ApplicationClient extends BaseClient {

    private static final String API_PREFIX = "/application";


    @Autowired
    public ApplicationClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                    .build());
    }



    public ResponseEntity<Object> postApplication(Application application) {
        return post("", application);
    }
}
