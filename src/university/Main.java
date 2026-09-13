package university;

import university.model.Course;
import university.model.CourseProfessor;

import java.util.List;

public class Main{
    public static void main(String[] args){
        CourseProfessor professor = new CourseProfessor("Makpal Zhartybayeva", "PhD");
        Course InitialCourse = new Course("CS-301", "Software Design Patterns", 5,professor,30,true,true,true,"100-point scale", List.of("CS-101 OOP") );

        System.out.println("Part A initial course created: " + "\n" + InitialCourse);
    }
}