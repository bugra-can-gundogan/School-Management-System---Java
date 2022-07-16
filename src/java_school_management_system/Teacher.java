/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package java_school_management_system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bugra
 */
public class Teacher extends javax.swing.JFrame {
    
    
    /**
     * Creates new form Teacher
     */
    public Teacher() {
        initComponents();
        this.setResizable(false);
        if(ActiveModels._activeUser.getSupervisor() == 1 || ActiveModels._activeUser.getSupervisor() == 2){
            ApplySupervisorBtn.setEnabled(false);
            if(ActiveModels._activeUser.getSupervisor() == 2 && ActiveModels.CourseRegOpen){
                jTabbedPane1.setEnabledAt(1, true);
            }else{
                jTabbedPane1.setEnabledAt(1, false);
            }
        }else{
            ApplySupervisorBtn.setEnabled(true);
            jTabbedPane1.setEnabledAt(1, false);
        }
        
        teacherNameLbl.setText("Name: " + ActiveModels._activeUser.getName());
        teacherSurnameLbl.setText("Surname: " + ActiveModels._activeUser.getSurname());
        teacherGenderLbl.setText("Gender: " + ActiveModels._activeUser.getGender());
        teacherMobNumberLbl.setText("Mobile Number: " + ActiveModels._activeUser.getMobileNumber());
        teacherDepLbl.setText("Department: " + ActiveModels._activeUser.getDepartment());
        teacherFacLbl.setText("Faculty: " + ActiveModels._activeUser.getFaculty());
        teacherSupLbl.setText("Supervisor: " + ActiveModels._activeUser.getSupervisorString());
        
        getMyCoursesTableReady();
        RefreshTable(SearchedStudentTable);
        if(ActiveModels._activeUser.getSupervisor() == 2){
            getSupervisedStudentsTableReady();
        }
    }
    
    public void setSearchedStudentLabels(UserModel um){
        if(um == null){
            SearchedStBdate.setText("Birthdate: -");
            SearchedStDep.setText("Department: -");
            SearchedStFac.setText("Faculty: -");
            SearchedStGender.setText("Gender: -");
            SearchedStMob.setText("Mobile Number: -");
            SearchedStName.setText("Name: -");
            SearchedStSurname.setText("Surname: -");
        }else{
            SearchedStBdate.setText("Birthdate: " + um.getDateOfBirth());
            SearchedStDep.setText("Department: " + um.getDepartment());
            SearchedStFac.setText("Faculty: " + um.getFaculty());
            SearchedStGender.setText("Gender: " + um.getGender());
            SearchedStMob.setText("Mobile Number: " + um.getMobileNumber());
            SearchedStName.setText("Name: " + um.getName());
            SearchedStSurname.setText("Surname: " + um.getSurname());
        }
    }
    
    
    public void getSearchedStudentTableReady(String _name){
        RefreshTable(SearchedStudentTable);
        var st_list = DatabaseContext.GetStudentByName(_name);
        setSearchedStudentLabels(null);
        if(st_list == null){
            JOptionPane.showMessageDialog(null, "The student you searched for was not found.");
            return;
        }
        
        DefaultTableModel tblModel = (DefaultTableModel) SearchedStudentTable.getModel();
        for(int i = 0; i<st_list.size(); i++){
            String tbData[] = {
                    String.valueOf(st_list.get(i).getUserId()),
                    st_list.get(i).getName(),
                    st_list.get(i).getSurname(),
                    st_list.get(i).getDepartment()
            };
            tblModel.addRow(tbData);
        }
        
    }
    
    public void getSupervisedStudentsTableReady(){
        RefreshTable(SupervisedStudentsTable);
        var myList = DatabaseContext.GetAllStudents(ActiveModels._activeUser.getDepartment());
        
        DefaultTableModel tblModel = (DefaultTableModel) SupervisedStudentsTable.getModel();
        for(int i = 0; i<myList.size(); i++){
            String tbData[] = {
                    String.valueOf(myList.get(i).getUserId()),
                    myList.get(i).getName(),
                    myList.get(i).getSurname()
            };
            tblModel.addRow(tbData);
        }
        
    }
    
    public void ConfirmSCR(){
        int selected_row = SupervisedStudentsTable.getSelectedRow();

        if(selected_row == -1) {
            JOptionPane.showMessageDialog(null, "You did not select a student.");
            return;
        }
        
        var id = SupervisedStudentsTable.getValueAt(selected_row, 0);
        int id_int = Integer.valueOf((String) id);
        DatabaseContext.ApproveStudentsCourseRegistration(id_int);
        getSupervisedStudentsTableReady();
        RefreshTable(SupervisedTakenCoursesTable);
    }
    
    public void RejectSCR(){
        int selected_row = SupervisedStudentsTable.getSelectedRow();

        if(selected_row == -1) {
            JOptionPane.showMessageDialog(null, "You did not select a student.");
            return;
        }
        
        var id = SupervisedStudentsTable.getValueAt(selected_row, 0);
        int id_int = Integer.valueOf((String) id);
        DatabaseContext.DisapproveStudentsCourseRegistration(id_int);
        getSupervisedStudentsTableReady();
        RefreshTable(SupervisedTakenCoursesTable);
    }
    
    public void AddAnouncement(){
        int selected_row = tMyCoursesTable.getSelectedRow();

        if(selected_row == -1) {
            JOptionPane.showMessageDialog(null, "You did not select a course.");
            return;
        }
        
        var id = tMyCoursesTable.getValueAt(selected_row, 0);
        int id_int = Integer.valueOf((String) id);
        String announcement = AnnouncementTf.getText();
        
        if(announcement == ""){
            JOptionPane.showMessageDialog(null, "You did not enter an announcement.");
            return;
        }
        
        DatabaseContext.ChangeAnnouncement(id_int,announcement);
        getMyCoursesTableReady();
    }
    
    public void AddExamDate(){
        int selected_row = tMyCoursesTable.getSelectedRow();

        if(selected_row == -1) {
            JOptionPane.showMessageDialog(null, "You did not select a course.");
            return;
        }
        
        var id = tMyCoursesTable.getValueAt(selected_row, 0);
        int id_int = Integer.valueOf((String) id);
        
        String examD = examDateTf.getText();
        
        if(examD == ""){
            JOptionPane.showMessageDialog(null, "You did not enter an exam date.");
            return;
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
        	   Date date1 = dateFormat.parse(examD.trim());
        	   Date todayDate = new Date();
        	   long diffInMillies = Math.abs(date1.getTime() - todayDate.getTime());
        	   long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        	   
        	   if(diff <= 0) { //6200 days makes 17 years.
                           JOptionPane.showMessageDialog(null, "Please enter a valid exam date.");
        		   return;
        	   }
        	   
        } catch (ParseException pe) {
            JOptionPane.showMessageDialog(null, "Please enter a valid  exam date.");
            return;
        }
        
        DatabaseContext.ChangeExamDate(id_int, examD);
        getMyCoursesTableReady();      
    }
    
    public void RefreshTable(JTable table){
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            tblModel.removeRow(i);
        }
    }
    
    public void getMyCoursesTableReady(){
        RefreshTable(tMyCoursesTable);
        ArrayList<CourseModel> courseList = DatabaseContext.GetAllCoursesByTeacher(ActiveModels._activeUser.getUserId());
        DefaultTableModel tblModel = (DefaultTableModel) tMyCoursesTable.getModel();
        for(int i = 0; i<courseList.size(); i++){
            String tbData[] = {
                    String.valueOf(courseList.get(i).getCourseID()),
                    courseList.get(i).getCourseName(),
                    courseList.get(i).getCourseCode(), 
                    courseList.get(i).getCourseExamDate(),
                    courseList.get(i).getCourseDay(),
                    String.valueOf(courseList.get(i).getCourseHour()),
                    String.valueOf(courseList.get(i).getCourseAnnouncement())
            };
            tblModel.addRow(tbData);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        teacherSupLbl = new javax.swing.JLabel();
        teacherNameLbl = new javax.swing.JLabel();
        teacherSurnameLbl = new javax.swing.JLabel();
        teacherGenderLbl = new javax.swing.JLabel();
        teacherMobNumberLbl = new javax.swing.JLabel();
        teacherDepLbl = new javax.swing.JLabel();
        teacherFacLbl = new javax.swing.JLabel();
        ApplySupervisorBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        ConfirmCRBtn = new javax.swing.JButton();
        RejectCRBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        SupervisedStudentsTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        SupervisedTakenCoursesTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        gradingBTN = new javax.swing.JButton();
        ExamDateBtn = new javax.swing.JButton();
        examDateTf = new javax.swing.JTextField();
        AnnouncementTf = new javax.swing.JTextField();
        SubmitAnnBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tMyCoursesTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        SearchedStudentTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        SearchedStudentTf = new javax.swing.JTextField();
        SearchStudentBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        SearchedStName = new javax.swing.JLabel();
        SearchedStSurname = new javax.swing.JLabel();
        SearchedStGender = new javax.swing.JLabel();
        SearchedStBdate = new javax.swing.JLabel();
        SearchedStMob = new javax.swing.JLabel();
        SearchedStDep = new javax.swing.JLabel();
        SearchedStFac = new javax.swing.JLabel();
        SearchedStudentCoursesBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        teacherSupLbl.setText("Supervisor:");
        jPanel2.add(teacherSupLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 260, 40));

        teacherNameLbl.setText("Name: ");
        jPanel2.add(teacherNameLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 260, 40));

        teacherSurnameLbl.setText("Surname:");
        jPanel2.add(teacherSurnameLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 260, 40));

        teacherGenderLbl.setText("Gender:");
        jPanel2.add(teacherGenderLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 260, 40));

        teacherMobNumberLbl.setText("Mobile Number:");
        jPanel2.add(teacherMobNumberLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 260, 40));

        teacherDepLbl.setText("Department:");
        jPanel2.add(teacherDepLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 260, 40));

        teacherFacLbl.setText("Faculty:");
        jPanel2.add(teacherFacLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 260, 40));

        ApplySupervisorBtn.setText("Apply To Be Supervisor");
        ApplySupervisorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplySupervisorBtnActionPerformed(evt);
            }
        });
        jPanel2.add(ApplySupervisorBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 170, 40));

        jTabbedPane1.addTab("My Information", jPanel2);

        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ConfirmCRBtn.setText("Confirm");
        ConfirmCRBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmCRBtnActionPerformed(evt);
            }
        });
        jPanel10.add(ConfirmCRBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 98, 35));

        RejectCRBtn.setText("Reject");
        RejectCRBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RejectCRBtnActionPerformed(evt);
            }
        });
        jPanel10.add(RejectCRBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 99, 35));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        SupervisedStudentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Name", "Surname"
            }
        ));
        SupervisedStudentsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OpenCourseRegForSelectedStudent(evt);
            }
        });
        jScrollPane3.setViewportView(SupervisedStudentsTable);

        SupervisedTakenCoursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Course Name", "Course Code", "Professor", "Credit"
            }
        ));
        jScrollPane4.setViewportView(SupervisedTakenCoursesTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Supervisor", jPanel3);

        gradingBTN.setText("Grading");
        gradingBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradingBTNActionPerformed(evt);
            }
        });

        ExamDateBtn.setText("Enter Exam Date");
        ExamDateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExamDateBtnActionPerformed(evt);
            }
        });

        examDateTf.setText("00-00-0000");

        AnnouncementTf.setText("Dear Students...");

        SubmitAnnBtn.setText("Submit Announcement");
        SubmitAnnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitAnnBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(examDateTf, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ExamDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(AnnouncementTf, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SubmitAnnBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(gradingBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(examDateTf, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ExamDateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gradingBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AnnouncementTf, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SubmitAnnBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        tMyCoursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Code", "Exam Date", "Course Day", "Course Hour", "Announcement"
            }
        ));
        jScrollPane2.setViewportView(tMyCoursesTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("My Courses", jPanel4);

        SearchedStudentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Surname", "Department"
            }
        ));
        SearchedStudentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchedStudentSelected(evt);
            }
        });
        jScrollPane1.setViewportView(SearchedStudentTable);

        SearchStudentBtn.setText("Search");
        SearchStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchStudentBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SearchedStudentTf, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchStudentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SearchStudentBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(SearchedStudentTf))
                .addContainerGap())
        );

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SearchedStName.setText("Student Name: ---");
        jPanel7.add(SearchedStName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 330, 20));

        SearchedStSurname.setText("Student Surname: ---");
        jPanel7.add(SearchedStSurname, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 330, 20));

        SearchedStGender.setText("Gender: ---");
        jPanel7.add(SearchedStGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 330, 20));

        SearchedStBdate.setText("Birthdate: ---");
        jPanel7.add(SearchedStBdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 330, 20));

        SearchedStMob.setText("Mobile Number: ---");
        jPanel7.add(SearchedStMob, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 330, 20));

        SearchedStDep.setText("Department: ---");
        jPanel7.add(SearchedStDep, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 330, 20));

        SearchedStFac.setText("Faculty: ---");
        jPanel7.add(SearchedStFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 330, 20));

        SearchedStudentCoursesBtn.setText("Courses Of Student");
        SearchedStudentCoursesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchedStudentCoursesBtnActionPerformed(evt);
            }
        });
        jPanel7.add(SearchedStudentCoursesBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 160, 30));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Students", jPanel5);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TEACHER PANEL - SMS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ConfirmCRBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmCRBtnActionPerformed
        // TODO add your handling code here:
        ConfirmSCR();
    }//GEN-LAST:event_ConfirmCRBtnActionPerformed

    private void ApplySupervisorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplySupervisorBtnActionPerformed
        // TODO add your handling code here:
        Boolean success = DatabaseContext.ApplyToBeSupervisor(ActiveModels._activeUser.getUserId());
        if(success){
            JOptionPane.showMessageDialog(null, "Okay, wait for admin to confirm your supervisor status.");
            ApplySupervisorBtn.setEnabled(false);
            teacherSupLbl.setText("Supervisor: " + ActiveModels._activeUser.getSupervisorString());
            return;
        }else{
            JOptionPane.showMessageDialog(null, "It is failed with CODE 044");
            return;
        }
    }//GEN-LAST:event_ApplySupervisorBtnActionPerformed

    private void ExamDateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExamDateBtnActionPerformed
        // TODO add your handling code here:
        AddExamDate();
    }//GEN-LAST:event_ExamDateBtnActionPerformed

    private void SubmitAnnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitAnnBtnActionPerformed
        // TODO add your handling code here:
        AddAnouncement();
    }//GEN-LAST:event_SubmitAnnBtnActionPerformed

    private void gradingBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradingBTNActionPerformed
        // TODO add your handling code here:
        int selected_row = tMyCoursesTable.getSelectedRow();

        if(selected_row == -1) {
            JOptionPane.showMessageDialog(null, "You did not select a course.");
            return;
        }
        
        var id = tMyCoursesTable.getValueAt(selected_row, 0);
        int id_int = Integer.valueOf((String) id);
        
        ActiveModels.SelectedCourseIDForGrading = id_int;
        
        GradingDialog gf = new GradingDialog(Teacher.this, true);
        gf.setVisible(true);
    }//GEN-LAST:event_gradingBTNActionPerformed

    private void OpenCourseRegForSelectedStudent(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OpenCourseRegForSelectedStudent
        // TODO add your handling code here:
        int selected_row = SupervisedStudentsTable.getSelectedRow();

        if(selected_row == -1) {
            return;
        }
        
        var id = SupervisedStudentsTable.getValueAt(selected_row, 0);
        int id_int = Integer.valueOf((String) id);
        
        var myList = DatabaseContext.GetAllRegisteredCoursesByStudent(id_int);
        
        RefreshTable(SupervisedTakenCoursesTable);
        
        DefaultTableModel tblModel = (DefaultTableModel) SupervisedTakenCoursesTable.getModel();
        for(int i = 0; i<myList.size(); i++){
            if(myList.get(i).getSituation() != 0){
                continue;
            }
            String tbData[] = {
                    String.valueOf(myList.get(i).getID()),
                    myList.get(i).getCourseM().getCourseName(),
                    myList.get(i).getCourseM().getCourseCode(),
                    myList.get(i).getCourseM().getCourseTutor(),
                    String.valueOf(myList.get(i).getCourseM().getCourseCredit())
            };
            tblModel.addRow(tbData);
        }
    }//GEN-LAST:event_OpenCourseRegForSelectedStudent

    private void RejectCRBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RejectCRBtnActionPerformed
        // TODO add your handling code here:
        RejectSCR();
    }//GEN-LAST:event_RejectCRBtnActionPerformed

    private void SearchStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchStudentBtnActionPerformed
        // TODO add your handling code here:
        getSearchedStudentTableReady(SearchedStudentTf.getText());
    }//GEN-LAST:event_SearchStudentBtnActionPerformed

    private void SearchedStudentSelected(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchedStudentSelected
        // TODO add your handling code here:
        int selected_row = SearchedStudentTable.getSelectedRow();

        if(selected_row == -1) {
            return;
        }
        
        var id = SearchedStudentTable.getValueAt(selected_row, 0);
        int id_int = Integer.valueOf((String) id);
        UserModel um = DatabaseContext.GetStudentById(id_int);
        setSearchedStudentLabels(um);
    }//GEN-LAST:event_SearchedStudentSelected

    private void SearchedStudentCoursesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchedStudentCoursesBtnActionPerformed
        // TODO add your handling code here:
        int selected_row = SearchedStudentTable.getSelectedRow();

        if(selected_row == -1) {
            JOptionPane.showMessageDialog(null, "You did not select a student.");
            return;
        }
        
        var id = SearchedStudentTable.getValueAt(selected_row, 0);
        int id_int = Integer.valueOf((String) id);
        ActiveModels.SelectedStudentForSeeingCourses = id_int;
        
        SeeCoursesDialog scd = new SeeCoursesDialog(Teacher.this, true);
        scd.setVisible(true);
    }//GEN-LAST:event_SearchedStudentCoursesBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Teacher().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AnnouncementTf;
    private javax.swing.JButton ApplySupervisorBtn;
    private javax.swing.JButton ConfirmCRBtn;
    private javax.swing.JButton ExamDateBtn;
    private javax.swing.JButton RejectCRBtn;
    private javax.swing.JButton SearchStudentBtn;
    private javax.swing.JLabel SearchedStBdate;
    private javax.swing.JLabel SearchedStDep;
    private javax.swing.JLabel SearchedStFac;
    private javax.swing.JLabel SearchedStGender;
    private javax.swing.JLabel SearchedStMob;
    private javax.swing.JLabel SearchedStName;
    private javax.swing.JLabel SearchedStSurname;
    private javax.swing.JButton SearchedStudentCoursesBtn;
    private javax.swing.JTable SearchedStudentTable;
    private javax.swing.JTextField SearchedStudentTf;
    private javax.swing.JButton SubmitAnnBtn;
    private javax.swing.JTable SupervisedStudentsTable;
    private javax.swing.JTable SupervisedTakenCoursesTable;
    private javax.swing.JTextField examDateTf;
    private javax.swing.JButton gradingBTN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tMyCoursesTable;
    private javax.swing.JLabel teacherDepLbl;
    private javax.swing.JLabel teacherFacLbl;
    private javax.swing.JLabel teacherGenderLbl;
    private javax.swing.JLabel teacherMobNumberLbl;
    private javax.swing.JLabel teacherNameLbl;
    private javax.swing.JLabel teacherSupLbl;
    private javax.swing.JLabel teacherSurnameLbl;
    // End of variables declaration//GEN-END:variables
}
