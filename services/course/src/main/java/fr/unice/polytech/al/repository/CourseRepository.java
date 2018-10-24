package fr.unice.polytech.al.repository;

import fr.unice.polytech.al.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByIdAnnouncement(String idAnnouncement);
}
