package example.rest;

import java.util.Collection;
import java.util.List;

public interface BookingService {
    Booking save(Booking booking);
    Collection<Booking> findById(Integer id);
    List<Booking> findAll();

}
