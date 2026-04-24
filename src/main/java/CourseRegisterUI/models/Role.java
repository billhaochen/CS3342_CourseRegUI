package CourseRegisterUI.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Student.class, name = "student"),
        @JsonSubTypes.Type(value = Admin.class, name = "admin")
})
public sealed interface Role permits Student, Admin, SignedOut {
    public String idValue();
    public String passwordValue();
    public String eidValue();
}
// This is the Java equivalent of a TypeScript Student | Teacher type
