package fr.unice.polytech.al.repository;

import fr.unice.polytech.al.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingRepository extends JpaRepository<Announcement, Long>  {


}
