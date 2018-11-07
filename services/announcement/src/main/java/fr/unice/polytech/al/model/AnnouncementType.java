package fr.unice.polytech.al.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public enum AnnouncementType implements Serializable{
    COURSE, GOOD;
}
