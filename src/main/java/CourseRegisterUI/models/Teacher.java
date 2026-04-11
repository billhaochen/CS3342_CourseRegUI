package CourseRegisterUI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("teacher")
public record Teacher(
        // TODO implement later
        String name
) implements Role {

    @JsonIgnore // So Jackson doesn't write a new ID field in here
    public String idValue() {
        System.out.println("Change this later to actual id");
        return name;
    }
}
