package com.etstur.hotelbooking.dao;

import com.etstur.hotelbooking.entity.Reservation;

import java.util.Collection;

//Dao interface for Reservation entity
public interface ReservationDao {

    public Reservation getReservationForLoggedUserById(Long id);


    public Collection<Reservation> getReservationsByUserId(Long userId);

    public void saveOrUpdateReservation(Reservation reservation);

    public void deleteReservationById(Reservation reservation);
}
