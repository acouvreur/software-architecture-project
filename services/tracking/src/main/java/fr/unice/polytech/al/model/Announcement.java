package fr.unice.polytech.al.model;

import fr.unice.polytech.al.State;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
public class Announcement {

        @Id
        @Column(unique = true, nullable = false)
        private Long idGoodAnnouncement;

        //dictionary in which the key correspend to idDriverAnnouncement and the value to his status
    /*@MapKey(name = "locale")
    private Map<Long, State> driverAnnouncementStatus;*/

    @Column
    private Long idDriverAnnouncement;

    @Column
    //private State state;
    private String state;


    public Announcement() {}

    public Announcement(Long idGoodAnnouncement, Long idDriverAnnouncement, String state) {
        this.idGoodAnnouncement = idGoodAnnouncement;
        this.idDriverAnnouncement = idDriverAnnouncement;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
