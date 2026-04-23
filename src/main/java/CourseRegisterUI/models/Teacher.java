package CourseRegisterUI.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("teacher")
public record Teacher(
        // TODO implement later
        String name
) implements Role {

     // So Jackson doesn't write a new ID field in here
    public @JsonIgnore String idValue() {
        System.out.println("Change this later to actual id");
        return name;
    }

    public @JsonIgnore String passwordValue() {
        System.out.println("Change this later to actual password");
        return name;
    }

    public @JsonIgnore String eidValue() {
        System.out.println("Change this later to actual eid");
        return name;
    }
}
