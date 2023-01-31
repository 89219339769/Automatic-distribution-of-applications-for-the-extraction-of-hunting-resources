package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.comment.Comment;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {

    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> createItem(long userId, Item item) {

        return post("", userId, item);
    }

    public ResponseEntity<Object> findItemById(Long id, Long userId) {

        return get("/" + id, userId);


    }

    public ResponseEntity<Object> updateItem(Long userId, Long itemId, Item item) {

        return patch("/" + itemId, userId, item);
    }

    public ResponseEntity<Object> getAllItems(long userId) {

        return get("", userId);
    }


    public ResponseEntity<Object> findItemByNameOrDescription(String text) {
        Map<String, Object> parameters = Map.of(
                "text", text
        );
        return get("/search?text={text}", null, parameters);
    }


    public ResponseEntity<Object> addComment(Long userId, Long itemId, Comment comment) {

        return post("/" + itemId + "/comment", userId, comment);
    }
}