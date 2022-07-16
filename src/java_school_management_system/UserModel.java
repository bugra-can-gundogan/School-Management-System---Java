/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_school_management_system;

/**
 *
 * @author bugra
 */
public class UserModel {
    
    private int userId;
    private String userName;
    private String Name;
    private String Surname;
    private String Department;
    private String Faculty;
    private String Gender;
    private String MobileNumber;
    private String DateOfBirth;
    private int userRole;
    private int supervisor;

    public UserModel(int userId, String userName, String Name, String Surname, String Department, String Faculty, String Gender, String MobileNumber, String DateOfBirth, int userRole, int supervisor) {
        this.userId = userId;
        this.userName = userName;
        this.Name = Name;
        this.Surname = Surname;
        this.Department = Department;
        this.Faculty = Faculty;
        this.Gender = Gender;
        this.MobileNumber = MobileNumber;
        this.DateOfBirth = DateOfBirth;
        this.userRole = userRole;
        this.supervisor = supervisor;
    }
    
    public Boolean Authenticate(){
        return true;
    }
    
    public Boolean RegisterCourse(){
        return true;
    }
    
    public Boolean BecomeSupervisor(){
        return true;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String Faculty) {
        this.Faculty = Faculty;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String MobileNumber) {
        this.MobileNumber = MobileNumber;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public int getSupervisor() {
        return supervisor;
    }
    
    public String getSupervisorString() {
        if(supervisor == 0){
            return "No";
        }else if(supervisor == 1){
            return "Waiting to be confirmed by admin";
        }else{
            return "Yes";
        }
    }

    public void setSupervisor(int supervisor) {
        this.supervisor = supervisor;
    }
}
