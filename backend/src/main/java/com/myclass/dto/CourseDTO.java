package com.myclass.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.myclass.entities.Course;
import com.myclass.entities.Deliver;
import com.myclass.entities.Lesson;
import com.myclass.entities.User;

public class CourseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String description;
	private String imgUri;
	private Instant createdAt;
	private List<UserViewDTO> users = new ArrayList<>();
	private List<LessonDTO> lessons = new ArrayList<>();
	private List<DeliverDTO> deliveries = new ArrayList<>();
	
	public CourseDTO() {
	}

	public CourseDTO(Long id, String name, String description, String imgUri, Instant createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.imgUri = imgUri;
		this.createdAt = createdAt;
	}
	
	public CourseDTO(Course entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.imgUri = entity.getImgUri();
		this.createdAt = entity.getCreatedAt();
	}
	
	public CourseDTO(Course entity, Set<User> users, Set<Lesson> lessons, Set<Deliver> deliveries) {
		this(entity);
		users.forEach(user -> this.users.add(new UserViewDTO(user)));
		lessons.forEach(lesson -> this.lessons.add(new LessonDTO(lesson)));
		deliveries.forEach(deliver -> this.deliveries.add(new DeliverDTO(deliver)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUri() {
		return imgUri;
	}

	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}