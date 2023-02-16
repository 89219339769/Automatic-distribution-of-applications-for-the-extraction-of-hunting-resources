package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.BadRequestException;

import javax.validation.Valid;



@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class RequestController {

    private final RequestClient requestClient;


    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader("X-Sharer-User-Id") Long userId,
                                      @RequestBody @Valid Request request) {


        return requestClient.addRequest(userId, request);
    }


    @GetMapping("/all")
    public ResponseEntity<Object> getAllWithItems(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size) {
        if (from < 0 || size < 0) {
            throw new BadRequestException(" параметр from и size не может быть отрицательным ");
        }
        return requestClient.getAllRequestsWithItems(userId, from, size);
    }


    @GetMapping()
    public ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestClient.getAllRequestsByUser(userId);
    }


    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @PathVariable Long requestId) {
        requestClient.getRequestById(userId, requestId);
        return requestClient.getRequestById(userId, requestId);
    }
}
