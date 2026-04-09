package CourseRegisterUI.models;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("teacher")
public record Teacher(
        // TODO implement later
        String name
) implements Role {
    public String getId() {
        System.out.println("Change this later to actual id");
        return name;
    }

    public String getName() {
        return name;
    }
}
