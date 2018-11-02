package fr.unice.polytech.al.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    @Column(nullable = false)
    private Long idClient;

    @Column(nullable = false)
    private Long idDriver;

    /**
     * Référence à une annonce ; Pas besoin d'autre chose ici.
     */

    @Column(nullable = false)
    private Long idAnnouncement;

    /**
     * Référence vers la prochaine course (Liste chainée)
     */

    @Column()
    private Long idNextCourse;

    /**
     * On définit le point de départ, d'arrivé, etc... car une course
     * peut être juste un étape dans une announcement ; Une announcement peut
     * matcher avec un ensemble de course.
     */

    @Column(nullable = false)
    private String startPoint;

    @Column(nullable = false)
    private String endPoint;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    public Course() {}

    public Course(Long idClient, Long idDriver, Long idAnnouncement, Long idNextCourse, String startPoint, String endPoint, Date startDate, Date endDate){
        this.idClient = idClient;
        this.idDriver = idDriver;
        this.idAnnouncement = idAnnouncement;
        this.idNextCourse = idNextCourse;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        this.startDate = dateFormat.format(startDate);
        this.endDate = dateFormat.format(endDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(Long idDriver) {
        this.idDriver = idDriver;
    }

    public Long getIdAnnouncement() {
        return idAnnouncement;
    }

    public void setIdAnnouncement(Long idAnnouncement) {
        this.idAnnouncement = idAnnouncement;
    }

    public Long getIdNextCourse() {
        return idNextCourse;
    }

    public void setIdNextCourse(Long idNextCourse) {
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

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return String.format(
                "Course[id=%d, idClient='%s', idDriver='%s', idAnnouncement='%s', startPoint='%s', endPoint='%s', startDate='%s', endDate='%s']",
                this.id, this.idClient, this.idDriver, this.idAnnouncement, this.startPoint, this.endPoint, dateFormat.format(this.startDate), dateFormat.format(this.endDate));
    }

}
