package views;

import java.sql.*;
import dal.ModuloConexao;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class TelaHistorico extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaHistorico() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void consulta() {
        String filtro = cboPesquisarSuj.getSelectedItem().toString();
        try {
            if (filtro.equals("Médico")) {
                String sql = "select idMedico as Id, nome as Nome from TbMedico where nome like ?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtHistDigiNome.getText() + "%");
                    rs = pst.executeQuery();
                    tblPacMed.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                String sql = "select `idPaciente` as `Id`, `nome` as  `Nome` from `TbPaciente` where nome like ?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtHistDigiNome.getText() + "%");
                    rs = pst.executeQuery();
                    tblPacMed.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        } catch (HeadlessException e) {
        }

    }

    public void setar_campos() {
        int setar = tblPacMed.getSelectedRow();
        txtHistId.setText(tblPacMed.getModel().getValueAt(setar, 0).toString());
        txtHistNome.setText(tblPacMed.getModel().getValueAt(setar, 1).toString());

        // Chamar o método consultarServico() após definir os valores dos campos
        consultarServico();
    }

    private void consultarServico() {
        String suj = cboPesquisarSuj.getSelectedItem().toString();
        String ser = cboPesquisarSer.getSelectedItem().toString();
        try {
            if ((suj.equals("Médico")) || (ser.equals("Consulta"))) {
                String sqlMedCon = "SELECT `idConsulta` AS `Id`, `dataConsulta` AS `Data`, `horario` AS `Horas`, `especialidadeCon` AS `Especialidade`, `nomePaciente` AS `Paciente`, `nomeMedico` AS `Medico`, `precoEspecialidade` AS `Valor` FROM `TbAgenConsulta` where nomeMedico=? and medicoId=?";
                try {
                    pst = conexao.prepareStatement(sqlMedCon);
                    pst.setString(1, txtHistNome.getText());
                    pst.setString(1, txtHistId.getText());
                    rs = pst.executeQuery();
                    tblServicos.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else if ((suj.equals("Médico")) || (ser.equals("Exame"))) {
                String sqlMedExa = "SELECT `idExame` AS `Id`, `dataExame` AS `Data do Exame`, `horario` AS `Horas`, `tipoExame` AS `Exame`, `nomePaciente` AS `Paciente`,  `nomeMedico` AS `Medico`, `precoExame` AS `Valor` FROM `TbAgenExame` where nomeMedico like ?";
                try {
                    pst = conexao.prepareStatement(sqlMedExa);
                    pst.setString(1, txtHistNome.getText() + "%");
                    rs = pst.executeQuery();
                    tblServicos.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else if ((suj.equals("Paciente")) || (ser.equals("Consulta"))) {
                String sqlPacCon = "SELECT `idConsulta` AS `Id`, `dataConsulta` AS `Data`, `horario` AS `Horas`, `especialidadeCon` AS `Especialidade`, `nomePaciente` AS `Paciente`,`nomeMedico` AS `Medico`, `precoEspecialidade` AS `Valor` FROM `TbAgenConsulta` where nomePaciente like ?";
                try {
                    pst = conexao.prepareStatement(sqlPacCon);
                    pst.setString(1, txtHistNome.getText() + "%");
                    rs = pst.executeQuery();
                    tblServicos.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else if ((suj.equals("Paciente")) || (ser.equals("Exame"))) {
                String sqlPacExa = "SELECT `idExame` AS `Id`, `dataExame` AS `Data`, `horario` AS `Horas`, `tipoExame` AS `Exame`,`nomePaciente` AS `Paciente`,`nomeMedico` AS `Medico`, `precoExame` AS `Valor` FROM `TbAgenExame` where nomePaciente like ?";
                try {
                    pst = conexao.prepareStatement(sqlPacExa);
                    pst.setString(1, txtHistNome.getText() + "%");
                    rs = pst.executeQuery();
                    tblServicos.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        } catch (HeadlessException e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtHistDigiNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cboPesquisarSuj = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPacMed = new javax.swing.JTable();
        txtHistId = new javax.swing.JTextField();
        txtHistNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cboPesquisarSer = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblServicos = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Consultar Histórico");
        setMaximumSize(new java.awt.Dimension(1020, 560));
        setMinimumSize(new java.awt.Dimension(1020, 560));
        setPreferredSize(new java.awt.Dimension(1020, 560));

        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(211, 215, 221));
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel1.setText("Nome");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(20, 160, 40, 20);

        txtHistDigiNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHistDigiNomeKeyReleased(evt);
            }
        });
        jPanel2.add(txtHistDigiNome);
        txtHistDigiNome.setBounds(20, 180, 280, 30);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel2.setText("Serviço");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 90, 50, 16);

        cboPesquisarSuj.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar", "Médico", "Paciente" }));
        cboPesquisarSuj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPesquisarSujActionPerformed(evt);
            }
        });
        jPanel2.add(cboPesquisarSuj);
        cboPesquisarSuj.setBounds(20, 50, 130, 30);

        tblPacMed = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblPacMed.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id", "Nome"
            }
        ));
        tblPacMed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPacMedMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPacMed);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(20, 230, 280, 170);

        txtHistId.setEnabled(false);
        txtHistId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHistIdActionPerformed(evt);
            }
        });
        jPanel2.add(txtHistId);
        txtHistId.setBounds(20, 430, 40, 30);

        txtHistNome.setEnabled(false);
        jPanel2.add(txtHistNome);
        txtHistNome.setBounds(70, 430, 220, 30);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("Id");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(20, 410, 20, 16);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel4.setText("Nome");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(70, 410, 33, 16);

        cboPesquisarSer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar ", "Consulta", "Exame" }));
        jPanel2.add(cboPesquisarSer);
        cboPesquisarSer.setBounds(20, 110, 130, 30);

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel6.setText("Pesquisar por");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(20, 30, 90, 16);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 20, 320, 480);

        jPanel3.setBackground(new java.awt.Color(211, 215, 221));
        jPanel3.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("Histórico");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(30, 20, 70, 16);

        tblServicos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Data", "Hora", "Especialidade", "Paciente ", "Médico", "Valor"
            }
        ));
        tblServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblServicosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblServicos);

        jPanel3.add(jScrollPane2);
        jScrollPane2.setBounds(20, 50, 590, 410);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(360, 20, 630, 480);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboPesquisarSujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPesquisarSujActionPerformed

    }//GEN-LAST:event_cboPesquisarSujActionPerformed

    private void txtHistIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHistIdActionPerformed

    }//GEN-LAST:event_txtHistIdActionPerformed

    private void tblServicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblServicosMouseClicked

    }//GEN-LAST:event_tblServicosMouseClicked

    private void txtHistDigiNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHistDigiNomeKeyReleased
        consulta();
    }//GEN-LAST:event_txtHistDigiNomeKeyReleased

    private void tblPacMedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacMedMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblPacMedMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboPesquisarSer;
    private javax.swing.JComboBox<String> cboPesquisarSuj;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblPacMed;
    private javax.swing.JTable tblServicos;
    private javax.swing.JTextField txtHistDigiNome;
    private javax.swing.JTextField txtHistId;
    private javax.swing.JTextField txtHistNome;
    // End of variables declaration//GEN-END:variables
}
