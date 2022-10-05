package com.etstur.hotelbooking.services;

import com.etstur.hotelbooking.entity.Reservation;
import com.etstur.hotelbooking.temp.CurrentReservation;

import java.util.Collection;
import java.util.Optional;

public interface ReservationService {


    public Optional<Reservation> getReservationForLoggedUserById(Long id);

    public Collection<Reservation> getReservationsForLoggedUser();

    public void saveOrUpdateReservation(CurrentReservation reservation);

    public void deleteReservation(Long resId);

    public  CurrentReservation reservationToCurrentReservation(Long resId);
}

