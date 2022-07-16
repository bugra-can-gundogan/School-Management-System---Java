/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_school_management_system;

/**
 *
 * @author bugra
 */
public class CourseModel {
    
    private int CourseID;
    private String CourseName;
    private String CourseCode;
    private String CourseDay;
    private int CourseHour;
    private String CourseTutor;
    private String CourseDepartment;
    private String CourseFaculty;
    private int CourseCredit;
    private String CourseExamDate;
    private String CourseAnnouncement;
    private int CourseTutorID;



    public CourseModel(int CourseID, String CourseName, String CourseCode, String CourseDay, int CourseHour, String CourseTutor, String CourseDepartment, String CourseFaculty, int CourseCredit, String CourseExamDate, String CourseAnnouncement,int CourseTutorID) {
        this.CourseID = CourseID;
        this.CourseName = CourseName;
        this.CourseCode = CourseCode;
        this.CourseDay = CourseDay;
        this.CourseHour = CourseHour;
        this.CourseTutor = CourseTutor;
        this.CourseDepartment = CourseDepartment;
        this.CourseFaculty = CourseFaculty;
        this.CourseCredit = CourseCredit;
        this.CourseExamDate = CourseExamDate;
        this.CourseAnnouncement = CourseAnnouncement;
        this.CourseTutorID = CourseTutorID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int CourseID) {
        this.CourseID = CourseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String CourseCode) {
        this.CourseCode = CourseCode;
    }

    public String getCourseDay() {
        return CourseDay;
    }

    public void setCourseDay(String CourseDay) {
        this.CourseDay = CourseDay;
    }

    public int getCourseHour() {
        return CourseHour;
    }

    public void setCourseHour(int CourseHour) {
        this.CourseHour = CourseHour;
    }

    public String getCourseTutor() {
        return CourseTutor;
    }

    public void setCourseTutor(String CourseTutor) {
        this.CourseTutor = CourseTutor;
    }

    public String getCourseDepartment() {
        return CourseDepartment;
    }

    public void setCourseDepartment(String CourseDepartment) {
        this.CourseDepartment = CourseDepartment;
    }

    public String getCourseFaculty() {
        return CourseFaculty;
    }

    public void setCourseFaculty(String CourseFaculty) {
        this.CourseFaculty = CourseFaculty;
    }

    public int getCourseCredit() {
        return CourseCredit;
    }

    public void setCourseCredit(int CourseCredit) {
        this.CourseCredit = CourseCredit;
    }

    public String getCourseExamDate() {
        return CourseExamDate;
    }

    public void setCourseExamDate(String CourseExamDate) {
        this.CourseExamDate = CourseExamDate;
    }
    
    public String getCourseAnnouncement() {
        return CourseAnnouncement;
    }

    public void setCourseAnnouncement(String CourseAnnouncement) {
        this.CourseAnnouncement = CourseAnnouncement;
    }
    
    public int getCourseTutorID() {
        return CourseTutorID;
    }

    public void setCourseTutorID(int CourseTutorID) {
        this.CourseTutorID = CourseTutorID;
    }
    
    
}
