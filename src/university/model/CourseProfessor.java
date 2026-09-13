package university.model;

public class CourseProfessor {
    private final String fullName;
    private final String academicDegree;

    public CourseProfessor(String fullName, String academicDegree){
        if (fullName == null || fullName.isBlank()){
            throw new IllegalArgumentException("Professor's full name can not be empty.");
        }
        this.fullName = fullName;
        this.academicDegree = academicDegree;
    }

    public String getFullName(){
        return fullName;
    }

    public String getAcademicDegree(){
        return academicDegree;
    }

    @Override
    public String toString(){
        return academicDegree + " " + fullName;
    }


}
