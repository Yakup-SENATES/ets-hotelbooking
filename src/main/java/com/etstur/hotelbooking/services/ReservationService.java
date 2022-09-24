package com.etstur.hotelbooking.services;

import com.etstur.hotelbooking.entity.Reservation;
import com.etstur.hotelbooking.temp.CurrentReservation;

import java.util.Collection;

public interface ReservationService {


    public Reservation getReservationForLoggedUserById(int id);

    public Collection<Reservation> getReservationsForLoggedUser();

    public void saveOrUpdateReservation(CurrentReservation reservation);

    public void deleteReservation(int resId);

    public  CurrentReservation reservationToCurrentReservation(int resId);
}

