package com.etstur.hotelbooking.services.impl;

import com.etstur.hotelbooking.entity.Reservation;
import com.etstur.hotelbooking.repository.ReservationRepository;
import com.etstur.hotelbooking.services.ReservationService;
import com.etstur.hotelbooking.services.UserService;
import com.etstur.hotelbooking.temp.CurrentReservation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {


    private final UserService userService;
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(UserService userService, ReservationRepository reservationRepository) {
        this.userService = userService;
        this.reservationRepository = reservationRepository;
    }

    //get reservation for logged user
    @Override
    @Transactional
    public Optional<Reservation> getReservationForLoggedUserById(Long id) {
        return reservationRepository.findById(id);
    }

    //get all reservations for logged user
    @Override
    @Transactional
    public Collection<Reservation> getReservationsForLoggedUser() {
        return reservationRepository.findAllByUserId(userService.getLoggedUserId());
    }

    // transfer data between temp reservation and
    // Reservation class after check it to save it in database
    @Override
    @Transactional
    public void saveOrUpdateReservation(CurrentReservation reservation) {
        Reservation newReservation = new Reservation();

        //get Required id user using user service

        newReservation.setUserId((long) userService.getLoggedUserId());

        newReservation.setArrivalDate(reservation.getArrivalDate());
        newReservation.setChildren(reservation.getChildren());
        newReservation.setPersons(reservation.getPersons());
        newReservation.setPrice(reservation.getPrice());
        newReservation.setRoom(reservation.getRoom());
        newReservation.setOpenBuffet(reservation.getOpenBuffet());
        newReservation.setStayDays(reservation.getStayPeriod());
        newReservation.setId(reservation.getId());

        reservationRepository.save(newReservation);
    }

    // transfer data between Reservation and temp Reservation class update request
    @Override
    public CurrentReservation reservationToCurrentReservation(Long resId) {
        Optional<Reservation> reservation = getReservationForLoggedUserById(resId);
        CurrentReservation currentReservation = new CurrentReservation();

        currentReservation.setArrivalDate(reservation.get().getArrivalDate());
        currentReservation.setChildren(reservation.get().getChildren());
        currentReservation.setPersons(reservation.get().getPersons());
        currentReservation.setPrice((int) reservation.get().getPrice());
        currentReservation.setRoom(reservation.get().getRoom());
        currentReservation.setOpenBuffet(reservation.get().getOpenBuffet());
        currentReservation.setStayPeriod(reservation.get().getStayDays());
        currentReservation.setId(reservation.get().getId());

        return currentReservation;
    }


    @Override
    @Transactional
    public void deleteReservation(Long resId) {
        reservationRepository.deleteById(resId);
    }

}
