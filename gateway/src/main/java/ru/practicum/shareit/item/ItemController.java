package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.Comment;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader("X-Sharer-User-Id") Long userId,
                                      @RequestBody @Valid Item item) {
        return itemClient.createItem(userId, item);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.findItemById(id, userId);
    }


    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> put(@PathVariable long itemId,
                                      @RequestHeader("X-Sharer-User-Id") Long userId,
                                      @RequestBody Item item) {
        return itemClient.updateItem(userId, itemId, item);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getAllItems(userId);
    }


    @GetMapping("search")
    public ResponseEntity<Object> findItemsBySearch(@RequestParam(name = "text") String query) {

        return itemClient.findItemByNameOrDescription(query);
    }


    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> add(@RequestHeader("X-Sharer-User-Id") Long userId,
                                      @PathVariable Long itemId,
                                      @RequestBody @Valid Comment comment) {
        return itemClient.addComment(userId, itemId, comment);
    }
}
