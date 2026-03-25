package CourseRegisterUI.models;

public sealed interface Role permits Student, Teacher {}
// This is the Java equivalent of a TypeScript Student | Teacher type
