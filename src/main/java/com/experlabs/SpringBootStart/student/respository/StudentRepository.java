package com.experlabs.SpringBootStart.student.respository;

import com.experlabs.SpringBootStart.student.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select student from Student student where student.id=?1")
    Optional<Student> findStudentByID(Long id);

    @Query("select student from Student student where student.email=?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT student FROM Student student WHERE LOWER(student.name) LIKE LOWER(concat('%', ?1, '%'))")
    List<Student> findStudentByNameFuzzySearch(String name);
}
