package io.javabrains.springboot.topic;

import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, String> { // Spring Data JPA will provide an implementation

}
