package com.example.university.model;

public class Lesson {
    private Long id;
    private Long courseId;
    private String topic;
    private Integer duration;

    public Lesson() {}

    public Lesson(Long id, Long courseId, String topic, Integer duration) {
        this.id = id; this.courseId = courseId; this.topic = topic; this.duration = duration;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
}
