package example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
class BookingRestController {
    /*@Autowired
    BookingRepository bookingRepository;*/
    @Autowired BookingService bookingService;

    @RequestMapping("/bookings")
    List<Booking> bookings() {
       /* List<Booking> list = new ArrayList<Booking>();
        list.add(new Booking("Ass"));
        list.add(new Booking("new Ass"));//, 22));
        return list;*/
        return bookingService.findAll();//this.bookingRepository.findAll();
    }

    @RequestMapping("/bookings/1")
    Collection<Booking> bookings2() {
       /* List<Booking> list = new ArrayList<Booking>();
        list.add(new Booking("Ass"));
        list.add(new Booking("new Ass"));//, 22));
        return list;*/

        return bookingService.findById(1);
    }


}
