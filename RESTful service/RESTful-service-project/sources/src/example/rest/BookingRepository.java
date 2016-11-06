package example.rest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    //Collection<Booking> findByBookingName(String name);
    //@Query(value = "SELECT * FROM Booking", nativeQuery = true)
    Collection<Booking> findAllByName(String name);
    Collection<Booking> findAllById(Integer id);
    List<Booking> findAll();
}