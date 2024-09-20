package io.javabrains.springbootstarter.topic;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service // marks this class a Spring Service
public class TopicService {

    private final List<Topic> topics;

    public TopicService() {
        this.topics = new ArrayList<>(Arrays.asList(
                new Topic("Spring", "Spring Framework", "Spring Framework Description"),
                new Topic("Java", "Core Java", "Core Java Description"),
                new Topic("Javascript", "Javascript", "Javascript Description")
        ));
    }

    public List<Topic> getAllTopics() {
        return topics;
    }

    public Topic getTopic(String id) {
        return topics.stream()
                .filter(topic -> topic.getId().equals(id))
                .findFirst()
                .get();
    }

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public void updateTopic(String id, Topic newTopic) {
        for (int i = 0; i < topics.size(); i++) {
            Topic existingTopic = topics.get(i);
            if (existingTopic.getId().equals(id)) {
                topics.set(i, newTopic);
                return;
            }
        }
    }

    public void deleteTopic(String id) {
        topics.removeIf(topic -> topic.getId().equals(id));
    }
}
