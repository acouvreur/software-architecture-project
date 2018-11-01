package fr.unice.polytech.al.repository;

import fr.unice.polytech.al.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByIdCourse(Long idCourse);
    Optional<Course> findByIdAnnouncement(String idAnnouncement);
    //Optional<Course> getNextCourse(Long idNextCourse);
}
