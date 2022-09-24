package com.etstur.hotelbooking.dao.impl;

import com.etstur.hotelbooking.dao.ReservationDao;
import com.etstur.hotelbooking.entity.Reservation;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class ReservationDaoImpl implements ReservationDao {

    private static EntityManager entityManager;

    private Session currentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public Reservation getReservationForLoggedUserById(int id) {

        Query<Reservation> query = currentSession().createQuery("from Reservation where id=:id", Reservation.class);

        query.setParameter("id", id);

        return query.getSingleResult();
    }

    @Override
    public Collection<Reservation> getReservationsByUserId(int userId) {

        Query<Reservation> query = currentSession().createQuery("from Reservation where userId=:userId", Reservation.class);

        query.setParameter("userId", userId);

        return query.getResultList();
    }

    @Override
    public void saveOrUpdateReservation(Reservation reservation) {
        currentSession().saveOrUpdate(reservation);
    }

    @Override
    public void deleteReservationById(Reservation reservation) {
        currentSession().delete(reservation);
    }
}
