package CourseRegisterUI.models;

public record SignedOut() implements Role {
    @Override
    public String idValue() {
        return "";
    }
}
