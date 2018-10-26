package fr.unice.polytech.al.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Les id client et driver serviront à avoir plus de détail en appelant le service
     * accounting comme le numéro de téléphone...
     */

    @Column(name = "id_client", nullable = false)
    private String idClient;

    @Column(name = "id_driver", nullable = false)
    private String idDriver;

    /**
     * Référence à une annonce ; Pas besoin d'autre chose ici.
     */

    @Column(name = "id_announcement", nullable = false)
    private String idAnnouncement;

    /**
     * Référence vers la prochaine course (Liste chainée)
     */

    @Column(name = "id_next_course")
    private String idNextCourse;

    /**
     * On définit le point de départ, d'arrivé, etc... car une course
     * peut être juste un étape dans une announcement ; Une announcement peut
     * matcher avec un ensemble de course.
     */

    @Column(name = "course_start_point", nullable = false)
    private String startPoint;

    @Column(name = "course_end_point", nullable = false)
    private String endPoint;

    @Column(name = "course_start_date", nullable = false)
    private Date startDate;

    @Column(name = "course_end_date", nullable = false)
    private Date endDate;

    public Course() {}

    public Course(String idClient, String idDriver, String idAnnouncement, String idNextCourse, String startPoint, String endPoint, Date startDate, Date endDate){
        this.idClient = idClient;
        this.idDriver = idDriver;
        this.idAnnouncement = idAnnouncement;
        this.idNextCourse = idNextCourse;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }

    public String getIdAnnouncement() {
        return idAnnouncement;
    }

    public void setIdAnnouncement(String idAnnouncement) {
        this.idAnnouncement = idAnnouncement;
    }

    public String getIdNextCourse() {
        return idNextCourse;
    }

    public void setIdNextCourse(String idNextCourse) {
        this.idNextCourse = idNextCourse;
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

    @Override
    public String toString() {
        return String.format(
                "Course[id=%d, idClient='%s', idDriver='%s', idAnnouncement='%s', startPoint='%s', endPoint='%s', startDate='%s', endDate='%s']",
                this.id, this.idClient, this.idDriver, this.idAnnouncement, this.startPoint, this.endPoint, this.startDate.toString(), this.endDate.toString());
    }

}
