/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package java_school_management_system;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bugra
 */
public class Student extends javax.swing.JFrame {

    /**
     * Creates new form Student
     */
    
    Dictionary CoursesToRegisterDictionary = new Hashtable();
    Dictionary CoursesToRegisterDictionary_Credits = new Hashtable();
    
    public Student() {
        initComponents();
        this.setResizable(false);
        sNameLbl.setText("Name: " + ActiveModels._activeUser.getName());
        sSurnameLbl.setText("Surname: " + ActiveModels._activeUser.getSurname());
        sGenderLbl.setText("Gender: " + ActiveModels._activeUser.getGender());
        sBirthdateLbl.setText("Birthdate: " + ActiveModels._activeUser.getDateOfBirth());
        sMobLbl.setText("Mobile Number: " + ActiveModels._activeUser.getMobileNumber());
        sFacLbl.setText("Faculty: " + ActiveModels._activeUser.getFaculty());
        sDepLbl.setText("Department: " + ActiveModels._activeUser.getDepartment());
        
        if(ActiveModels.CourseRegOpen){
            jTabbedPane1.setEnabledAt(2,true);
        }else{
            jTabbedPane1.setEnabledAt(2,false);
        }
        
        getMyCoursesTableReady();
        GetCoursesTableReady();
        getAnnouncementsTPReady();
    }
    
    public void RefreshTable(JTable table){
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            tblModel.removeRow(i);
        }
    }
    
    public void getAnnouncementsTPReady(){
        ArrayList<RegisteredCourseModel> courseList = DatabaseContext.GetAllRegisteredCoursesByStudent(ActiveModels._activeUser.getUserId());
        for(int i = 0; i<courseList.size(); i++){
            if(courseList.get(i).getCourseM().getCourseAnnouncement() != "-" && courseList.get(i).getCourseM().getCourseAnnouncement() != "" && courseList.get(i).getSituation() == 1){
                String announcement = courseList.get(i).getCourseM().getCourseCode() + " | " + courseList.get(i).getCourseM().getCourseTutor() + ": " + courseList.get(i).getCourseM().getCourseAnnouncement() + " \n";
                String alreadyIn = AnnouncementsTP.getText();
                AnnouncementsTP.setText(alreadyIn + announcement);
            }
        }
    }
    
    
    public void getMyCoursesTableReady(){
        RefreshTable(sMyCoursesTable);
        ArrayList<RegisteredCourseModel> courseList = DatabaseContext.GetAllRegisteredCoursesByStudent(ActiveModels._activeUser.getUserId());
        DefaultTableModel tblModel = (DefaultTableModel) sMyCoursesTable.getModel();
        for(int i = 0; i<courseList.size(); i++){
            if(courseList.get(i).getSituation() == 1){
                int gr = courseList.get(i).getGrade();
                String grStr = String.valueOf(gr);
                if(gr == -1){
                    grStr = "NaN";
                }


                String tbData[] = {courseList.get(i).getCourseM().getCourseName(),
                    courseList.get(i).getCourseM().getCourseCode(), 
                    courseList.get(i).getCourseM().getCourseTutor(),
                    grStr, 
                    courseList.get(i).getCourseM().getCourseExamDate(),
                    courseList.get(i).getCourseM().getCourseDay(),
                    String.valueOf(courseList.get(i).getCourseM().getCourseHour()),
                    String.valueOf(courseList.get(i).getCourseM().getCourseCredit())
                };
                tblModel.addRow(tbData);
            }
        }
    }
    
    public void GetCoursesTableReady(){
        RefreshTable(RegCoursesTable);
        ArrayList<CourseModel> courseList = DatabaseContext.GetAllCourses(ActiveModels._activeUser.getDepartment());
        DefaultTableModel tblModel = (DefaultTableModel) RegCoursesTable.getModel();
        for(int i = 0; i<courseList.size(); i++){
            String tbData[] = {String.valueOf(courseList.get(i).getCourseID()),courseList.get(i).getCourseName(), courseList.get(i).getCourseCode(), courseList.get(i).getCourseTutor(), courseList.get(i).getCourseDay(), String.valueOf(courseList.get(i).getCourseHour()), String.valueOf(courseList.get(i).getCourseCredit())};
            tblModel.addRow(tbData);
        }
    }
    
    public void addToCourseSelection(){
        int selected_row = RegCoursesTable.getSelectedRow();

        if(selected_row == -1) {
            JOptionPane.showMessageDialog(null, "You did not select a course.");
            return;
        }
        
        var id = RegCoursesTable.getValueAt(selected_row, 0);
        var credit = RegCoursesTable.getValueAt(selected_row, 6);
        int i = Integer.valueOf((String) id);
        
        if(CoursesToRegisterDictionary.get(i)!= null){
            JOptionPane.showMessageDialog(null, "You already selected this course.");
            return;
        }
        CoursesToRegisterDictionary.put(i,(String) credit);
    }
    
    public void removeFromCourseSelection(){
        int selected_row = RegCoursesTable.getSelectedRow();

        if(selected_row == -1) {
            JOptionPane.showMessageDialog(null, "You did not select a course.");
            return;
        }
        
        var id = RegCoursesTable.getValueAt(selected_row, 0);
        var credit = RegCoursesTable.getValueAt(selected_row, 6);
        int i = Integer.valueOf((String) id);
        
        if(CoursesToRegisterDictionary.get(i)== null){
            JOptionPane.showMessageDialog(null, "You did not select this course.");
            return;
        }
        CoursesToRegisterDictionary.remove(i);
    }
    
    public void registerToCourses(){
        Enumeration<Integer> e = CoursesToRegisterDictionary.keys();
        
        while (e.hasMoreElements()) {
 
            // Getting the key of a particular entry
            int key = e.nextElement();
 
            CourseModel cm = DatabaseContext.GetCourseById(key);
            UserModel sm = ActiveModels._activeUser;
            
            
            int rcm_id = -1;
            int CourseID = key;
            int StudentID = ActiveModels._activeUser.getUserId();
            int Situation = 0;
            int Grade = -1;
            int TutorID = cm.getCourseTutorID();
            
            UserModel tm = DatabaseContext.GetTeacherById(TutorID);
           
            RegisteredCourseModel rcm = new RegisteredCourseModel(rcm_id, CourseID, StudentID, Situation, Grade, TutorID, sm, cm, tm);
            
            DatabaseContext.RegisterToCourse(rcm);
        }
        
        getMyCoursesTableReady();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        sNameLbl = new javax.swing.JLabel();
        sSurnameLbl = new javax.swing.JLabel();
        sGenderLbl = new javax.swing.JLabel();
        sBirthdateLbl = new javax.swing.JLabel();
        sDepLbl = new javax.swing.JLabel();
        sFacLbl = new javax.swing.JLabel();
        sMobLbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        AnnouncementsTP = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sMyCoursesTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        RegCoursesTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("STUDENT - SMS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sNameLbl.setText("Name:");
        jPanel2.add(sNameLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 470, 30));

        sSurnameLbl.setText("Surname:");
        jPanel2.add(sSurnameLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 470, 30));

        sGenderLbl.setText("Gender:");
        jPanel2.add(sGenderLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 470, 30));

        sBirthdateLbl.setText("Birthdate:");
        jPanel2.add(sBirthdateLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 470, 30));

        sDepLbl.setText("Department:");
        jPanel2.add(sDepLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 470, 30));

        sFacLbl.setText("Faculty:");
        jPanel2.add(sFacLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, 470, 30));

        sMobLbl.setText("Mobile Number:");
        jPanel2.add(sMobLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, 470, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Announcements:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 610, -1));

        AnnouncementsTP.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        AnnouncementsTP.setEnabled(false);
        AnnouncementsTP.setFocusable(false);
        jScrollPane3.setViewportView(AnnouncementsTP);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, 610, 160));

        jTabbedPane1.addTab("My Information", jPanel2);

        sMyCoursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Course Name", "Course Code", "Course Teacher", "Grade", "Exam Date", "Course Day", "Course Hour", "Credit"
            }
        ));
        jScrollPane1.setViewportView(sMyCoursesTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("My Courses", jPanel3);

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Submit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Remove");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(817, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        RegCoursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Course Name", "Course Code", "Teacher", "Day ", "Hour", "Credit"
            }
        ));
        jScrollPane2.setViewportView(RegCoursesTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Register Courses", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        addToCourseSelection();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        removeFromCourseSelection();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(CoursesToRegisterDictionary.isEmpty()){
            JOptionPane.showMessageDialog(null, "You did not select a course.");
            return;
        }
        registerToCourses();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Student().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane AnnouncementsTP;
    private javax.swing.JTable RegCoursesTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel sBirthdateLbl;
    private javax.swing.JLabel sDepLbl;
    private javax.swing.JLabel sFacLbl;
    private javax.swing.JLabel sGenderLbl;
    private javax.swing.JLabel sMobLbl;
    private javax.swing.JTable sMyCoursesTable;
    private javax.swing.JLabel sNameLbl;
    private javax.swing.JLabel sSurnameLbl;
    // End of variables declaration//GEN-END:variables
}
