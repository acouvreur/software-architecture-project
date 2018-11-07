package fr.unice.polytech.al.model;

import javax.persistence.*;

@Entity
    public class Billing {

        @Id
        @Column(unique = true, nullable = false)
        private Long clientId;

        @Column(unique = false, nullable = false)
        private int points;

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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
