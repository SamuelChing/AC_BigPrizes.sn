/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend;

import Controlador.ControladorDB;
import javax.swing.JOptionPane;

/**
 *
 * @author Nahum
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        PanelLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        user_input = new javax.swing.JTextField();
        btn_iniciar = new javax.swing.JButton();
        password_input = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        Label_Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 71)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("BigPrizes.sn");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 430, 100));

        PanelLogin.setBackground(new java.awt.Color(255, 255, 255));
        PanelLogin.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PanelLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(26, 128, 224));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 46)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 51));
        jLabel1.setText("Inicio de sesión");
        PanelLogin.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 30, 340, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Contraseña");
        PanelLogin.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("ShaumEnterprise ® 2019");
        PanelLogin.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, 330, -1));

        user_input.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        user_input.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        PanelLogin.add(user_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 330, 30));

        btn_iniciar.setBackground(new java.awt.Color(255, 153, 0));
        btn_iniciar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_iniciar.setForeground(new java.awt.Color(255, 255, 255));
        btn_iniciar.setText("Ingresar");
        btn_iniciar.setBorder(null);
        btn_iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iniciarActionPerformed(evt);
            }
        });
        PanelLogin.add(btn_iniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 130, 30));

        password_input.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        password_input.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        PanelLogin.add(password_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 330, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Usuario");
        PanelLogin.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        getContentPane().add(PanelLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 100, 430, 480));

        Label_Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/lottery.png"))); // NOI18N
        Label_Fondo.setText("LB_ImagenFondo");
        getContentPane().add(Label_Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iniciarActionPerformed
        // TODO add your handling code here:
        if(user_input.getText().trim().isBlank() || password_input.getText().trim().isBlank()){
            JOptionPane.showMessageDialog(null, "Espacios vacíos", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            ControladorDB.getControlador().InicioSesion(user_input.getText(), password_input.getText().trim(),this);
        }
    }//GEN-LAST:event_btn_iniciarActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Label_Fondo;
    private javax.swing.JPanel PanelLogin;
    private javax.swing.JButton btn_iniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField password_input;
    private javax.swing.JTextField user_input;
    // End of variables declaration//GEN-END:variables
}
