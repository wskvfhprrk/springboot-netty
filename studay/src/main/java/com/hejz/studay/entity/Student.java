package com.hejz.studay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "tb_student")
@Data
//@Table(
//        name = "tb_student",
//        uniqueConstraints = {
//                @UniqueConstraint(name = "student_email_unique",columnNames = "email")
//        }
//)
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            columnDefinition="bigint COMMENT 'id'"
    )
    private Long id;
    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition="varchar(100) COMMENT '姓'"
    )
    private String firstName;
    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition="varchar(100) COMMENT '名'"
    )
    private String lastName;
    @Column(
            name = "email",
            nullable = false,
            columnDefinition="varchar(100) COMMENT 'email'"

    )
    private String email;
    @Column(
            name = "age",
            nullable = false,
            columnDefinition="int COMMENT '年龄'"
    )
    private Integer age;

    public Student(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Student() {

    }
}
