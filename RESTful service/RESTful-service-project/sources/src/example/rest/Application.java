package example.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) throws SQLException {
        //org.h2.tools.Server.createTcpServer().start();
        //C:\Users\Lena\.m2\repository\com\h2database\h2\1.4.190
        SpringApplication.run(Application.class, args);
    }
}

@Component
class BookingCommandLineRunner implements CommandLineRunner {
    @Autowired BookingRepository bookingRepository;

    @Override
    public void run(String... strings) throws Exception {
        for(Booking book : this.bookingRepository.findAll()) {
            System.out.println(book.toString());
        }
    }
}

interface BookingRepository extends JpaRepository<Booking, Long> {

    //Collection<Booking> findByBookingName(String name);
    //@Query(value = "SELECT * FROM Booking", nativeQuery = true)
    Collection<Booking> findAllByName(String name);
    List<Booking> findAll();
}

@RestController
class BookingRestController {
    @RequestMapping("/bookings")
    List<Booking> bookings() {
       /* List<Booking> list = new ArrayList<Booking>();
        list.add(new Booking("Ass"));
        list.add(new Booking("new Ass"));//, 22));
        return list;*/
       return this.bookingRepository.findAll();
    }

    @Autowired
    BookingRepository bookingRepository;
}

@Entity
class Booking {
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    public Booking(String name) {
        super();
        this.name = name;
       //this.id = id;
    }

    public Booking() {

    }
}

