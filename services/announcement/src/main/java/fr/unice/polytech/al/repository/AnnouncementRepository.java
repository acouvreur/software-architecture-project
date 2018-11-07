package fr.unice.polytech.al.repository;

import fr.unice.polytech.al.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}