package university.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Course {
    // courseCode courseName credits professor
    // maxStudents isAdvanced isOnline requiresLab gradingPolicy prerequisites
    private final String courseCode;
    private final String courseName;
    private final int credits;
    private CourseProfessor professor;

    private final int maxStudents;
    private final boolean isAdvanced;
    private final boolean isOnline;
    private final boolean requiresLab;
    private final String gradingPolicy;
    private final List<String> prerequisites;

    public Course(String courseCode, String courseName, int credits, CourseProfessor professor, int maxStudents, boolean isAdvanced, boolean isOnline, boolean requiresLab,String gradingPolicy, List<String> prerequisites){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.professor = professor;
        this.maxStudents = maxStudents;
        this.isAdvanced = isAdvanced;
        this.isOnline = isOnline;
        this.requiresLab = requiresLab;
        this.gradingPolicy = gradingPolicy;
        this.prerequisites = prerequisites != null ? new ArrayList<>(prerequisites) : new ArrayList<>();
    }
    public String getCourseCode(){ return courseCode;}
    public String getCourseName(){ return courseName;}
    public int getCredits(){ return credits;}
    public CourseProfessor getProfessor(){ return professor;}
    public int getMaxStudents(){return maxStudents;}
    public boolean isAdvanced(){ return isAdvanced;}
    public boolean isOnline(){ return isOnline;}
    public boolean isRequiresLab(){return requiresLab;}
    public String getGradingPolicy(){return gradingPolicy;}
    public List<String> getPrerequisites(){return Collections.unmodifiableList(prerequisites);}

    @Override
    public String toString(){
        return "Course{" + "\n" + " code = " + courseCode + ", name = " + courseName + "\n" + " credits = " + credits + ", professor = " + professor + "\n" + " maximum students = " + maxStudents + ", is advanced? " + isAdvanced + "\n" + " is online? " + isOnline + ", is required lab? " + requiresLab + "\n" + " grading policy = " + gradingPolicy + "\n" + " prerequisites = " + prerequisites + " }";
    }

}
