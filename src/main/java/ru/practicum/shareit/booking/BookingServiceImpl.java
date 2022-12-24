package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDtoShort;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.BookingShortDtoWithItemId;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ItemUnvailableException;
import ru.practicum.shareit.exceptions.WrongTimeException;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.booking.BookingStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final ItemMapper itemMapper;

    private final BookingMapper bookingMapper;

    @Override
    public BookingDtoShort saveBooking(long userId, BookingShortDtoWithItemId bookingShortDtoWithItemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден бронирующий с номером: " + userId));

        Item item = itemRepository.findById(bookingShortDtoWithItemId.getItemId())
                .orElseThrow(() -> new NotFoundException("Невозможно найти вещь с номером "));


        if (item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Невозможно создать бронирование - " +
                    "пользователь не может забронировать принадлежащую ему вещь");
        }

        if (item.getAvailable() == false) {
            throw new ItemUnvailableException("Вещь недоступна");
        }


        Booking booking = bookingMapper.toBooking(bookingShortDtoWithItemId);
        booking.setBooker(user);
        booking.setItem(item);

        if (booking.getStart().isAfter(booking.getEnd())) {
            throw new WrongTimeException("ошибка с датами бронирования");
        }


        if (booking.getEnd().isBefore(LocalDateTime.now())) {
            throw new WrongTimeException("ошибка с датами бронирования");
        }

        if (booking.getStart().isBefore(LocalDateTime.now())) {
            throw new WrongTimeException("ошибка с датами бронирования");
        }


        booking.setStatus(WAITING);
        bookingRepository.save(booking);

        return bookingMapper.bookingToBookingDtoShort(booking);

    }

    @Override
    public BookingDtoShort getById(long userId, long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Не найдена бронь с номером: " + bookingId));

        if (booking.getBooker().getId().equals(userId) || booking.getItem().getOwner().getId().equals(userId)) {
            return bookingMapper.bookingToBookingDtoShort(booking);
        } else {
            throw new NotFoundException("Only owner of the item or booker can view information about booking");
        }
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
        if (!booking.getStatus().equals(BookingStatus.WAITING)) {
            throw new  WrongTimeException("Невозможно подтвердить бронирование - " +
                    "бронирование уже подтверждено или отклонено");
        }


        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        bookingRepository.save(booking);

        return bookingMapper.bookingToBookingDtoShort(booking);
    }

    @Override
    public List<Booking> getAllBokingsSortByState(Long userId, String state) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден бронирующий с номером: " + userId));
        List<Booking> allBookings = new ArrayList<>();
        switch (state) {
            case "ALL":
                allBookings.addAll(bookingRepository.findAllByBookerIdOrderByStartDesc(userId));
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
                allBookings.addAll(bookingRepository.findAllByBookerIdAndStartIsAfterOrderByStartDesc(userId, LocalDateTime.now()));
                break;
            case "WAITING":
                allBookings.addAll(bookingRepository.findAllByBookerAndStatusEquals(user, BookingStatus.WAITING));
                break;
            case "REJECTED":
                allBookings.addAll(bookingRepository.findAllByBookerAndStatusEquals(user, BookingStatus.REJECTED));
                break;
            default:  throw new WrongTimeException("Unknown state: UNSUPPORTED_STATUS");
        }

        return allBookings;
    }


    @Override
    public List<Booking> getAllBokingsByOwnerSortByState(Long userId, String state) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден бронирующий с номером: " + userId));
        List<Booking> allBookings = new ArrayList<>();
        switch (state) {
            case "ALL":
                allBookings.addAll(bookingRepository.getAllUsersItemsBookings(userId));
                break;
            case "CURRENT":
                allBookings.addAll(bookingRepository.findAllByItemOwnerAndStartBeforeAndEndAfter(user,
                        LocalDateTime.now(), LocalDateTime.now()));
               break;
            case "PAST":
                allBookings.addAll(bookingRepository.findAllByItemOwnerAndEndBefore(user,
                        LocalDateTime.now()));
                break;
           case "FUTURE":
               log.info(LocalDateTime.now().toString()+"время создания запроса!!!!!!!!!");
               log.info(bookingRepository.findAll().toString());
               List<Booking> allBookingsNaw = bookingRepository.findAll();
               allBookings.addAll(bookingRepository.getFutureUsersItemsBookings(userId, LocalDateTime.now()));

               log.info(LocalDateTime.now().toString());
                break;
            case "WAITING":
                allBookings.addAll(bookingRepository.findAllByItemOwnerAndStatusEquals(user, BookingStatus.WAITING));
                break;
            case "REJECTED":
                allBookings.addAll(bookingRepository.findAllByItemOwnerAndStatusEquals(user, BookingStatus.REJECTED));
                break;
            default:  throw new WrongTimeException("Unknown state: UNSUPPORTED_STATUS");
        }

        return allBookings;
    }

}
