package fr.unice.polytech.al.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Announcement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "idTransmistter", nullable = false)
    private int idTransmitter;

   // @Column(name = "idAnother")
    //private int idAnother;

    @Column(name = "nameTransmistter", nullable = false)
    private String nameTransmitter;

    @Column(name = "startPoint", nullable = false)
    private String startPoint;

    @Column(name = "endPoint", nullable = false)
    private String endPoint;

    @Column(name = "startDate", nullable = false)
    private String startDate;

    @Column(name = "endDate", nullable = false)
    private String endDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AnnouncementType type;

    public Announcement() {}

    public Announcement(int idTransmitter, String nameTransmitter, String startPoint, String endPoint, Date startDate, Date endDate, AnnouncementType type){
        this.setIdTransmitter(idTransmitter);
        //this.idAnother = -1;
        this.setNameTransmitter(nameTransmitter);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        this.startDate = dateFormat.format(startDate);
        this.endDate = dateFormat.format(endDate);
        this.type = type;
    }

    /*public int getIdAnother() {
        return idAnother;
    }*/

    /*public void setIdAnother(int idAnother) {
        this.idAnother = idAnother;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdTransmitter() {
        return idTransmitter;
    }

    public void setIdTransmitter(int idTransmitter) {
        this.idTransmitter = idTransmitter;
    }

    public String getNameTransmitter() {
        return nameTransmitter;
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

    public AnnouncementType getType() {
        return type;
    }

    public void setType(AnnouncementType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(
                "Announcement[id=%d, idTransmitter='%d', nameTransmitter='%s', startPoint='%s', endPoint='%s', startDate='%s', endDate='%s']",
                this.id, this.idTransmitter, this.nameTransmitter, this.startPoint, this.endPoint, this.startDate, this.endDate);
    }


}
