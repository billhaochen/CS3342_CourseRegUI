package CourseRegisterUI.models;

public record SignedOut() implements Role {
    @Override
    public String idValue() {
        return "";
    }
    public String passwordValue() { return "" ; }

    @Override
    public String eidValue() {
        return "";
    }
}
