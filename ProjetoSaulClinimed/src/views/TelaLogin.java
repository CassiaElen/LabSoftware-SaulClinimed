package views;

import java.sql.*;
import dal.ModuloConexao;
import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

public class TelaLogin extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void logar() {
        String sql = "select * from TbUsuario where login=? and senha=? ";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuario.getText());
            String captura = new String(txtSenha.getPassword());
            pst.setString(2, captura);
            rs = pst.executeQuery();
            if (rs.next()) {
                String perfil = rs.getString(7);
                if (perfil.equals("Admin")) {

                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    TelaPrincipal.btnPaciente.setEnabled(true);
                    TelaPrincipal.btnMedico.setEnabled(true);
                    TelaPrincipal.btnUsuario.setEnabled(true);
                    TelaPrincipal.btnConsulta.setEnabled(true);
                    TelaPrincipal.btnExame.setEnabled(true);
                    TelaPrincipal.lblsobre.setEnabled(true);
                    TelaPrincipal.btnHistorico.setEnabled(true);
                    TelaPrincipal.btnAdcExame.setEnabled(true);
                    TelaPrincipal.btnEspecialidade.setEnabled(true);
                    TelaPrincipal.lblNome.setText(rs.getString(2));
                    TelaPrincipal.lblNome.setForeground(Color.red);
                    TelaPrincipal.lblPerfil.setText(perfil);
                    this.dispose();
                } else {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    TelaPrincipal.btnPaciente.setEnabled(true);
                    TelaPrincipal.btnMedico.setEnabled(false);
                    TelaPrincipal.btnMedico.setForeground(Color.gray);
                    TelaPrincipal.btnUsuario.setEnabled(false);
                    TelaPrincipal.btnUsuario.setForeground(Color.gray);
                    TelaPrincipal.btnConsulta.setEnabled(true);
                    TelaPrincipal.btnExame.setEnabled(true);
                    TelaPrincipal.lblsobre.setEnabled(true);
                    TelaPrincipal.btnHistorico.setEnabled(true);
                    TelaPrincipal.btnAdcExame.setEnabled(false);
                    TelaPrincipal.btnAdcExame.setForeground(Color.gray);
                    TelaPrincipal.btnEspecialidade.setEnabled(false);
                    TelaPrincipal.btnEspecialidade.setForeground(Color.gray);
                    TelaPrincipal.lblNome.setText(rs.getString(2));
                    TelaPrincipal.lblNome.setForeground(Color.yellow);
                    TelaPrincipal.lblPerfil.setText(perfil);
                    this.dispose();
                }
                conexao.close();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha inválido");
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public TelaLogin() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        iblStatus = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(788, 391));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(9, 9, 115));
        jPanel1.setLayout(null);
        jPanel1.add(iblStatus);
        iblStatus.setBounds(37, 424, 0, 0);

        jPanel2.setBackground(new java.awt.Color(237, 241, 244));
        jPanel2.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Login");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(30, 30, 100, 25);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel1.setText("Usuário");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(30, 70, 60, 20);
        jPanel2.add(txtUsuario);
        txtUsuario.setBounds(30, 100, 250, 30);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel2.setText("Senha");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(30, 140, 60, 24);
        jPanel2.add(txtSenha);
        txtSenha.setBounds(30, 170, 250, 30);

        btnLogin.setBackground(new java.awt.Color(67, 137, 186));
        btnLogin.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel2.add(btnLogin);
        btnLogin.setBounds(30, 240, 250, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(500, 50, 310, 320);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/saul clinimed logo (2).png"))); // NOI18N
        jPanel1.add(jLabel4);
        jLabel4.setBounds(50, 50, 370, 320);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 870, 440);

        setSize(new java.awt.Dimension(877, 447));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        logar();
    }//GEN-LAST:event_btnLoginActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel iblStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
