package com.myclass.services;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myclass.dto.DeliverDTO;
import com.myclass.dto.DeliverInsertDTO;
import com.myclass.dto.DeliverRevisionDTO;
import com.myclass.entities.Course;
import com.myclass.entities.Deliver;
import com.myclass.entities.Lesson;
import com.myclass.entities.User;
import com.myclass.entities.enums.DeliverStatus;
import com.myclass.repositories.CourseRepository;
import com.myclass.repositories.DeliverRepository;
import com.myclass.repositories.LessonRepository;
import com.myclass.repositories.UserRepository;


@Service
public class DeliverService {

	@Autowired
	private DeliverRepository repository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private LessonRepository lessonRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public List<DeliverDTO> getDeliveries() {
		authService.validateAdmin();
		List<Deliver> list = repository.findAll();
		return list.stream().map(x -> new DeliverDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<DeliverDTO> getDeliveriesByCourse(Long id) {
		Course course = courseRepository.getOne(id);
		Set<Deliver> list = course.getDeliveries();
		return list.stream().map(x -> new DeliverDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public DeliverDTO insert(DeliverInsertDTO dto) {
		Deliver entity = new Deliver();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new DeliverDTO(entity);
	}
	
	@Transactional
	public void saveRevision(Long id, DeliverRevisionDTO dto) {
		Deliver deliver = repository.getOne(id);
		deliver.setStatus(dto.getStatus());
		deliver.setFeedback(dto.getFeedback());
		repository.save(deliver);
	}
	
	private void copyDtoToEntity(DeliverInsertDTO dto, Deliver entity) {
		entity.setUri(dto.getUri());
		entity.setCreatedAt(Instant.now());
		entity.setStatus(DeliverStatus.PENDING);
		entity.setFeedback("");
		Lesson task = lessonRepository.getOne(dto.getTask().getId());
		entity.setTask(task);
		User user = userRepository.getOne(dto.getUser().getId());
		entity.setUser(user);
		Course course = courseRepository.getOne(dto.getCourse().getId());
		entity.setCourse(course);
	}

	
}
