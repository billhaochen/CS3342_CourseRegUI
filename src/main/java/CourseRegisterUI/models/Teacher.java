package CourseRegisterUI.models;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("teacher")
public record Teacher(
        // TODO implement later
        String name
) implements Role {
}
