package fr.unice.polytech.al.repository;

import fr.unice.polytech.al.model.Announcement;
import org.springframework.data.repository.CrudRepository;


public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
}