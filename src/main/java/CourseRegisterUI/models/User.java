package CourseRegisterUI.models;

/**
 *  represents the generic User class that defines all possible users for this application
 * @param id : the string id of a user
 * @param name : the name of a user
 * @param role : whether the user is a Student or Teacher
 */
public record User(
        String id,
        String name,
        Role role
) {
    public String getID() {
        return role.idValue();
    }

    public String getPassword() {
        return role.passwordValue();
    }
}
