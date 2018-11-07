package fr.unice.polytech.al.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Tracking {

    @Id
    @Column(unique = true, nullable = false)
    private Long clientId;

    @Column(unique = false)
    private int position;

    @Column(unique = false)
    private List<Course> courses;

    public Tracking () {}


    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
