package com.etstur.hotelbooking.dao;

import com.etstur.hotelbooking.entity.Reservation;

import java.util.Collection;

//Dao interface for Reservation entity
public interface ReservationDao {

    public Reservation getReservationForLoggedUserById(int id);


    public Collection<Reservation> getReservationsByUserId(int userId);

    public void saveOrUpdateReservation(Reservation reservation);

    public void deleteReservationById(Reservation reservation);
}
