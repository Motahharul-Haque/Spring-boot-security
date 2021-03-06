package com.spring.security.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.model.Student;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
	
	private static final List<Student> STUDENTS = Arrays.asList(
			new Student(1, "John Nash"),
			new Student(2, "Stephen Hawking"),
			new Student(3, "Marie Curie"),
			new Student(4, "Albert Einstein"),
			new Student(5, "Isaac Newton"),
			new Student(6, "Nikola Tesla"),
			new Student(7, "Pythagoras"),
			new Student(8, "Michael Faraday"));
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
	public List<Student> getAllStudent()
	{
		return STUDENTS;
	}

	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public void registerNewStudent(@RequestBody Student student)
	{
		System.out.println(student);
	}
	
	@DeleteMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void deleteStudent(@PathVariable("studentId") Integer studentId)
	{
		System.out.println(studentId);
	}
	
	@PutMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student)
	{
		System.out.println(String.format("%s  %s", studentId, student));
	}
	
}
