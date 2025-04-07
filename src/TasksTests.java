import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TasksTests {
    static University university;
    static Tasks tasks;

    @BeforeAll
    static void setup() {
        university = new University();
        tasks = new Tasks(university);
    }

    @Test
    void task1() {
        var students = tasks.task1();
        if (students == null) {
            // The user has not attempted the problem, skip it
            assumeTrue(false);
            return;
        }

        var solution = new HashSet<>(university.getStudents());
        assertEquals(solution.size(), students.size());
        solution.forEach(s -> assertTrue(students.contains(s)));
    }

    @Test
    void task2() {
        var students = tasks.task2();
        if (students == null) {
            // The user has not attempted the problem, skip it
            assumeTrue(false);
            return;
        }

        var studentSet = new HashSet<>(students);
        var solution = university.getStudents().stream().filter(s -> s.getGpa() >= 3.5f).toList();
        assertEquals(solution.size(), studentSet.size());
        solution.forEach(s -> assumeTrue(studentSet.contains(s)));
    }


    @Test
    void task3() {
        var student = tasks.task3();
        if (student == null) {
            // The user has not attempted the problem, skip it
            assumeTrue(false);
            return;
        }

        var solution = university.getStudents().stream().min(Comparator.comparingDouble(Student::getGpa)).get();
        if (student != solution) {
            // There could be two students with the exact same gpa
            assertTrue(student.getGpa() <= solution.getGpa());
        }
    }

    @Test
    void task4() {
        var courses = tasks.task4();
        if (courses == null) {
            // The user has not attempted the problem, skip it
            assumeTrue(false);
            return;
        }

        var coursesSet = new HashSet<Course>(courses);
        var solution = university.getCourses().stream().filter(c ->
                c.getStudents().stream().filter(s ->
                                s.getGrade() == Grade.Masters || s.getGrade() == Grade.Phd)
                        .count() >= 3).toList();
        assertEquals(solution.size(), courses.size());
        solution.forEach(s -> assertTrue(coursesSet.contains(s)));
    }

    @Test
    void task5() {
        var count = tasks.task5();
        if (count == null) {
            // The user has not attempted the problem, skip it
            assumeTrue(false);
            return;
        }

        // There are many different ways you can solve this one
        var solution = university.getCourses().stream().mapToInt(c ->
                c.getStudents().stream().mapToInt(s ->
                        // Count all friends each student has in this class and divide it by 2
                        (int) c.getStudents().stream().filter(f -> (s.getFriends().containsKey(f)))
                                .count() / 2).sum()).sum();
        assertEquals(solution, count);
    }
}
