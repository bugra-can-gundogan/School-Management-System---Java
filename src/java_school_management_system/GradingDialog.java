/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
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
public class GradingDialog extends javax.swing.JDialog {

    /**
     * Creates new form GradingDialog
     */
    public GradingDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setResizable(false);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        GradingTableForm = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        GradeStudentTF = new javax.swing.JTextField();
        GradeStudentBtn = new javax.swing.JButton();
        GradingCourseName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 803, -1));

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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 454, 803, -1));

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
            java.util.logging.Logger.getLogger(GradingDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GradingDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GradingDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GradingDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GradingDialog dialog = new GradingDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
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
