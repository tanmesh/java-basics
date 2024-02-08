package org.example.courseRegistrationService;

import java.util.*;

class Course {
    private String courseId;
    private String courseName;
    private int credits;
    private Set<String> enrolledStudents;

    public Course(String courseId, String courseName, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.enrolledStudents = new HashSet<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public Set<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void enrollStudent(String studentId) {
        enrolledStudents.add(studentId);
    }
}


public class CourseRegistrationService {
    private Map<String, Course> courseMap;
    private Map<String, Integer> studentCredits;

    public CourseRegistrationService() {
        this.courseMap = new HashMap<>();
        this.studentCredits = new HashMap<>();
    }

    public List<String> processQueries(List<List<String>> queries) {
        List<String> results = new ArrayList<>();
        for (List<String> query : queries) {
            String operation = query.get(0);
            if (operation.equals("CREATE_COURSE")) {
                String courseId = query.get(1);
                String courseName = query.get(2);
                int credits = Integer.parseInt(query.get(3));
                if (!courseMap.containsKey(courseId)) {
                    courseMap.put(courseId, new Course(courseId, courseName, credits));
                }
            } else {
                String courseId = query.get(1);
                String studentId = query.get(2);
                if (courseMap.containsKey(courseId)
                        && studentCredits.getOrDefault(studentId, 0) + courseMap.get(courseId).getCredits() <= 24
                        && !courseMap.get(courseId).getEnrolledStudents().contains(studentId)) {
                    courseMap.get(courseId).enrollStudent(studentId);
                    studentCredits.put(studentId, studentCredits.getOrDefault(studentId, 0) + courseMap.get(courseId).getCredits());
                    results.add("true");
                } else {
                    results.add("false");
                }
            }
        }
        return results;
    }

    public static void main(String[] args) {
        List<List<String>> queries = new ArrayList<>();
        queries.add(List.of("CREATE_COURSE", "C001", "Math", "4"));
        queries.add(List.of("CREATE_COURSE", "C002", "Physics", "4"));
        queries.add(List.of("CREATE_COURSE", "C003", "DSA", "4"));
        queries.add(List.of("CREATE_COURSE", "C004", "DBMS", "4"));
        queries.add(List.of("CREATE_COURSE", "C005", "Mobile Computing", "4"));
        queries.add(List.of("CREATE_COURSE", "C006", "OS", "4"));
        queries.add(List.of("CREATE_COURSE", "C007", "IS", "4"));
        queries.add(List.of("REGISTER_COURSE", "C001", "S001"));
        queries.add(List.of("REGISTER_COURSE", "C001", "S002"));
        queries.add(List.of("REGISTER_COURSE", "C002", "S001"));
        queries.add(List.of("REGISTER_COURSE", "C002", "S002"));
        queries.add(List.of("REGISTER_COURSE", "C003", "S001"));
        queries.add(List.of("REGISTER_COURSE", "C003", "S002"));
        queries.add(List.of("REGISTER_COURSE", "C004", "S001"));
        queries.add(List.of("REGISTER_COURSE", "C004", "S002"));
        queries.add(List.of("REGISTER_COURSE", "C005", "S001"));
        queries.add(List.of("REGISTER_COURSE", "C005", "S002"));
        queries.add(List.of("REGISTER_COURSE", "C006", "S001"));
        queries.add(List.of("REGISTER_COURSE", "C006", "S002"));
        queries.add(List.of("REGISTER_COURSE", "C007", "S001"));
        queries.add(List.of("REGISTER_COURSE", "C007", "S002"));

        CourseRegistrationService system = new CourseRegistrationService();
        List<String> results = system.processQueries(queries);
        System.out.println(results);
    }
}
