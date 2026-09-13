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
