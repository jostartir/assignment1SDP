package university.director;

import university.model.Course;
import university.model.CourseProfessor;

public class CourseDirector {
    public Course constructSafeCourse(String code, String name, CourseProfessor professor ){
        return new Course.Builder(code,name,3,professor).maxStudents(30).gradingPolicy("Standart 100-point scale").build();
    }

    public Course constructAdvancedCourse(String code, String name, CourseProfessor professor, String prerequisite){
        return new Course.Builder(code,name,5,professor).asAdvanced().requireLab().addPrerequisite(prerequisite).maxStudents(20).build();
    }

    public Course constructOnlineCourse(String code, String name, CourseProfessor professor){
        return new Course.Builder(code,name,3,professor).deliverOnline().maxStudents(100).build();
    }

}
