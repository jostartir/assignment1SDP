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

    // Приватный конструктор, который принимает сам Builder
    private Course(Builder builder) {
        this.courseCode = builder.courseCode;
        this.courseName = builder.courseName;
        this.credits = builder.credits;
        this.professor = builder.professor;
        this.maxStudents = builder.maxStudents;
        this.isAdvanced = builder.isAdvanced;
        this.isOnline = builder.isOnline;
        this.requiresLab = builder.requiresLab;
        this.gradingPolicy = builder.gradingPolicy;
        this.prerequisites = new ArrayList<>(builder.prerequisites);
    }

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


    public static class Builder{
        private final String courseCode;
        private final String courseName;
        private final int credits;
        private CourseProfessor professor;

        private int maxStudents = 30;
        private boolean isAdvanced = false;
        private boolean isOnline = false;
        private boolean requiresLab = false;
        private String gradingPolicy = "Standart 100-point scale";
        private List<String> prerequisites = new ArrayList<>();

        public Builder(String courseCode, String courseName, int credits, CourseProfessor professor){
            this.courseCode = courseCode;
            this.courseName = courseName;
            this.credits = credits;
            this.professor = professor;
        }

        public Builder maxStudents(int maxStudents){
            this.maxStudents = maxStudents;
            return this;
        }

        public Builder asAdvanced(){
            this.isAdvanced = true;
            return this;
        }

        public Builder deliverOnline(){
            this.isOnline = true;
            return this;
        }
        public Builder requireLab(){
            this.requiresLab = true;
            return this;
        }

        public Builder gradingPolicy(String gradingPolicy){
            this.gradingPolicy = gradingPolicy;
            return this;
        }

        public Builder addPrerequisite(String prerequisite){
            if (prerequisite != null && !prerequisite.isBlank() ){
                this.prerequisites.add(prerequisite);
            }
            return this;
        }

        private void validate(){
            if (courseCode == null || courseCode.isBlank()){
                throw new IllegalStateException("Course code can not be empty");
            }
            if (credits <= 0){
                throw new IllegalStateException("Credits must have greater than 0");
            }
            if (professor == null){
                throw new IllegalStateException("Course must have an assigned professor");
            }
            if (isAdvanced){
                if (credits < 5){
                    throw new IllegalStateException("Advanced courses must carry at least 5 credits");
                }
                if (prerequisites.isEmpty()){
                    throw new IllegalStateException("Advanced courses must have at least one prerequisite");
                }
            }

            if (isOnline && requiresLab){
                throw new IllegalStateException("Online courses cannot require physical lab space");
            }
        }

        public Course build(){
            validate();
            return new Course(this);
        }
    }


}


