package fr.unice.polytech.al.model;

import javax.persistence.*;

@Entity
public class Billing {

    @Id
    private Long clientId;

    @Column(unique = true, nullable = false)
    private double points;

    public Billing () {}

    public Billing (Long clientid, int points) {
        this.clientId = clientid;
        this.points = points;
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

    public void setPoints(int points) {
        this.points = points;
    }

}
