package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {


//    List<Booking> findAllByItemIdOrderByStartAsc(Long itemId);

    //   List<Booking> findAllByItemIdOrderByStartDesc(Long itemId);


    //   List<Booking> findAllByBookerIdAndItemIdAndStatusEqualsAndEndIsBefore(Long bookerId, Long itemId,
    //                                                                         BookingStatus status, LocalDateTime end);


    //all
    List<Booking> findAllByBookerIdOrderByStartDesc(Long userId);

    //current
    List<Booking> findAllByBookerAndStartBeforeAndEndAfter(User booker, LocalDateTime start, LocalDateTime end);


    //past
    List<Booking> findAllByBookerAndEndBefore(User booker, LocalDateTime end);


    //future
    List<Booking> findAllByBookerIdAndStartIsAfterOrderByStartDesc(Long userId, LocalDateTime start);

    //status
    List<Booking> findAllByBookerAndStatusEquals(User booker, BookingStatus status);

//    Page<Booking> findAllByItemOwner(User owner, Pageable pageable);
//
//    Page<Booking> findAllByItemOwnerAndStartBeforeAndEndAfter(User owner, LocalDateTime start,
//                                                              LocalDateTime end, Pageable pageable);
//
//    Page<Booking> findAllByItemOwnerAndEndBefore(User owner, LocalDateTime end, Pageable pageable);
//
//    Page<Booking> findAllByItemOwnerAndStartAfter(User owner, LocalDateTime start, Pageable pageable);
//
//    Page<Booking> findAllByItemOwnerAndStatusEquals(User owner, BookingStatus status, Pageable pageable);

    @Query("select b from Booking b Inner join Item i on b.item.id = i.id where i.owner.id = ?1 " +
            "order by b.start desc")
    List<Booking> getAllUsersItemsBookings(Long userId);



    @Query("select b from Booking b Inner join Item i on b.item.id = i.id where i.owner.id = ?1 " +
            "and b.start > ?2 order by b.start desc")
    List<Booking> getFutureUsersItemsBookings(Long userId, LocalDateTime nowTime);

   List<Booking> findAllByItemOwnerAndEndBefore(User owner, LocalDateTime end);


   List<Booking> findAllByItemOwnerAndStartBeforeAndEndAfter(User owner, LocalDateTime start,
                                                              LocalDateTime end);


   List<Booking> findAllByItemOwnerAndStatusEquals(User owner, BookingStatus status);

}
