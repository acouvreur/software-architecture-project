package fr.unice.polytech.al.model;

import org.json.JSONObject;
import java.util.Date;

public class Announcement {

    private Long id;
    private Transmitter transmitter;
    private String startPoint;
    private String endPoint;
    private Date startDate;
    private Date endDate;
    private AnnouncementType type;

    public Announcement(long id) {
        this.id = id;
    }

    public Announcement(Transmitter t, String startPoint, String endPoint, Date startDate, Date endDate, AnnouncementType type){
        this.transmitter = t;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public JSONObject toJson() {
        return new JSONObject()
                .put("id", id);
    }
}