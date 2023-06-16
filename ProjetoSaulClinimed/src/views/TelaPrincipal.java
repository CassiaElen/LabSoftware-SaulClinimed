package views;

import java.text.DateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class TelaPrincipal extends javax.swing.JFrame {

    public TelaPrincipal() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        lblPerfil = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblData = new javax.swing.JLabel();
        desktop = new javax.swing.JDesktopPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblsobre = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        iconAES = new javax.swing.JLabel();
        iconU = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        iconAE = new javax.swing.JLabel();
        iconM = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnMedico = new javax.swing.JButton();
        btnPaciente = new javax.swing.JButton();
        btnUsuario = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnExame = new javax.swing.JButton();
        btnHistorico = new javax.swing.JButton();
        btnAdcExame = new javax.swing.JButton();
        btnEspecialidade = new javax.swing.JButton();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1178, 580));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(44, 84, 108));
        jPanel3.setLayout(null);

        jPanel1.setBackground(new java.awt.Color(67, 137, 186));
        jPanel1.setMaximumSize(new java.awt.Dimension(1160, 100));
        jPanel1.setLayout(null);

        lblNome.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNome.setForeground(new java.awt.Color(255, 255, 255));
        lblNome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNome.setText("Nome User");
        jPanel1.add(lblNome);
        lblNome.setBounds(570, 0, 300, 30);

        lblPerfil.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        lblPerfil.setForeground(new java.awt.Color(255, 255, 255));
        lblPerfil.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPerfil.setText("perfil");
        jPanel1.add(lblPerfil);
        lblPerfil.setBounds(770, 30, 100, 20);

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Sair");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(930, 20, 54, 23);

        lblData.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        lblData.setForeground(new java.awt.Color(255, 255, 255));
        lblData.setText("Data");
        jPanel1.add(lblData);
        lblData.setBounds(10, 10, 130, 16);

        jPanel3.add(jPanel1);
        jPanel1.setBounds(180, 0, 1020, 60);

        desktop.setMaximumSize(new java.awt.Dimension(980, 480));
        desktop.setMinimumSize(new java.awt.Dimension(980, 480));
        desktop.setRequestFocusEnabled(false);

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1020, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        jPanel3.add(desktop);
        desktop.setBounds(180, 60, 1020, 560);

        jPanel5.setBackground(new java.awt.Color(59, 120, 163));
        jPanel5.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Showcard Gothic", 2, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Saul Clinimed");
        jPanel5.add(jLabel3);
        jLabel3.setBounds(2, 10, 180, 48);

        jPanel3.add(jPanel5);
        jPanel5.setBounds(0, 0, 190, 60);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("GERENCIAMENTO");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 70, 100, 16);

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("AGENDAMENTO");
        jPanel3.add(jLabel11);
        jLabel11.setBounds(10, 220, 100, 16);

        lblsobre.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        lblsobre.setForeground(new java.awt.Color(255, 255, 255));
        lblsobre.setText("Sobre");
        jPanel3.add(lblsobre);
        lblsobre.setBounds(70, 540, 40, 30);

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("ADICIONAIS");
        jPanel3.add(jLabel17);
        jLabel17.setBounds(10, 330, 100, 16);
        jPanel3.add(jLabel18);
        jLabel18.setBounds(10, 90, 0, 30);
        jPanel3.add(jLabel19);
        jLabel19.setBounds(10, 420, 0, 30);
        jPanel3.add(jLabel20);
        jLabel20.setBounds(10, 170, 0, 30);
        jPanel3.add(jLabel21);
        jLabel21.setBounds(10, 220, 0, 30);
        jPanel3.add(jLabel22);
        jLabel22.setBounds(10, 460, 0, 30);
        jPanel3.add(jLabel23);
        jLabel23.setBounds(10, 360, 0, 30);
        jPanel3.add(jLabel24);
        jLabel24.setBounds(10, 320, 0, 30);
        jPanel3.add(jLabel25);
        jLabel25.setBounds(10, 130, 0, 30);
        jPanel3.add(jLabel26);
        jLabel26.setBounds(10, 260, 0, 30);

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/paciente.png"))); // NOI18N
        jPanel3.add(jLabel27);
        jLabel27.setBounds(10, 90, 30, 40);
        jPanel3.add(jLabel28);
        jLabel28.setBounds(10, 90, 30, 30);
        jPanel3.add(jLabel29);
        jLabel29.setBounds(-10, 90, 20, 30);

        iconAES.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/medico.png"))); // NOI18N
        jPanel3.add(iconAES);
        iconAES.setBounds(10, 430, 24, 30);

        iconU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/adicionar-usuario.png"))); // NOI18N
        jPanel3.add(iconU);
        iconU.setBounds(10, 170, 24, 40);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/consulta.png"))); // NOI18N
        jPanel3.add(jLabel4);
        jLabel4.setBounds(10, 250, 24, 24);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/teste-de-sangue.png"))); // NOI18N
        jPanel3.add(jLabel5);
        jLabel5.setBounds(10, 290, 24, 30);

        iconAE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/teste-de-sangue.png"))); // NOI18N
        jPanel3.add(iconAE);
        iconAE.setBounds(10, 390, 24, 30);

        iconM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/medico.png"))); // NOI18N
        jPanel3.add(iconM);
        iconM.setBounds(10, 130, 24, 30);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/historico-medico.png"))); // NOI18N
        jPanel3.add(jLabel9);
        jLabel9.setBounds(10, 350, 24, 30);

        btnMedico.setBackground(new java.awt.Color(67, 137, 186));
        btnMedico.setForeground(new java.awt.Color(255, 255, 255));
        btnMedico.setText("Médico");
        btnMedico.setEnabled(false);
        btnMedico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMedicoMouseClicked(evt);
            }
        });
        jPanel3.add(btnMedico);
        btnMedico.setBounds(50, 140, 110, 23);

        btnPaciente.setBackground(new java.awt.Color(67, 137, 186));
        btnPaciente.setForeground(new java.awt.Color(255, 255, 255));
        btnPaciente.setText("Paciente");
        btnPaciente.setEnabled(false);
        btnPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPacienteMouseClicked(evt);
            }
        });
        jPanel3.add(btnPaciente);
        btnPaciente.setBounds(50, 100, 110, 23);

        btnUsuario.setBackground(new java.awt.Color(67, 137, 186));
        btnUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnUsuario.setText("Usuário");
        btnUsuario.setEnabled(false);
        btnUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUsuarioMouseClicked(evt);
            }
        });
        jPanel3.add(btnUsuario);
        btnUsuario.setBounds(50, 180, 110, 23);

        btnConsulta.setBackground(new java.awt.Color(67, 137, 186));
        btnConsulta.setForeground(new java.awt.Color(255, 255, 255));
        btnConsulta.setText("Consulta");
        btnConsulta.setEnabled(false);
        btnConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConsultaMouseClicked(evt);
            }
        });
        jPanel3.add(btnConsulta);
        btnConsulta.setBounds(50, 250, 110, 23);

        btnExame.setBackground(new java.awt.Color(67, 137, 186));
        btnExame.setForeground(new java.awt.Color(255, 255, 255));
        btnExame.setText("Exame");
        btnExame.setEnabled(false);
        btnExame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExameMouseClicked(evt);
            }
        });
        jPanel3.add(btnExame);
        btnExame.setBounds(50, 290, 110, 23);

        btnHistorico.setBackground(new java.awt.Color(67, 137, 186));
        btnHistorico.setForeground(new java.awt.Color(255, 255, 255));
        btnHistorico.setText("Histórico");
        btnHistorico.setEnabled(false);
        btnHistorico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHistoricoMouseClicked(evt);
            }
        });
        jPanel3.add(btnHistorico);
        btnHistorico.setBounds(50, 350, 110, 23);

        btnAdcExame.setBackground(new java.awt.Color(67, 137, 186));
        btnAdcExame.setForeground(new java.awt.Color(255, 255, 255));
        btnAdcExame.setText("Exame");
        btnAdcExame.setEnabled(false);
        btnAdcExame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAdcExameMouseClicked(evt);
            }
        });
        btnAdcExame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdcExameActionPerformed(evt);
            }
        });
        jPanel3.add(btnAdcExame);
        btnAdcExame.setBounds(50, 390, 110, 23);

        btnEspecialidade.setBackground(new java.awt.Color(67, 137, 186));
        btnEspecialidade.setForeground(new java.awt.Color(255, 255, 255));
        btnEspecialidade.setText("Especialidade");
        btnEspecialidade.setEnabled(false);
        btnEspecialidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEspecialidadeActionPerformed(evt);
            }
        });
        jPanel3.add(btnEspecialidade);
        btnEspecialidade.setBounds(50, 440, 110, 23);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1199, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        lblData.setText(formatador.format(data));
    }//GEN-LAST:event_formWindowActivated

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que quer sair?");
         if (sair == JOptionPane.YES_NO_OPTION){
             System.exit(0);
         }
         
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPacienteMouseClicked
        TelaPaciente paciente = new TelaPaciente();
        paciente.setVisible(true);
        desktop.add(paciente);
    }//GEN-LAST:event_btnPacienteMouseClicked

    private void btnMedicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMedicoMouseClicked
        TelaMedico medico = new TelaMedico();
        medico.setVisible(true);
        desktop.add(medico);
    }//GEN-LAST:event_btnMedicoMouseClicked

    private void btnUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUsuarioMouseClicked
        TelaUsuario usuario = new TelaUsuario();
        usuario.setVisible(true);
        desktop.add(usuario);
    }//GEN-LAST:event_btnUsuarioMouseClicked

    private void btnConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConsultaMouseClicked
        TelaConsulta consulta = new TelaConsulta();
        consulta.setVisible(true);
        desktop.add(consulta);
    }//GEN-LAST:event_btnConsultaMouseClicked

    private void btnExameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExameMouseClicked
        TelaExame exame = new TelaExame();
        exame.setVisible(true);
        desktop.add(exame);
    }//GEN-LAST:event_btnExameMouseClicked

    private void btnHistoricoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHistoricoMouseClicked
        TelaHistorico historico = new TelaHistorico();
        historico.setVisible(true);
        desktop.add(historico);
    }//GEN-LAST:event_btnHistoricoMouseClicked

    private void btnAdcExameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdcExameMouseClicked
      
    }//GEN-LAST:event_btnAdcExameMouseClicked

    private void btnEspecialidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEspecialidadeActionPerformed
      TelaAdcEspecialidade especialidade = new TelaAdcEspecialidade();
      especialidade.setVisible(true);
      desktop.add(especialidade);
    }//GEN-LAST:event_btnEspecialidadeActionPerformed

    private void btnAdcExameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdcExameActionPerformed
        TelaAdcExame exame = new TelaAdcExame();
      exame.setVisible(true);
      desktop.add(exame);
    }//GEN-LAST:event_btnAdcExameActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAdcExame;
    public static javax.swing.JButton btnConsulta;
    public static javax.swing.JButton btnEspecialidade;
    public static javax.swing.JButton btnExame;
    public static javax.swing.JButton btnHistorico;
    public static javax.swing.JButton btnMedico;
    public static javax.swing.JButton btnPaciente;
    public static javax.swing.JButton btnUsuario;
    private javax.swing.JDesktopPane desktop;
    public static javax.swing.JLabel iconAE;
    public static javax.swing.JLabel iconAES;
    public static javax.swing.JLabel iconM;
    public static javax.swing.JLabel iconU;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblData;
    public static javax.swing.JLabel lblNome;
    public static javax.swing.JLabel lblPerfil;
    public static javax.swing.JLabel lblsobre;
    // End of variables declaration//GEN-END:variables
}
