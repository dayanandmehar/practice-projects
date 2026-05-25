package org.example.evaluations.models;

import java.io.Serializable;
import java.util.Objects;

public class TeacherRatingId implements Serializable {

    private Long student;
    private Long teacher;

    public TeacherRatingId() {
    }

    public TeacherRatingId(Long student, Long teacher) {
        this.student = student;
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TeacherRatingId that = (TeacherRatingId) o;
        return Objects.equals(student, that.student) && Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, teacher);
    }
}
