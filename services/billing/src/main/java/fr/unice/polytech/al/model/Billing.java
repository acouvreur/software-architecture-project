package fr.unice.polytech.al.model;

import javax.persistence.*;

@Entity
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long clientId;

    @Column(unique = true, nullable = false)
    private double points;

    public Long getTest() {
        return test;
    }

    public void setTest(Long test) {
        this.test = test;
    }

    @Column(unique = true, nullable = false)
    private Long test;

    public Billing () {}

    public Billing (Long clientid) {
        this.clientId = clientid;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
