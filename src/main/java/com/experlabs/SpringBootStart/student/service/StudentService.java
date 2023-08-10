package com.experlabs.SpringBootStart.student.service;

import com.experlabs.SpringBootStart.student.models.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {
    List<Student> getStudents(Map<String, String> queryParams);

    Student getStudentByID(Long id);

    Student saveStudent(Student student);

    void deleteStudentByID(Long id);

    Student updateStudentByID(Long id, String name, String email);
}
