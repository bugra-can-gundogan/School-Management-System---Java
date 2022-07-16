/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_school_management_system;

/**
 *
 * @author bugra
 */
public class RegisteredCourseModel {
    private int ID;
    private int CourseID;
    private int StudentID;
    private int Situation;
    private int Grade;
    private int TutorID;
    private UserModel StudentM;
    private CourseModel CourseM;
    private UserModel TutorM;

    public RegisteredCourseModel(int ID, int CourseID, int StudentID, int Situation, int Grade, int TutorID, UserModel StudentM, CourseModel CourseM, UserModel TutorM) {
        this.ID = ID;
        this.CourseID = CourseID;
        this.StudentID = StudentID;
        this.Situation = Situation;
        this.Grade = Grade;
        this.TutorID = TutorID;
        this.StudentM = StudentM;
        this.CourseM = CourseM;
        this.TutorM = TutorM;
    }
    
    public int getTutorID() {
        return TutorID;
    }

    public void setTutorID(int TutorID) {
        this.TutorID = TutorID;
    }

    public UserModel getTutorM() {
        return TutorM;
    }

    public void setTutorM(UserModel TutorM) {
        this.TutorM = TutorM;
    }
    
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int CourseID) {
        this.CourseID = CourseID;
    }

    public int getStudentID() {
        return StudentID;
    }

    public void setStudentID(int StudentID) {
        this.StudentID = StudentID;
    }

    public int getSituation() {
        return Situation;
    }

    public void setSituation(int Situation) {
        this.Situation = Situation;
    }

    public int getGrade() {
        return Grade;
    }

    public void setGrade(int Grade) {
        this.Grade = Grade;
    }

    public UserModel getStudentM() {
        return StudentM;
    }

    public void setStudentM(UserModel StudentM) {
        this.StudentM = StudentM;
    }

    public CourseModel getCourseM() {
        return CourseM;
    }

    public void setCourseM(CourseModel CourseM) {
        this.CourseM = CourseM;
    }
}
