package university;

import university.model.Course;
import university.model.CourseProfessor;

import java.util.List;

public class Main{
    public static void main(String[] args){
        CourseProfessor professor = new CourseProfessor("Dr. Smith", "Computer Science");
        Course course = new Course.Builder("CS-301","Software Design Patterns",5,professor).asAdvanced().requireLab().addPrerequisite("CS-101 OOP").build();
        System.out.println(course);
    }
}