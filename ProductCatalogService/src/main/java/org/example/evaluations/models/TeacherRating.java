package org.example.evaluations.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TEACHER_RATING")
@IdClass(TeacherRatingId.class)
public class TeacherRating {

    @Id
    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;

    @Column(name = "RATING")
    private Integer rating;

    public TeacherRating() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
