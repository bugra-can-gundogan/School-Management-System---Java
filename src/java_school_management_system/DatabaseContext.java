/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_school_management_system;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author bugra
 */
public class DatabaseContext {
    
    Connection con = null;
    
    public static Connection ConnectionDB(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            System.out.println("Connection succeeded.");
            return con;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    public static Boolean RegisterUser(String name, String surname, String password, String username, String dateOfBirth, String gender, String department, String faculty, int userRole, String mobileNumber, int supervisor){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "INSERT INTO Users (Name, Surname, Password, Gender, Username, Mobile_Number, Date_Of_Birth, Department, Faculty, Supervisor, UserRole) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, password);
            statement.setString(4, gender);
            statement.setString(5, username);
            statement.setString(6, mobileNumber);
            statement.setString(7, dateOfBirth);
            statement.setString(8, department);
            statement.setString(9, faculty);
            statement.setInt(10, supervisor);
            statement.setInt(11, userRole);
            statement.executeUpdate();       
            connection.close();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static UserModel LoginUser(String username, String password){
        
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Users WHERE Username LIKE ? AND Password LIKE ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet res = statement.executeQuery();
            int userId = res.getInt(1);
            String userName = res.getString(6);
            String Name = res.getString(2);
            String Surname = res.getString(3);
            String Department = res.getString(9);
            String Faculty = res.getString(10);
            String Gender = res.getString(5);
            String MobileNumber = res.getString(7);
            String DateOfBirth = res.getString(8);
            int userRole = res.getInt(12);
            int supervisor = res.getInt(11);
            connection.close();	
            UserModel um = new UserModel(userId, userName, Name, Surname, Department, Faculty, Gender, MobileNumber, DateOfBirth, userRole, supervisor);
            return um;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static ArrayList<UserModel> GetAllStudents(String byDepartment){
        ArrayList<UserModel> myList = new ArrayList<UserModel>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Users Where UserRole=0";
            if(byDepartment != ""){
                sql = "SELECT * from Users Where UserRole=0 and Department LIKE ?";
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            if(byDepartment != ""){
                statement.setString(1,byDepartment);
            }
            ResultSet res = statement.executeQuery();
            while(res.next()){
                int userId = res.getInt(1);
                String userName = res.getString(6);
                String Name = res.getString(2);
                String Surname = res.getString(3);
                String Department = res.getString(9);
                String Faculty = res.getString(10);
                String Gender = res.getString(5);
                String MobileNumber = res.getString(7);
                String DateOfBirth = res.getString(8);
                int userRole = res.getInt(12);
                int supervisor = res.getInt(11);
                
                UserModel um = new UserModel(userId, userName, Name, Surname, Department, Faculty, Gender, MobileNumber, DateOfBirth, userRole, supervisor);
                myList.add(um);
            }
            connection.close();	
            return myList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }
    
    public static UserModel GetStudentById(int id){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Users Where ID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            int userId = -1;
            String userName = "";
            String Name = "";
            String Surname = "";
            String Department = "";
            String Faculty = "";
            String Gender = "";
            String MobileNumber = "";
            String DateOfBirth = "";
            int userRole = -1;
            int supervisor = -1;
            if(res.next()){
                 userId = res.getInt(1);
                 userName = res.getString(6);
                 Name = res.getString(2);
                 Surname = res.getString(3);
                 Department = res.getString(9);
                 Faculty = res.getString(10);
                 Gender = res.getString(5);
                 MobileNumber = res.getString(7);
                 DateOfBirth = res.getString(8);
                 userRole = res.getInt(12);
                 supervisor = res.getInt(11);
            }

            
            UserModel um = new UserModel(userId, userName, Name, Surname, Department, Faculty, Gender, MobileNumber, DateOfBirth, userRole, supervisor);
            connection.close();	
            if(userId == -1){
               return null; 
            }
            return um;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }
    
    public static ArrayList<UserModel> GetAllTeachers(String byDepartment){
        ArrayList<UserModel> myList = new ArrayList<UserModel>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Users Where UserRole=1";
            if(byDepartment != ""){
                sql = "SELECT * from Users Where UserRole=1 and Department LIKE ?";
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            if(byDepartment != ""){
                statement.setString(1,byDepartment);
            }
            ResultSet res = statement.executeQuery();
            while(res.next()){
                int userId = res.getInt(1);
                String userName = res.getString(6);
                String Name = res.getString(2);
                String Surname = res.getString(3);
                String Department = res.getString(9);
                String Faculty = res.getString(10);
                String Gender = res.getString(5);
                String MobileNumber = res.getString(7);
                String DateOfBirth = res.getString(8);
                int userRole = res.getInt(12);
                int supervisor = res.getInt(11);
                
                UserModel um = new UserModel(userId, userName, Name, Surname, Department, Faculty, Gender, MobileNumber, DateOfBirth, userRole, supervisor);
                myList.add(um);
            }
            connection.close();	
            return myList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static UserModel GetTeacherById(int id){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Users Where UserRole=1 AND ID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            int userId = res.getInt(1);
            String userName = res.getString(6);
            String Name = res.getString(2);
            String Surname = res.getString(3);
            String Department = res.getString(9);
            String Faculty = res.getString(10);
            String Gender = res.getString(5);
            String MobileNumber = res.getString(7);
            String DateOfBirth = res.getString(8);
            int userRole = res.getInt(12);
            int supervisor = res.getInt(11);
            connection.close();	
            UserModel um = new UserModel(userId, userName, Name, Surname, Department, Faculty, Gender, MobileNumber, DateOfBirth, userRole, supervisor);
            return um;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Boolean AddCourse(CourseModel course){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "INSERT INTO Courses (Course_Name, Course_Code, CourseDepartment, CourseFaculty, CourseTutor, CourseDay, CourseHour, CourseCredit, CourseExamDate, CourseAnnouncement, CourseTutorID) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseCode());
            statement.setString(3, course.getCourseDepartment());
            statement.setString(4, course.getCourseFaculty());
            statement.setString(5, course.getCourseTutor());
            statement.setString(6, course.getCourseDay());
            statement.setString(7, String.valueOf(course.getCourseHour()));
            statement.setInt(8, course.getCourseCredit());
            statement.setString(9, course.getCourseExamDate());
            statement.setString(10, course.getCourseAnnouncement());
            statement.setInt(11, course.getCourseTutorID());
            statement.executeUpdate();       
            connection.close();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static ArrayList<CourseModel> GetAllCourses(String byDepartment){
        ArrayList<CourseModel> myList = new ArrayList<CourseModel>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Courses";
            if(byDepartment != ""){
                sql = "SELECT * from Courses Where CourseDepartment LIKE ?";
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            if(byDepartment != ""){
                statement.setString(1,byDepartment);
            }
            ResultSet res = statement.executeQuery();
            
            while(res.next()){
                int CourseID = res.getInt(1);
                String CourseName = res.getString(2);
                String CourseCode = res.getString(3);
                String CourseDepartment = res.getString(4);
                String CourseFaculty = res.getString(5);
                String CourseTutor = res.getString(6);
                String CourseDay = res.getString(7);
                int CourseHour = res.getInt(8);
                int CourseCredit = res.getInt(9);
                String CourseExamDate = res.getString(10);
                String CourseAnnouncement = res.getString(11);
                int CourseTutorId = res.getInt(12);
                
                CourseModel cm = new CourseModel(CourseID, CourseName, CourseCode, CourseDay, CourseHour, CourseTutor, CourseDepartment, CourseFaculty, CourseCredit, CourseExamDate, CourseAnnouncement,CourseTutorId);
                myList.add(cm);
            }
              
            
            connection.close();	
            return myList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static ArrayList<CourseModel> GetAllCoursesByTeacher(int TeacherID){
        ArrayList<CourseModel> myList = new ArrayList<CourseModel>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Courses Where CourseTutorID = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,TeacherID);
            ResultSet res = statement.executeQuery();
            
            while(res.next()){
                int CourseID = res.getInt(1);
                String CourseName = res.getString(2);
                String CourseCode = res.getString(3);
                String CourseDepartment = res.getString(4);
                String CourseFaculty = res.getString(5);
                String CourseTutor = res.getString(6);
                String CourseDay = res.getString(7);
                int CourseHour = res.getInt(8);
                int CourseCredit = res.getInt(9);
                String CourseExamDate = res.getString(10);
                String CourseAnnouncement = res.getString(11);
                int CourseTutorId = res.getInt(12);
                
                CourseModel cm = new CourseModel(CourseID, CourseName, CourseCode, CourseDay, CourseHour, CourseTutor, CourseDepartment, CourseFaculty, CourseCredit, CourseExamDate, CourseAnnouncement,CourseTutorId);
                myList.add(cm);
            }
              
            
            connection.close();	
            return myList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static CourseModel GetCourseById(int id){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Courses Where ID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            int CourseID = res.getInt(1);
            String CourseName = res.getString(2);
            String CourseCode = res.getString(3);
            String CourseDepartment = res.getString(4);
            String CourseFaculty = res.getString(5);
            String CourseTutor = res.getString(6);
            String CourseDay = res.getString(7);
            int CourseHour = res.getInt(8);
            int CourseCredit = res.getInt(9);
            String CourseExamDate = res.getString(10);
            String CourseAnnouncement = res.getString(11);
            int CourseTutorId = res.getInt(12);
            connection.close();	
            CourseModel cm = new CourseModel(CourseID, CourseName, CourseCode, CourseDay, CourseHour, CourseTutor, CourseDepartment, CourseFaculty, CourseCredit, CourseExamDate, CourseAnnouncement,CourseTutorId);
            
            return cm;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Boolean ApproveSupervisor(int id){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("UPDATE Users SET Supervisor = ? WHERE ID = ?");
            st.setInt(1,2);
            st.setInt(2,id);
            st.executeUpdate();
            connection.close();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    
    }
    
    public static Boolean ApplyToBeSupervisor(int id){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("UPDATE Users SET Supervisor = ? WHERE ID = ?");
            st.setInt(1,1);
            st.setInt(2,id);
            st.executeUpdate();
            connection.close();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static Boolean DisapproveSupervisor(int id){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("UPDATE Users SET Supervisor = ? WHERE ID = ?");
            st.setInt(1,0);
            st.setInt(2,id);
            st.executeUpdate();
            connection.close();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static ArrayList<UserModel> GetSupervisors(){
        ArrayList<UserModel> myList = new ArrayList<UserModel>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Users Where Supervisor = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,2);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                int userId = res.getInt(1);
                String userName = res.getString(6);
                String Name = res.getString(2);
                String Surname = res.getString(3);
                String Department = res.getString(9);
                String Faculty = res.getString(10);
                String Gender = res.getString(5);
                String MobileNumber = res.getString(7);
                String DateOfBirth = res.getString(8);
                int userRole = res.getInt(12);
                int supervisor = res.getInt(11);
                
                UserModel um = new UserModel(userId, userName, Name, Surname, Department, Faculty, Gender, MobileNumber, DateOfBirth, userRole, supervisor);
                myList.add(um);
            }
            connection.close();	
            return myList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static ArrayList<UserModel> GetSupervisorCandidates(){
        ArrayList<UserModel> myList = new ArrayList<UserModel>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Users Where Supervisor = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,1);
            ResultSet res = statement.executeQuery();
         
            while(res.next()){
                int userId = res.getInt(1);
                String userName = res.getString(6);
                String Name = res.getString(2);
                String Surname = res.getString(3);
                String Department = res.getString(9);
                String Faculty = res.getString(10);
                String Gender = res.getString(5);
                String MobileNumber = res.getString(7);
                String DateOfBirth = res.getString(8);
                int userRole = res.getInt(12);
                int supervisor = res.getInt(11);
                
                UserModel um = new UserModel(userId, userName, Name, Surname, Department, Faculty, Gender, MobileNumber, DateOfBirth, userRole, supervisor);
                myList.add(um);
            }
            connection.close();	
            return myList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static ArrayList<FacultyModel> GetFaculties(){
        ArrayList<FacultyModel> myList = new ArrayList<FacultyModel>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Faculties";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                FacultyModel fm = new FacultyModel(res.getInt(1), res.getString(2));
                myList.add(fm);
            }
            connection.close();
            return myList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return null;
        }
    }
    
    public static ArrayList<DepartmentModel> GetAllDepartments(){
        ArrayList<DepartmentModel> myList = new ArrayList<DepartmentModel>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Departments";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                DepartmentModel dm = new DepartmentModel(res.getInt(1), res.getString(2), res.getInt(3));
                myList.add(dm);
            }
            connection.close();	
            return myList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
        
    public static ArrayList<DepartmentModel> GetDepartmentsByFaculty(int id){
        ArrayList<DepartmentModel> myList = new ArrayList<DepartmentModel>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Departments Where Faculty_ID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            int id_dep = -1;
            String name = "";
            int faculty_id = -1;
            while(res.next()){
                 id_dep = res.getInt(1);
                 name = res.getString(2);
                 faculty_id = res.getInt(3);
                 DepartmentModel dm = new DepartmentModel(id_dep, name, faculty_id);
                 myList.add(dm);
            }
            
            connection.close();	
            return myList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Boolean UpdateCourseRegistration(int open){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("UPDATE CourseRegistrationTermTable SET Open = ? WHERE ID = ?");
            st.setInt(1,open);
            st.setInt(2,1);
            st.executeUpdate();
            connection.close();	
            return true;
        }catch(SQLException ex){
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static Boolean GetCourseRegistrationTerm(){
        Boolean openNes = false;
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("SELECT * from CourseRegistrationTermTable WHERE ID = ?");
            st.setInt(1,1);
            ResultSet rs = st.executeQuery();
            int open = rs.getInt(2);
            connection.close();	
            if(open == 1){
                openNes = true;
            }else{
                openNes = false;
            }
            return openNes;
        }catch(SQLException ex){
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return openNes;
        }
    }
    
    
    public static ArrayList<RegisteredCourseModel> GetAllRegisteredCourses(){
        try{
            ArrayList<RegisteredCourseModel> myList = new ArrayList<RegisteredCourseModel>();
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("SELECT * from RegisteredCourses");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int rc_id = rs.getInt(1);
                int rc_Cid = rs.getInt(2);
                int rc_Sid = rs.getInt(3);
                int rc_sit = rs.getInt(4);
                int rc_grade = rs.getInt(5);
                int rc_Tid = rs.getInt(6);
                UserModel student = GetStudentById(rc_Sid);
                UserModel teacher = GetTeacherById(rc_Tid);
                CourseModel course = GetCourseById(rc_Cid);
                
                RegisteredCourseModel rcm = new RegisteredCourseModel(rc_id, rc_Cid, rc_Sid, rc_sit, rc_grade, rc_Tid, student, course, teacher);
                
                myList.add(rcm);
            }

            connection.close();	
            
            return myList;
        }catch(SQLException ex){
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static ArrayList<RegisteredCourseModel> GetAllRegisteredCoursesByStudent(int id){
        try{
            ArrayList<RegisteredCourseModel> myList = new ArrayList<RegisteredCourseModel>();
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("SELECT * from RegisteredCourses WHERE StudentID = ?");
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int rc_id = rs.getInt(1);
                int rc_Cid = rs.getInt(2);
                int rc_Sid = rs.getInt(3);
                int rc_sit = rs.getInt(4);
                int rc_grade = rs.getInt(5);
                int rc_Tid = rs.getInt(6);
                UserModel student = GetStudentById(rc_Sid);
                UserModel teacher = GetTeacherById(rc_Tid);
                CourseModel course = GetCourseById(rc_Cid);
                
                RegisteredCourseModel rcm = new RegisteredCourseModel(rc_id, rc_Cid, rc_Sid, rc_sit, rc_grade, rc_Tid, student, course, teacher);
                
                myList.add(rcm);
            }

            connection.close();	
            
            return myList;
        }catch(SQLException ex){
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static ArrayList<RegisteredCourseModel> GetAllRegisteredCoursesByTeacher(int id){
        try{
            ArrayList<RegisteredCourseModel> myList = new ArrayList<RegisteredCourseModel>();
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("SELECT * from RegisteredCourses WHERE TutorID = ?");
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int rc_id = rs.getInt(1);
                int rc_Cid = rs.getInt(2);
                int rc_Sid = rs.getInt(3);
                int rc_sit = rs.getInt(4);
                int rc_grade = rs.getInt(5);
                int rc_Tid = rs.getInt(6);
                UserModel student = GetStudentById(rc_Sid);
                UserModel teacher = GetTeacherById(rc_Tid);
                CourseModel course = GetCourseById(rc_Cid);
                
                RegisteredCourseModel rcm = new RegisteredCourseModel(rc_id, rc_Cid, rc_Sid, rc_sit, rc_grade, rc_Tid, student, course, teacher);
                
                myList.add(rcm);
            }

            connection.close();	
            
            return myList;
        }catch(SQLException ex){
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static ArrayList<RegisteredCourseModel> GetRegisteredCourseById(int id){
        try{
            ArrayList<RegisteredCourseModel> myList = new ArrayList<RegisteredCourseModel>();
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("SELECT * from RegisteredCourses WHERE CourseID = ?");
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int rc_id = rs.getInt(1);
                int rc_Cid = rs.getInt(2);
                int rc_Sid = rs.getInt(3);
                int rc_sit = rs.getInt(4);
                int rc_grade = rs.getInt(5);
                int rc_Tid = rs.getInt(6);
                UserModel student = GetStudentById(rc_Sid);
                UserModel teacher = GetTeacherById(rc_Tid);
                CourseModel course = GetCourseById(rc_Cid);
                
                RegisteredCourseModel rcm = new RegisteredCourseModel(rc_id, rc_Cid, rc_Sid, rc_sit, rc_grade, rc_Tid, student, course, teacher);
                
                myList.add(rcm);
            }

            connection.close();	
            
            return myList;
        }catch(SQLException ex){
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Boolean RegisterToCourse(RegisteredCourseModel rcm){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "INSERT INTO RegisteredCourses (CourseID, StudentID, Situation, Grade, TutorID) VALUES (?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, rcm.getCourseID());
            statement.setInt(2, rcm.getStudentID());
            statement.setInt(3, rcm.getSituation());
            statement.setInt(4, rcm.getGrade());
            statement.setInt(5, rcm.getTutorID());
            statement.executeUpdate();       
            connection.close();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    public static Boolean ChangeExamDate(int id, String examDate){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("UPDATE Courses SET CourseExamDate = ? WHERE ID = ?");
            st.setString(1,examDate);
            st.setInt(2,id);
            st.executeUpdate();
            connection.close();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static Boolean ChangeAnnouncement(int id, String announcement){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("UPDATE Courses SET CourseAnnouncement = ? WHERE ID = ?");
            st.setString(1,announcement);
            st.setInt(2,id);
            st.executeUpdate();
            connection.close();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    public static Boolean GradeStudentById(int id, int grade){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("UPDATE RegisteredCourses SET Grade = ? WHERE ID = ?");
            st.setInt(1,grade);
            st.setInt(2,id);
            st.executeUpdate();
            connection.close();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static Boolean ApproveStudentsCourseRegistration(int student_id){
         try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("UPDATE RegisteredCourses SET Situation = 1 WHERE StudentID = ?");
            st.setInt(1,student_id);
            st.executeUpdate();
            connection.close();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static Boolean DisapproveStudentsCourseRegistration(int student_id){
         try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            PreparedStatement st = connection.prepareStatement("DELETE from RegisteredCourses WHERE StudentID = ?");
            st.setInt(1,student_id);
            st.executeUpdate();
            connection.close();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static ArrayList<UserModel> GetStudentByName(String _name){
        try {
            ArrayList<UserModel> myList = new ArrayList<UserModel>();
            Connection connection = DriverManager.getConnection("jdbc:sqlite:GundoganSMSP.db");
            String sql = "SELECT * from Users WHERE (Name LIKE ? Or Surname LIKE ?) AND UserRole = 0;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, _name);
            statement.setString(2, _name);
            ResultSet res = statement.executeQuery();
            while(res.next()){
                int userId = res.getInt(1);
                String userName = res.getString(6);
                String Name = res.getString(2);
                String Surname = res.getString(3);
                String Department = res.getString(9);
                String Faculty = res.getString(10);
                String Gender = res.getString(5);
                String MobileNumber = res.getString(7);
                String DateOfBirth = res.getString(8);
                int userRole = res.getInt(12);
                int supervisor = res.getInt(11);
                UserModel um = new UserModel(userId, userName, Name, Surname, Department, Faculty, Gender, MobileNumber, DateOfBirth, userRole, supervisor);
                myList.add(um);
            }
            connection.close();	        
            return myList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
