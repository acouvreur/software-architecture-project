package fr.unice.polytech.al.model;

import fr.unice.polytech.al.State;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
public class Announcement {

    @Id
    @Column(unique = true, nullable = false)
    private Long idGoodAnnouncement;

    @Column
    private Long idDriverAnnouncement;

    public String getNameTransmitter() {
        return nameTransmitter;
    }

    @Column(name = "nameTransmistter")
    private String nameTransmitter;

    @Column(name = "startPoint")
    private String startPoint;

    @Column(name = "endPoint")
    private String endPoint;

    @Column(name = "startDate")
    private String startDate;

    @Column(name = "endDate")
    private String endDate;

    @Enumerated(EnumType.STRING)
    //private State state;
    private State state;


    public Announcement() {}

    public Announcement(Long idGoodAnnouncement, Long idDriverAnnouncement, String nameTransmitter, String startPoint, String endPoint, String startDate, String endDate, State state) {
        this.idGoodAnnouncement = idGoodAnnouncement;
        this.idDriverAnnouncement = idDriverAnnouncement;
        this.setNameTransmitter(nameTransmitter);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
    }

    public Long getIdGoodAnnouncement() {
            return idGoodAnnouncement;
    }

    public void setIdGoodAnnouncement(Long idTracking) {
            this.idGoodAnnouncement = idTracking;
        }

    public Long getIdDriverAnnouncement() {
        return idDriverAnnouncement;
    }

    public void setIdDriverAnnouncement(Long idDriverAnnouncement) {
        this.idDriverAnnouncement = idDriverAnnouncement;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setNameTransmitter(String nameTransmitter) {
        this.nameTransmitter = nameTransmitter;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
