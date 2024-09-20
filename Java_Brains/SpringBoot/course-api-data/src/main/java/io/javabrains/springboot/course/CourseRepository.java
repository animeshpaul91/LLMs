package io.javabrains.springboot.course;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, String> { // Spring Data JPA will provide an implementation
    List<Course> findByTopicId(String topicId);
}
