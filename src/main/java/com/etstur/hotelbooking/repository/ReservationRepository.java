package com.etstur.hotelbooking.repository;

import com.etstur.hotelbooking.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findById(int id);

    Collection<Reservation> findAllByUserId(int id);

}
