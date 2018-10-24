package fr.unice.polytech.al.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Transmitter implements Serializable{

    @Column(name = "transmistter_id", nullable = false)
    private int id;

    @Column(name = "transmistter_name", nullable = false)
    private String name;

    public Transmitter(){}

    public Transmitter(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Transmitter[id=%d, name='%s']", this.id, this.name);
    }
}
