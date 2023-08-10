package com.experlabs.SpringBootStart.student.controller;

import com.experlabs.SpringBootStart.student.models.Student;
import com.experlabs.SpringBootStart.student.service.StudentService;
import core.models.ResponseBody;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(@RequestParam Map<String, String> queryParams) {
        return studentService.getStudents(queryParams);
    }

    @GetMapping(path = "{studentId}")
    public ResponseEntity<Object> getStudent(@PathVariable("studentId") Long id) {
        try {
            Student student = studentService.getStudentByID(id);
            return ResponseEntity.ok(student);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping
    public Student saveStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<Object> deleteStudent(@PathVariable("studentId") Long id) {
        try {
            studentService.deleteStudentByID(id);
            return ResponseEntity.ok(new ResponseBody(HttpStatus.OK.value(), String.format("Student id:%d deleted successfully!", id)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PutMapping(path = "{studentId}")
    public ResponseEntity<Object> deleteStudent(@PathVariable("studentId") Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String email) {
        try {
            Student student = studentService.updateStudentByID(id, name, email);
            return ResponseEntity.ok(student);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
