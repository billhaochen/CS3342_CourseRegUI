package CourseRegisterUI.util;

import CourseRegisterUI.models.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static CourseRegisterUI.util.UserService.*;
import static CourseRegisterUI.util.UserService.getAdminByID;

public class AuthenticationService {
    public static boolean validateStudentCredentials(Root root, String student_name, String student_id, String password) {
        boolean valid = false;
        if (Objects.equals(student_name, "") || Objects.equals(student_id, "")) {
            return valid;
        }
        Optional<User> name_lookup = getStudentByName(root, student_name);
        Optional<User> id_lookup = getStudentByID(root, student_id);
        return name_lookup.isPresent()
                && id_lookup.isPresent()
                && name_lookup.get().role() instanceof Student
                && id_lookup.get().role() instanceof Student
                && name_lookup.get().getID().equals(student_id)
                && id_lookup.get().name().equals(student_name)
                && id_lookup.get().getPassword().equals(password)
                && name_lookup.get().getPassword().equals(password);
    }

    public static boolean validateAdminCredentials(Root root, String admin_name, String admin_id, String password) {
        boolean valid = false;
        if (Objects.equals(admin_name, "") || Objects.equals(admin_id, "")) {
            return valid;
        }
        Optional<User> name_lookup = getAdminByName(root, admin_name);
        Optional<User> id_lookup = getAdminByID(root, admin_id);
        return name_lookup.isPresent()
                && id_lookup.isPresent()
                && name_lookup.get().role() instanceof Admin
                && id_lookup.get().role() instanceof Admin
                && name_lookup.get().getID().equals(admin_id)
                && id_lookup.get().name().equals(admin_name)
                && id_lookup.get().getPassword().equals(password)
                && name_lookup.get().getPassword().equals(password);
    }
}
