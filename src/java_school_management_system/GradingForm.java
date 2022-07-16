/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package java_school_management_system;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bugra
 */
public class GradingForm extends javax.swing.JFrame {

    /**
     * Creates new form GradingForm
     */
    public GradingForm() {
        initComponents();
        getGradingTableReady();
    }
    
    
    public void RefreshTable(JTable table){
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            tblModel.removeRow(i);
        }
    }
    
    public void getGradingTableReady(){
        RefreshTable(GradingTableForm);
        ArrayList<RegisteredCourseModel> myList = DatabaseContext.GetRegisteredCourseById(ActiveModels.SelectedCourseIDForGrading);
        DefaultTableModel tblModel = (DefaultTableModel) GradingTableForm.getModel();
        GradingCourseName.setText("Course Name: "+DatabaseContext.GetCourseById(ActiveModels.SelectedCourseIDForGrading).getCourseName());
        for(int i = 0; i<myList.size(); i++){
            String tbData[] = {
                    String.valueOf(myList.get(i).getID()),
                    myList.get(i).getStudentM().getName(),
                    myList.get(i).getStudentM().getSurname(), 
                    String.valueOf(myList.get(i).getGrade())
            };
            tblModel.addRow(tbData);
        }
    }
    
    public void GradeStudent(){
        int selected_row = GradingTableForm.getSelectedRow();

        if(selected_row == -1) {
            JOptionPane.showMessageDialog(null, "You did not select a course.");
            return;
        }
        
        var id = GradingTableForm.getValueAt(selected_row, 0);
        int id_int = Integer.valueOf((String) id);
        String grade = GradeStudentTF.getText();
        int grade_int = 0;
        
        if(grade == ""){
            JOptionPane.showMessageDialog(null, "You did not enter an announcement.");
            return;
        }
        
        try{
            grade_int = Integer.parseInt(grade);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        if(grade_int<0 || grade_int>100){
            JOptionPane.showMessageDialog(null, "Please enter a valid grade.");
            return;
        }
        
        DatabaseContext.GradeStudentById(id_int, grade_int);
        getGradingTableReady();
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
        GradeStudentTF = new javax.swing.JTextField();
        GradeStudentBtn = new javax.swing.JButton();
        GradingCourseName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        GradingTableForm = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        GradeStudentTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GradeStudentTFActionPerformed(evt);
            }
        });
        jPanel1.add(GradeStudentTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, 30));

        GradeStudentBtn.setText("Grade");
        GradeStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GradeStudentBtnActionPerformed(evt);
            }
        });
        jPanel1.add(GradeStudentBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 95, 30));

        GradingCourseName.setText("Course Name:");
        jPanel1.add(GradingCourseName, new org.netbeans.lib.awtextra.AbsoluteConstraints(348, -1, 260, 50));

        GradingTableForm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Student Name", "Student Surname", "Grade"
            }
        ));
        jScrollPane1.setViewportView(GradingTableForm);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GradeStudentTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GradeStudentTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GradeStudentTFActionPerformed

    private void GradeStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GradeStudentBtnActionPerformed
        // TODO add your handling code here:
        GradeStudent();
    }//GEN-LAST:event_GradeStudentBtnActionPerformed

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
            java.util.logging.Logger.getLogger(GradingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GradingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GradingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GradingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GradingForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton GradeStudentBtn;
    private javax.swing.JTextField GradeStudentTF;
    private javax.swing.JLabel GradingCourseName;
    private javax.swing.JTable GradingTableForm;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
