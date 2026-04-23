package CourseRegisterUI;

import CourseRegisterUI.models.*;
import CourseRegisterUI.util.CourseService;
import CourseRegisterUI.util.JSONDeserializer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.File;
import java.util.*;

public class AppContext {
    private Root courseUserRepository;

    private boolean isAdmin;
    private final ObjectProperty<User> currentUser = new SimpleObjectProperty<>(null);
    private final ObservableList<CourseRow> courseRows = FXCollections.observableArrayList();
    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final ObservableList<Course> master_list_courses = FXCollections.observableArrayList();

    private final ObservableList<Course> selectedCourses = FXCollections.observableArrayList();
    private final ObservableList<CourseRow> selectedCourseRows = FXCollections.observableArrayList();
    private final FilteredList<CourseRow> filteredCourseRows;
    private RootUserType rootUserType;

    public AppContext() {
        this.courseUserRepository = new Root(new ArrayList<>(), new ArrayList<>());
        this.currentUser.set(new User("", "Sign In", new SignedOut()));
        this.filteredCourseRows = new FilteredList<>(selectedCourseRows, null);
        this.rootUserType = RootUserType.SIGNED_OUT;
    }

    public void loadInitialData() {
        String latest_sample_file = "src/main/resources/json/master_export_2026-04-14_12-53-17-378.json";
        File latest_created_master = JSONDeserializer.findLatestMasterExportFile();
        this.courseUserRepository = JSONDeserializer.JSONToRoot(latest_sample_file);
        if (latest_created_master != null) {
            if (!latest_created_master.getPath().isBlank()) {
                this.courseUserRepository = JSONDeserializer.JSONToRoot(latest_created_master.getPath());
            }
        }


        courseRows.setAll(CourseService.loadCourseRowsFromRoot(this.courseUserRepository));
        selectedCourses.setAll(CourseService.loadCoursesForStudent(getCurrentUser()));
        selectedCourseRows.setAll(CourseService.loadCourseRowsForStudent(getCurrentUser()));
        users.addAll(courseUserRepository.users());
        master_list_courses.addAll(courseUserRepository.courses());
    }

    public ObservableList<CourseRow> getCourseRows() {
        return courseRows;
    }

    public Root getCourseRepository() {
        return courseUserRepository;
    }

    public void setCurrentUser(String full_name, String id) {
        User new_user = this.courseUserRepository.users().stream().filter(
                user -> user.id().equals(id) && user.name().equals(full_name)
        ).findFirst().orElse(null);

        if (new_user != null) {
            this.currentUser.set(new_user);
            reloadData();
        } else {
            System.out.println("There was an issue with finding the credentials in the list of Users ");
        }
    }

    private void reloadData() {
        selectedCourses.setAll(CourseService.loadCoursesForStudent(getCurrentUser()));
        selectedCourseRows.setAll(CourseService.loadCourseRowsForStudent(getCurrentUser()));
    }

    public User getCurrentUser() {
        return currentUser.get();
    }

    public ObjectProperty<User> currentUserProperty() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser.set(currentUser);
    }

    public void registerCourses() {
        User user = currentUser.get();

        if (user.role() instanceof Student student) {
            List<Course> updatedCourses = new ArrayList<>(student.enrolled_courses());
            updatedCourses.addAll(selectedCourses);

            Student updatedStudent = new Student(
                    student.name(),
                    student.surname(),
                    student.first_name(),
                    student.student_id(),
                    student.student_eid(),
                    student.password(),
                    student.college(),
                    student.degree(),
                    student.status(),
                    student.program(),
                    student.location(),
                    student.start_date(),
                    student.end_date(),
                    updatedCourses,
                    student.completed_courses(),
                    student.major(),
                    student.waitlisted_courses()
            );

            User updatedUser = new User(
                    user.id(),
                    user.name(),
                    updatedStudent
            );

            users.remove(user);
            users.add(updatedUser);

            currentUser.set(updatedUser);
        }
    }

    public boolean addUserToWaitlist(Course course) {
        List<Course> matchingCourse = this.courseUserRepository.courses().stream().filter(course1 -> course1.equals(course)).toList();
        if (matchingCourse.size() == 1) {
            Course course1 = matchingCourse.getFirst();
            List<String> updatedWaitlist = course1.waitlist();
            updatedWaitlist.add(getCurrentUser().id());

            Course updatedCourse = new Course(
                    course1.academic_unit(),
                    course1.subject(),
                    course1.course_code(),
                    course1.title(),
                    course1.college(),
                    course1.crn(),
                    course1.section(),
                    course1.credit(),
                    course1.campus(),
                    course1.web_enabled(),
                    course1.level(),
                    course1.availability() - 1,
                    course1.cap(),
                    course1.waitlist_available(),
                    course1.start_date(),
                    course1.end_date(),
                    course1.start_time(),
                    course1.end_time(),
                    course1.day(),
                    course1.building(),
                    course1.room(),
                    course1.instructor(),
                    course1.medium(),
                    course1.meeting_time(),
                    course1.prerequisites(),
                    updatedWaitlist
            );
            HashMap<String, List<User>> changedUsers = removeCourseFromUsers(this.users, course1);

            Student student = (Student) getCurrentUser().role();
            student.waitlisted_courses().add(course);
            setCurrentUser(new User(getCurrentUser().getID(), getCurrentUser().name(), student));
            addCourseToUsers(changedUsers, updatedCourse);

            return true;

        } else if (matchingCourse.size() > 1) {
            System.out.println("There are duplicate instances of this course");
        } else {
            System.out.println("There are no instances of this course");
        }
        return false;
    }

    // I need this to return the dict of students affected to add back the course to the correct users
    private HashMap<String, List<User>> removeCourseFromUsers(List<User> userList, Course course) {
        List<User> enrolledStudents = userList.stream().filter(user -> {
            if (user.role() instanceof Student) {
                Student student = (Student) user.role();
                return CourseService.courseInCourses(student.enrolled_courses(), course);
            }  else {
                return false;
            }
        }).toList();

        List<User> completedStudents = userList.stream().filter(user -> {
            if (user.role() instanceof Student) {
                Student student = (Student) user.role();
                return CourseService.courseInCourses(student.completed_courses(), course);
            } else {
                return false;
            }
        }).toList();

        List<User> waitlistedStudents = userList.stream().filter(user -> {
            if (user.role() instanceof Student) {
                Student student = (Student) user.role();
                return CourseService.courseInCourses(student.waitlisted_courses(), course);
            } else {
                return false;
            }
        }).toList();

        List<User> updatedEnrolledStudents = enrolledStudents.stream().map(user -> removeCourseFromUser(user, course)).toList();
        List<User> updatedCompletedStudents = completedStudents.stream().map(user -> removeCourseFromUser(user, course)).toList();
        List<User> updatedWaitlistedStudents = waitlistedStudents.stream().map(user -> removeCourseFromUser(user, course)).toList();

        this.users.removeAll(enrolledStudents);
        this.users.removeAll(completedStudents);
        this.users.removeAll(waitlistedStudents);

        this.users.addAll(updatedEnrolledStudents);
        this.users.addAll(updatedCompletedStudents);
        this.users.addAll(updatedWaitlistedStudents);

        HashMap<String, List<User>> result = new HashMap<>();
        result.put("Enrolled", updatedEnrolledStudents);
        result.put("Completed", completedStudents);
        result.put("Waitlisted", waitlistedStudents);

        return result;
    }

    private User removeCourseFromUser(User user, Course course) {
        Student student = (Student) user.role();
        List<Course> updatedEnrolled = new ArrayList<>(student.enrolled_courses());
        List<Course> updatedCompleted = new ArrayList<>(student.completed_courses());
        List<Course> filteredEnrolled = CourseService.removeCourseFromCourses(updatedEnrolled, course);
        List<Course> filteredCompleted = CourseService.removeCourseFromCourses(updatedCompleted, course);
        updatedEnrolled.clear();
        updatedEnrolled.addAll(filteredEnrolled);
        updatedCompleted.clear();
        updatedCompleted.addAll(filteredCompleted);
        student.setEnrolled_courses(updatedEnrolled);
        student.setCompleted_courses(updatedCompleted);

        return new User(user.getID(), user.name(), student);
    }

    private void addCourseToUsers(HashMap<String, List<User>> userMap, Course course) {
        List<User> updatedEnrolledStudents = userMap.get("Enrolled").stream().map(user -> {
            Student student = (Student) user.role();
            student.enrolled_courses().add(course);
            return new User(user.getID(), user.name(), student);
        }).toList();

        List<User> updatedCompletedStudents = userMap.get("Completed").stream().map(user -> {
            Student student = (Student) user.role();
            student.completed_courses().add(course);
            return new User(user.getID(), user.name(), student);
        }).toList();

        List<User> updatedWaitlistedStudents = userMap.get("Waitlisted").stream().map(user -> {
            Student student = (Student) user.role();
            student.waitlisted_courses().add(course);
            return new User(user.getID(), user.name(), student);
        }).toList();

        List<User> allStudents = new ArrayList<>();
        allStudents.addAll(userMap.get("Enrolled"));
        allStudents.addAll(userMap.get("Completed"));
        allStudents.addAll(userMap.get("Waitlisted"));

        this.users.removeAll(allStudents);
        this.users.addAll(updatedEnrolledStudents);
        this.users.addAll(updatedCompletedStudents);
        this.users.addAll(updatedWaitlistedStudents);
    }

    public void addNewUser(User user) {
        users.add(user);
    }

    public void unregisterCourse(Course course) {
        if (getCurrentUser().role() instanceof Student) {
            setCurrentUser(removeCourseFromUser(getCurrentUser(), course));
        }
        removeCourseFromSchedule(course);
    }

    private void removeCourseFromSchedule(Course course) {
        CourseRow courseToBeRemoved = new CourseRow(course);
        selectedCourseRows.setAll(selectedCourseRows.stream().filter(courseRow -> !CourseService.equalCourses(courseRow.getCourse(), courseToBeRemoved.getCourse())).toList());
    }

    public Root exportContext() {
        return new Root(this.users, this.master_list_courses);
    }

    public ObservableList<Course> getSelectedCourses() {
        return selectedCourses;
    }

    public ObservableList<CourseRow> getSelectedCourseRows() {
        return selectedCourseRows;
    }

    public void clearSelectedCourses() {
        selectedCourses.clear();
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public FilteredList<CourseRow> getFilteredCourseRows() {
        return filteredCourseRows;
    }

    public void setRootUserType(RootUserType type) {
        this.rootUserType = type;
    }

    public RootUserType getRootUserType() {
        return rootUserType;
    }

}
