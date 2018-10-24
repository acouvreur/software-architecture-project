package fr.unice.polytech.al.repository;

import java.util.List;

import fr.unice.polytech.al.model.Announcement;
import org.springframework.data.repository.CrudRepository;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {

    List<Announcement> findByStartPoint(String startPoint);
}