package com.hejz.studay.repository;

import com.hejz.studay.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<Student,Long>, JpaSpecificationExecutor<Student> {
}
