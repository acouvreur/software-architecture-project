package fr.unice.polytech.al.model;

import org.json.JSONObject;
import java.util.Date;

public class Course {

    private Long id;
    private String idClient;
    private String idDriver;
    private String idAnnouncement;
    private String startPoint;
    private String endPoint;
    private Date startDate;
    private Date endDate;

    public Course(long id) {
        this.id = id;
    }

    public Course(String idClient, String idDriver, String idAnnouncement, String startPoint, String endPoint, Date startDate, Date endDate){
        this.idClient = idClient;
        this.idDriver = idDriver;
        this.idAnnouncement = idAnnouncement;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public JSONObject toJson() {
        return new JSONObject()
                .put("id", id);
    }
}