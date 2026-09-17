package university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import university.model.Course;
import university.model.CourseProfessor;

import static org.junit.jupiter.api.Assertions.*;

public class CourseBuilderTest {
    private CourseProfessor defaultProfessor;

    @BeforeEach
    void setUp(){
        defaultProfessor = new CourseProfessor("Dr.Sanchez","PhD");

    }

    // 1

    @Test
    @DisplayName("1. valid: should construct a course with def par")
    void testValidBasicConstruction(){
        Course course = new Course.Builder("CS101","Intro to CS", 3, defaultProfessor).build();
        assertNotNull(course);
        assertEquals("CS101",course.getCourseCode());
        assertEquals("Intro to CS", course.getCourseName());
        assertEquals(3,course.getCredits());
        assertEquals(30,course.getMaxStudents());
        assertFalse(course.isAdvanced());
        assertFalse(course.isOnline());
    }

    // 2

    @Test
    @DisplayName("2. valid: should construct a adv course with lab and prereq")
    void testValidAdvancedCourseConstruction(){
        Course course = new Course.Builder("CS201", "Software Design Patterns",5,defaultProfessor ).asAdvanced().requireLab().addPrerequisite("CS101 OOP").build();

        assertTrue(course.isAdvanced());
        assertTrue(course.isRequiresLab());
        assertEquals(1,course.getPrerequisites().size());
        assertEquals("CS101 OOP", course.getPrerequisites().get(0));


    }


    // 3

    @Test
    @DisplayName("3. valid: should construct online course without lab")
    void testValidOnlineCourseConstruction(){
        Course course = new Course.Builder("CS067", "Web Technologies", 3,defaultProfessor).deliverOnline().maxStudents(100).build();

        assertTrue(course.isOnline());
        assertFalse(course.isRequiresLab());
        assertEquals(100, course.getMaxStudents());


    }

    // 4

    @Test
    @DisplayName("4. invalid: should throw exception when course code is blank")
    void testInvalidBlankCourseCode(){
        Course.Builder builder = new Course.Builder(" ", "Database Systems", 3,defaultProfessor);

        IllegalStateException exception = assertThrows(IllegalStateException.class, builder::build);
        assertEquals("Course code can not be empty", exception.getMessage());
    }

    // 5

    @Test
    @DisplayName("5. invalid: should throw exceptions when advanced course has fewer than 5 credits")
    void testInvalidAdvancedCourseCredits(){
        Course.Builder builder = new Course.Builder("CS402", "Algorithm and Design System", 4,defaultProfessor).asAdvanced().addPrerequisite("CS101");

        IllegalStateException exception = assertThrows(IllegalStateException.class, builder::build);
        assertEquals("Advanced courses must carry at least 5 credits", exception.getMessage());

    }

    // 6

    @Test
    @DisplayName("6. invalid: should throw exception when advanced course has no prereq")
    void testAdvancedCourseWithoutPrerequisites(){
        Course.Builder builder = new Course.Builder("CS403","Algorithm and Design system", 5,defaultProfessor).asAdvanced();

        IllegalStateException exception = assertThrows(IllegalStateException.class, builder::build);
        assertEquals("Advanced courses must have at least one prerequisite", exception.getMessage());

    }

    // 7

    @Test
    @DisplayName("7. boundary: should throw exception when credits are zero or less than 0")
    void testBoundaryZeroOrNegativeCredits(){
        Course.Builder builderZero = new Course.Builder("CS042","Welcome to Python",0,defaultProfessor);
        Course.Builder builderNegative = new Course.Builder("CS042","Welcome to C++",-2,defaultProfessor);

        assertThrows(IllegalStateException.class, builderZero::build);
        assertThrows(IllegalStateException.class, builderNegative::build);
    }

    // 8

    @Test
    @DisplayName("8. boundary: Advanced course with exactly 5 credits should pass vaildation")
    void testBoundaryMinimumCreditsForAdvanced(){
        Course course = new Course.Builder("CS052","Operating System",5,defaultProfessor).asAdvanced().addPrerequisite("CS101").build();

        assertEquals(5,course.getCredits());
        assertTrue(course.isAdvanced());
    }

    // 9

    @Test
    @DisplayName("9. Constraint: Online courses can not require physical lab")
    void testConstraintOnlineCourseWithLab(){
        Course.Builder builder = new Course.Builder("CS067", "DAA", 3,defaultProfessor).deliverOnline().requireLab();

        IllegalStateException exception = assertThrows(IllegalStateException.class, builder::build);
        assertEquals("Online courses cannot require physical lab space", exception.getMessage());

    }


    // 10

    @Test
    @DisplayName("10. independence: modyfiung or reusing builder after build() does not change previously built product")
    void testBuilderReuseIndependence(){
        Course.Builder builder = new Course.Builder("CS102","OOP",3,defaultProfessor).addPrerequisite("CS101");

        Course firstCourse = builder.build();

        builder.addPrerequisite("OS100").maxStudents(50);

        Course secondCourse = builder.build();

        assertEquals(1,firstCourse.getPrerequisites().size());
        assertEquals("CS101",firstCourse.getPrerequisites().get(0));
        assertEquals(30,firstCourse.getMaxStudents());

        assertEquals(2,secondCourse.getPrerequisites().size());
        assertEquals(50,secondCourse.getMaxStudents());

    }



}

