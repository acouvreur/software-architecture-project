package fr.unice.polytech.al.model;

import fr.unice.polytech.al.State;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;

@Entity
public class Announcement {

        @Id
        @Column(unique = true, nullable = false)
        private Long idGoodAnnouncement;

        //dictionary in which the key correspend to idDriverAnnouncement and the value to his status
        @Column(unique = false)
        private Map<Long, State> driverAnnouncementStatus;

        public Announcement () {}

        public Long getIdGoodAnnouncement() {
            return idGoodAnnouncement;
        }

        public void setIdGoodAnnouncement(Long idTracking) {
            this.idGoodAnnouncement = idTracking;
        }

        public Map<Long, State> getDriversWithStatus() {
            return driverAnnouncementStatus;
        }

        public boolean ifExistsDriverAnnouncementStatus(Long idDriverAnnouncement) {
            try {
                driverAnnouncementStatus.get(idDriverAnnouncement);
                return true;
            } catch (Exception e) { }
            return false;
        }

        public void setDriversWithStatus(Map<Long, State> driverAnnouncementStatus) {
            this.driverAnnouncementStatus = driverAnnouncementStatus;
        }

        public void setStatusDriver(Long idDriverAnnouncement, State state) {
            driverAnnouncementStatus.put(idDriverAnnouncement, state);
        }


}
