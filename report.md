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

These validation rules make sure that the created `Course` object follows the requirements of the university course domain.
