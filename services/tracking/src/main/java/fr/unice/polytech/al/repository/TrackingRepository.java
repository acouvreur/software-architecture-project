package fr.unice.polytech.al.repository;

import fr.unice.polytech.al.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackingRepository extends JpaRepository<Announcement, Long>  {
   // Optional<List<Announcement>> findByIdGoodAnnouncement(Long idGoodAnnouncement);

}
