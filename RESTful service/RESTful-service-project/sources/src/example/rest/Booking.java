package example.rest;

import javax.persistence.*;

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
    //@Column(name = "name")
    private String name;

    public Booking(String name) {
        super();
        this.name = name;
        //this.id = id;
    }

    public Booking() {

    }
}
