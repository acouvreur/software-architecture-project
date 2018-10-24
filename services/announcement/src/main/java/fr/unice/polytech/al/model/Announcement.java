package fr.unice.polytech.al.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Announcement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Transmitter transmitter;

    @Column(name = "start_point", nullable = false)
    private String startPoint;

    @Column(name = "end_point", nullable = false)
    private String endPoint;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private AnnouncementType type;

    public Announcement() {}

    public Announcement(Transmitter t, String startPoint, String endPoint, Date startDate, Date endDate, AnnouncementType type){
        this.transmitter = t;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public Transmitter getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(Transmitter transmitter) {
        this.transmitter = transmitter;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public AnnouncementType getType() {
        return type;
    }

    public void setType(AnnouncementType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(
                "Announcement[id=%d, transmitter='%s', startPoint='%s', endPoint='%s', startDate='%s', endDate='%s']",
                this.id, this.transmitter.toString(), this.startPoint, this.endPoint, this.startDate.toString(), this.endDate.toString());
    }
}
