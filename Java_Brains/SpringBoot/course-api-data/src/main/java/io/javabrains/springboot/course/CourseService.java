package io.javabrains.springboot.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // marks this class a Spring Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(String id) {
        return courseRepository.findByTopicId(id);
    }

    public Course getCourse(String id) {
        Optional<Course> topicOpt = courseRepository.findById(id);
        return topicOpt.orElse(null);
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void updateCourse(Course course) {
        courseRepository.save(course); // insert or update because it knows Primary Key
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }
}
