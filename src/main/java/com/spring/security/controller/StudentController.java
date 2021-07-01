package com.spring.security.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.model.Student;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

	private static final List<Student> STUDENTS = Arrays.asList(
			new Student(1, "John Nash"),
			new Student(2, "Stephen Hawking"),
			new Student(3, "Marie Curie"),
			new Student(4, "Albert Einstein"),
			new Student(5, "Isaac Newton"),
			new Student(6, "Nikola Tesla"),
			new Student(7, "Pythagoras"),
			new Student(8, "Michael Faraday"));
	@GetMapping(path = "{studentId}")
	public Student getStudent(@PathVariable("studentId") Integer studentId)
	{
		return STUDENTS.stream()
				.filter(student -> studentId.equals(student.getStudentId()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Student "+ studentId + " does not exists"));
	}
}
