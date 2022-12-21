package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDtoShort;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.BookingStatus.*;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final ItemMapper itemMapper;

    private final BookingMapper bookingMapper;

    @Override
    public BookingDtoShort saveBooking(long userId, Booking booking) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден бронирующий с номером: " + userId));

        Item item = itemRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Невозможно найти вещь с номером " + booking.getItem()));
        booking.setBooker(user);


        if (item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Невозможно создать бронирование - " +
                    "пользователь не может забронировать принадлежащую ему вещь");
        }


//        if (!booking.getStatus().equals(BookingStatus.WAITING)) {
//            throw new BadRequestException("Невозможно подтвердить бронирование - " +
//                    "бронирование уже подтверждено или отклонено");
//        }


        booking.setItem(item);
        booking.setStatus(WAITING);
        bookingRepository.save(booking);

        return bookingMapper.bookingToBookingDtoShort(booking);

    }

    @Override
    public BookingDtoShort getById(long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Не найдена бронь с номером: " + bookingId));
        return bookingMapper.bookingToBookingDtoShort(booking);

    }


    @Override
    public BookingDtoShort approve(Long bookingId, Long userId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Не найдена бронь с номером: " + bookingId));
        Item item = booking.getItem();
        User owner = item.getOwner();
        Long ownerId = owner.getId();
        if (!ownerId.equals(userId)) {
            throw new NotFoundException("не найдено вещи владельца с номером " + userId);
        }
        if (approved = true)
            booking.setStatus(APPROVED);
        if (approved = false)
            booking.setStatus(REJECTED);
        bookingRepository.save(booking);

        return bookingMapper.bookingToBookingDtoShort(booking);
    }

    @Override
    public List<Booking> getAllBokingsSortByState(Long userId, String state) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден бронирующий с номером: " + userId));
       List<Booking> allBookings = new ArrayList<>();
//             if(!bookingRepository.findAll().contains(user)){
//                 throw new NotFoundException(" Пользователь не найден");}
//
//        allBookings  =  bookingRepository.findAll().stream()
//                .map(Booking::getBooker)
//                .filter(id -> id.getId().equals(userId))
//                .collect(Collectors.toList());

    //    allBookings.addAll(bookingRepository.findAll().(user))
               //bookingRepository.findAll();
//        List<Booking> userBookings = new ArrayList<>();
//        for (int i = 0; i < bookingRepository.findAll().size(); i++) {
//            if(bookingRepository.findAll().get(i).getBooker().equals(user)){
//                userBookings.add(allBookings.get(i));
//            }
 //       }



        switch (state) {
            case "ALL":
                allBookings.addAll(bookingRepository.findAllByBooker(user));
                break;
            case "CURRENT":
                allBookings.addAll(bookingRepository.findAllByBookerAndStartBeforeAndEndAfter(user,
                        LocalDateTime.now(), LocalDateTime.now()));
                break;
            case "PAST":
                allBookings.addAll(bookingRepository.findAllByBookerAndEndBefore(user,
                        LocalDateTime.now()));
                break;
            case "FUTURE":
                allBookings.addAll(bookingRepository.findAllByBookerAndStartAfter(user, LocalDateTime.now()));
                break;
            case "WAITING":
                allBookings.addAll(bookingRepository.findAllByBookerAndStatusEquals(user, BookingStatus.WAITING));
                break;
            case "REJECTED":
                allBookings.addAll(bookingRepository.findAllByBookerAndStatusEquals(user, BookingStatus.REJECTED));
                break;
        }

//        return bookingDtoList
//                .stream()
//                .map(BookingMapper::toBookingDto)
//                .collect(Collectors.toList());
        return allBookings;
    }







}
