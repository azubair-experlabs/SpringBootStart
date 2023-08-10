package com.experlabs.SpringBootStart.student.service;

import com.experlabs.SpringBootStart.student.respository.StudentRepository;
import com.experlabs.SpringBootStart.student.models.Student;
import com.experlabs.SpringBootStart.core.utils.HelperMethods;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    public List<Student> getStudents(Map<String, String> queryParams) {
        String name = queryParams.get("name");
        if (name != null && !name.isEmpty())
            return studentRepo.findStudentByNameFuzzySearch(name);
        return studentRepo.findAll();
    }

    public Student getStudentByID(Long id) {
        Optional<Student> response = studentRepo.findStudentByID(id);
        return response.orElseThrow(() -> new EntityNotFoundException(String.format("student not found with id %d", id)));
    }

    public Student saveStudent(Student student) {
        boolean isTaken = studentRepo.findStudentByEmail(student.getEmail()).isPresent();
        if (isTaken)
            throw new IllegalStateException("email already taken!");
        else
            return studentRepo.save(student);
    }

    public void deleteStudentByID(Long id) {
        Optional<Student> response = studentRepo.findStudentByID(id);
        if (response.isPresent()) {
            studentRepo.delete(response.get());
        }
        else
            throw new EntityNotFoundException(String.format("Student with id %d does not exists",id));
    }

    @Override
    @Transactional
    public Student updateStudentByID(Long id, String name, String email) {
        Student student = studentRepo.findStudentByID(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("student not found with id %d", id)));

        if (name != null && !name.isBlank() && !name.equals(student.getName()))
            student.setName(name);
        if (email != null && !email.isBlank() && !email.equals(student.getName()) && HelperMethods.isValidEmail(email)) {
            boolean isAlreadyTaken = studentRepo.findStudentByEmail(email).isPresent();
            if (isAlreadyTaken)
                throw new IllegalStateException("email already taken!");
            else
                student.setEmail(email);
        }
        return student;
    }
}
