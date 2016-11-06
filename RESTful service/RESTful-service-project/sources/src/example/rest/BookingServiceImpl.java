package example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BookingRepository repository;

    @Override
    @Transactional
    public Booking save(Booking booking) {
        return repository.save(booking);
    }

    @Override
    public Collection<Booking> findById(Integer id) {
        return repository.findAllById(id);
        /*TypedQuery query = em.createQuery("select a from Booking a where a.id = ?1", Booking.class);
        query.setParameter(1, id);

        return query.getResultList();*/
    }

    @Override
    public List<Booking> findAll(){
        return repository.findAll();
    }

}
