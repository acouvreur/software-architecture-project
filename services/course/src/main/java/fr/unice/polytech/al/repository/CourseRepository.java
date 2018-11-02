package fr.unice.polytech.al.repository;

import fr.unice.polytech.al.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<List<Course>> findByIdAnnouncement(Long idAnnouncement);
}
