# Assignment 1 - Builder Pattern: Design Under Changing Requirements

### Alisher Kairov

## 1. Individual variant:
Domain:University course

Constraint: Advanced courses must have at least 5 credits and must have at least one prerequisite.

Preset:SAFE

## 2. Part A - Demonstrate the Design Problem
In the initial version, the Course object was created using a constructor with many parameters:

```
Course InitialCourse = new Course("CS-301", "Software Design Patterns", 5,professor,30,true,true,true,"100-point scale", List.of("CS-101 OOP") );
```
**Identified Problems:**

Confusing Parameters: Several parameters have the same type, such as boolean and int. Because of this, it is easy to accidentally put values in the wrong order. For example, isOnline and isAdvanced could be swapped without causing a compilation error.

Too Many Constructor Parameters: The constructor contains many optional values. This makes the code harder to read and can require using null or default values when some parameters are not needed.

Validation Problems: It is difficult to check all the course rules directly in a large constructor. For example, an advanced course must have at least 5 credits and at least one prerequisite. These conditions are easier to validate before creating the final object.

## 3. Part B - Refactor to Builder

The ``Course`` class was changed to use the Builder Design Pattern. This makes the process of creating a course easier to understand and keeps the construction logic separate from the main Course object.

**Implementation Details:**

**Product:** Course is an immutable class, so all its fields are final and cannot be changed after the object is created.

**Builder:** A static inner class Course.Builder is used to create Course objects. It provides a fluent API, so methods can be called one after another. For example, .asAdvanced(), .deliverOnline(), and .requireLab() make the code easier to read.

**Constructor:** The Course constructor is private and accepts a Builder. This means that a Course object can only be created through the Builder.

**Default Values:** Some optional fields have default values. For example, maxStudents is set to 30, and gradingPolicy uses "Standard 100-point scale" by default.

## 4. Part C - Validation Challenge

The validation is done inside the `Builder.validate()` method before the Course `object` is created. This helps prevent invalid course objects from being created.

#### **Single-Field Rules:**

**Course Code:** The course code cannot be null or empty.

**Credits:** The number of credits must be greater than 0.

**Professor:** A professor must be assigned to the course.

#### **Cross-Field Rules:**

**Advanced Course:** If a course is marked as advanced, it must have at least 5 credits and at least one prerequisite.

**Online Lab:** If a course is online, it cannot require a physical laboratory (requiresLab == false).

## 5. Part D - Preset Configurations

The `CourseDirector` class was created to make it easier to create courses with common configurations. It stores the main steps for creating different types of courses, so the same Builder code does not have to be repeated in different parts of the program.

#### **Implemented Presets:**
**1.SAFE Course (constructSafeCourse)**

**Purpose:** Used for a basic introductory course.

**Configuration:** The course has 3 credits, a default capacity of 30 students, and the standard grading policy.

**2.ADVANCED Course (constructAdvancedCourse)**

**Purpose:** Used for a more difficult and specialized course.

**Configuration:** The course has 5 credits, is marked as advanced, requires a physical lab, has a prerequisite, and has a maximum capacity of 20 students.

**3.ONLINE Course (constructOnlineCourse)**

**Purpose:** Used for courses that are taught online.

**Configuration:** The course has 3 credits, is delivered online, allows up to 100 students, and does not require a physical lab.

**Why Use CourseDirector?**

**DRY Principle:**
It helps avoid repeating the same Builder code when creating common course types.

**Separation of Concerns:**
The client does not need to know all the steps required to create a specific course. It can simply ask the CourseDirector to create one of the available presets.

## 6. Part E - Clean Code: Before  --> After

### Fragment 1: Avoiding Flag Arguments
**BEFORE**
```
public void setOnline(boolean isOnline) {
    this.isOnline = isOnline;
}
```
**AFTER**
```
public Builder deliverOnline(){
    this.isOnline = true;
    return this;
}
```

**1. What was wrong?**

The original code used boolean values like true and false in setter methods. For example, `setOnline(true)` does not clearly show what the method is supposed to do.

**2. Which Clean Code principle did you apply?**

Avoid Flag Arguments

**3. Why is the new implementation better?**
Replacing flag arguments with dedicated, intent-revealing methods makes the API self-explanatory, chainable, and clean at the call site.


### Fragment 2: Small Functions and Clear Error Handling

**BEFORE**

```
public Course build() {
    if (courseCode == null || courseCode.isBlank()) {
        throw new IllegalStateException("Course code can not be empty");
    }
    if (credits <= 0) {
        throw new IllegalStateException("Credits must have greater than 0");
    }
    // ...
    return new Course(this);
}
```

**AFTER**

```
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
```

**1. What was wrong?**

The build() method was doing too much. It was checking all the validation rules and creating the Course object at the same time.

**2. Which Clean Code principle did you apply?**

Small Functions and One Level of Abstraction per Function

**3. Why is the new implementation better?**

Now, build() has a simple purpose: it first calls validate() and then creates the Course. The detailed validation is handled separately inside the validate() method. This makes the code easier to understand and maintain.


### Fragment 3: Encapsulation and Defensive Copying
**BEFORE**

```
public Course(..., List<String> prerequisites) {
    // ...
    this.prerequisites = prerequisites;
}

public List<String> getPrerequisites() {
    return prerequisites;
}
```

**AFTER**

```
private Course(Builder builder) {
    // ...
    this.prerequisites = new ArrayList<>(builder.prerequisites);
}

public List<String> getPrerequisites() {
    return Collections.unmodifiableList(prerequisites);
}
```
**1. What was wrong?**

The original code directly used external lists. This meant that the list could be changed from outside after the Course was created. This could unexpectedly change the course data.

**2. Which Clean Code principle did you apply?**

Encapsulation and Avoiding Hidden Side Effects

**3. Why is the new implementation better?**

The new implementation creates a copy of the list using new ArrayList<>(...). The list is also made unmodifiable with Collections.unmodifiableList(...). This protects the course data and keeps the Course object immutable.

## 7. Part F - Design Decision

**Decision:** I decided to put the main validation logic in the Builder validate() method. It checks things like advanced courses having prerequisites and at least 5 credits, and online courses not requiring physical labs. The validation is done before the Course object is created.

**Alternative:** Another option was to put the validation in the Course constructor, use setter methods, or create a separate validator class.

**Reasoning:** I chose the Builder because it prevents invalid Course objects from being created. This follows the Fail-Fast principle. It also keeps the Course class simpler because the Builder handles the construction and validation rules.

## 8. Part G - UML Diagram

![umldiagrammm.png](umldiagrammm.png)

| Builder Role | Your Class | Responsibility |
| ------- | ------- | ------- |
| Product | university.model.Course | Represents the course object that is being created.It stores the final course information and provides getters to access the data. |
| Builder | university.model.Course.Builder | Builds the Course object step by step, sets default values, and checks that the course data is valid using the validate() method. |
| Client | university.Main | Starts the course creation process by using CourseDirector or Course.Builder to create Course objects. |
| Director | university.director.CourseDirector | Contains predefined methods for creating different types of courses using Course.Builder, such as safe, advanced, and online courses. |


## 9. Part H - Automated Testing

The implementation was tested with 10 tests. The tests check the main construction cases, boundary values, validation rules, and object immutability:

1.`testValidBasicConstruction`: Checks that a basic course is created correctly with the default values.

2.`testValidAdvancedCourseConstruction`: Checks that an advanced course can be created with lab requirements and prerequisite courses.

3.`testValidOnlineCourseConstruction`: Checks that an online course can be created with a custom number of students.

4.`testInvalidBlankCourseCode`: Checks that an IllegalStateException is thrown when the course code is empty or null.

5.`testInvalidAdvancedCourseCredits`: Checks that an exception is thrown when an advanced course has less than 5 credits.

6.`testAdvancedCourseWithoutPrerequisites`: Checks that an advanced course has at least one prerequisite.

7.`testBoundaryZeroOrNegativeCredits`: Checks that 0 or less than 0 credits are not allowed.

8.`testBoundaryMinimumCreditsForAdvanced`: Checks that an advanced course with exactly 5 credits passes validation successfully.

9.`testConstraintOnlineCourseWithLab`: Checks that online courses cannot require a physical lab.

10.`testBuilderReuseIndependence`:Checks that the builder can be reused and that changing it after build() does not affect already created Course objects.




