package views;

import java.sql.*;
import dal.ModuloConexao;
import java.awt.HeadlessException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class TelaMedico extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaMedico() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private boolean valorJaExiste(String sql, String valor) throws SQLException {
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setString(1, valor);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }
     
    private void adicionar() {
        
        String dataAdmissao = txtMedDA.getText();
        java.util.Date data = new java.util.Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        String dataDesktop = formatador.format(data);
        

        if (dataAdmissao.equals(dataDesktop)) {
            String sqlConsultarCrm = "SELECT crm FROM TbMedico WHERE crm = ?";
            String sqlConsultarTelefone = "SELECT telefone FROM TbMedico WHERE telefone = ?";
            String sqlConsultarEmail = "SELECT email FROM TbMedico WHERE email = ?";

            try {
                if (valorJaExiste(sqlConsultarCrm, txtMedCrm.getText())) {
                    JOptionPane.showMessageDialog(null, "CRM já cadastrado!");
                    limpar();
                    return;
                }

                if (valorJaExiste(sqlConsultarTelefone, txtMedTel.getText())) {
                    JOptionPane.showMessageDialog(null, "Telefone já cadastrado!");
                    limpar();
                    return;
                }

                if (valorJaExiste(sqlConsultarEmail, txtMedEma.getText())) {
                    JOptionPane.showMessageDialog(null, "Email já cadastrado!");
                    limpar();
                    return;
                }
            } catch (SQLException e) {
            }
            String sql = "insert into `TbMedico` (`nome`, `telefone`, `crm`, `especialidadeMed`, `email`, `dataAdmissao`) values (?,?,?,?,?,?)";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtMedNom.getText());
                pst.setString(2, txtMedTel.getText());
                pst.setString(3, txtMedCrm.getText());
                pst.setString(4, txtMedEsp.getText());
                pst.setString(5, txtMedEma.getText());
                String dia = txtMedDA.getText().substring(0, 2);
                String mes = txtMedDA.getText().substring(3, 5);
                String ano = txtMedDA.getText().substring(6);
                String dataMylql = ano + "-" + mes + "-" + dia;
                pst.setString(6, dataMylql);
                if ((txtMedNom.getText().isEmpty()) || (txtMedCrm.getText().isEmpty()) || (txtMedEsp.getText().isEmpty()) || (txtMedTel.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
                } else {
                    int adicionado = pst.executeUpdate();
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "Médico(a) adicionado com sucesso!");
                        limpar();
                    }
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data Inválida!");
            limpar();
        }
       
    }

    private void pesquisar_medico() {
        String sql = "select idMedico as Id, nome as Nome, telefone as Telefone, crm as CRM, especialidadeMed as Especialidade, email as Email, dataAdmissao as `Data de Admissão` from TbMedico where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtMedPesquisar.getText() + "%");
            rs = pst.executeQuery();

            tbtMedico.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campos() {
        int setar = tbtMedico.getSelectedRow();
        txtMedId.setText(tbtMedico.getModel().getValueAt(setar, 0).toString());
        txtMedNom.setText(tbtMedico.getModel().getValueAt(setar, 1).toString());
        txtMedTel.setText(tbtMedico.getModel().getValueAt(setar, 2).toString());
        txtMedCrm.setText(tbtMedico.getModel().getValueAt(setar, 3).toString());
        txtMedEsp.setText(tbtMedico.getModel().getValueAt(setar, 4).toString());
        txtMedEma.setText(tbtMedico.getModel().getValueAt(setar, 5).toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtMedDA.setText(sdf.format(tbtMedico.getModel().getValueAt(setar, 6)));

        btnAdicionar.setEnabled(false);

    }

    private void alterar() {
        String sql = "update TbMedico set nome=?, telefone=?, crm=?,especialidadeMed=?, email=?, dataAdmissao=? where IdMedico=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtMedNom.getText());
            pst.setString(2, txtMedTel.getText());
            pst.setString(3, txtMedCrm.getText());
            pst.setString(4, txtMedEsp.getText());
            pst.setString(5, txtMedEma.getText());
            String dia = txtMedDA.getText().substring(0, 2);
            String mes = txtMedDA.getText().substring(3, 5);
            String ano = txtMedDA.getText().substring(6);
            String dataMylql = ano + "-" + mes + "-" + dia;
            pst.setString(6, dataMylql);
            pst.setString(7, txtMedId.getText());
            if ((txtMedNom.getText().isEmpty()) || (txtMedCrm.getText().isEmpty()) || (txtMedEsp.getText().isEmpty()) || (txtMedTel.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do médico(a) alterados com sucesso!");
                    limpar();
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void remover() {
        int confima = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este médico(a)?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confima == JOptionPane.YES_OPTION) {
            String sql = "delete from TbMedico where idMedico=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtMedId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Médico(a) removido com sucesso!");
                    limpar();
                    btnAdicionar.setEnabled(true);
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void limpar() {
        txtMedPesquisar.setText(null);
        txtMedId.setText(null);
        txtMedNom.setText(null);
        txtMedCrm.setText(null);
        txtMedEsp.setText(null);
        txtMedTel.setText(null);
        txtMedEma.setText(null);
        txtMedDA.setText(null);
        ((DefaultTableModel) tbtMedico.getModel()).setRowCount(0);
    }

      private void consultar_especialidade() {
        String sql = "select `especialidade` as `Especialidade` from `TbEspecialidade` where `especialidade` like ?";
        // consultar especialidade que esteja disponivel
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtExaPesExa.getText() + "%");
            rs = pst.executeQuery();
            tblConPesEsp.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campoEsp() {
        int setar = tblConPesEsp.getSelectedRow();
        txtMedEsp.setText(tblConPesEsp.getModel().getValueAt(setar, 0).toString());
       
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtMedPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbtMedico = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMedId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMedNom = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMedEma = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMedTel = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMedEsp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMedCrm = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        txtMedDA = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        txtExaPesExa = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblConPesEsp = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gerenciamento de Dados do Médico");
        setMaximumSize(new java.awt.Dimension(1000, 520));
        setMinimumSize(new java.awt.Dimension(1000, 520));
        setPreferredSize(new java.awt.Dimension(1000, 520));

        jPanel1.setBackground(new java.awt.Color(234, 236, 239));
        jPanel1.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel11.setText("Pesquisar:");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(10, 260, 60, 20);

        txtMedPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMedPesquisarKeyReleased(evt);
            }
        });
        jPanel1.add(txtMedPesquisar);
        txtMedPesquisar.setBounds(70, 260, 500, 30);

        tbtMedico = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbtMedico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Telefone", "CRM", "Especialidade", "Email", "Data de Admissão"
            }
        ));
        tbtMedico.setFocusable(false);
        tbtMedico.getTableHeader().setResizingAllowed(false);
        tbtMedico.getTableHeader().setReorderingAllowed(false);
        tbtMedico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbtMedicoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbtMedico);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 300, 970, 170);

        btnAdicionar.setBackground(new java.awt.Color(0, 153, 0));
        btnAdicionar.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        btnAdicionar.setForeground(new java.awt.Color(255, 255, 255));
        btnAdicionar.setText("Cadastrar");
        btnAdicionar.setToolTipText("Adicionar");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdicionar);
        btnAdicionar.setBounds(830, 20, 100, 40);

        btnAlterar.setBackground(new java.awt.Color(67, 137, 186));
        btnAlterar.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        btnAlterar.setForeground(new java.awt.Color(255, 255, 255));
        btnAlterar.setText("Alterar");
        btnAlterar.setToolTipText("Editar");
        btnAlterar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAlterar);
        btnAlterar.setBounds(830, 100, 100, 40);

        btnRemover.setBackground(new java.awt.Color(255, 51, 51));
        btnRemover.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        btnRemover.setForeground(new java.awt.Color(255, 255, 255));
        btnRemover.setText("Deletar");
        btnRemover.setToolTipText("Excluir");
        btnRemover.setPreferredSize(new java.awt.Dimension(80, 80));
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });
        jPanel1.add(btnRemover);
        btnRemover.setBounds(830, 180, 100, 40);

        jPanel2.setBackground(new java.awt.Color(211, 215, 221));
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel1.setText("Id Médico");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(10, 10, 70, 19);

        txtMedId.setEnabled(false);
        jPanel2.add(txtMedId);
        txtMedId.setBounds(10, 30, 40, 30);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel2.setText("*Nome");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(90, 10, 38, 16);

        txtMedNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMedNomActionPerformed(evt);
            }
        });
        jPanel2.add(txtMedNom);
        txtMedNom.setBounds(90, 30, 290, 30);

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel6.setText("Email");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(10, 140, 29, 16);
        jPanel2.add(txtMedEma);
        txtMedEma.setBounds(10, 160, 290, 30);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel4.setText("*Telefone");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(10, 70, 51, 16);

        try {
            txtMedTel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel2.add(txtMedTel);
        txtMedTel.setBounds(10, 90, 140, 30);

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("*Especialidade");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(320, 140, 76, 16);

        txtMedEsp.setEnabled(false);
        jPanel2.add(txtMedEsp);
        txtMedEsp.setBounds(320, 160, 180, 30);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("*CRM");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(190, 70, 30, 16);

        try {
            txtMedCrm.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel2.add(txtMedCrm);
        txtMedCrm.setBounds(190, 90, 110, 30);

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel10.setText("*Data de Admissão");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(320, 70, 101, 16);

        try {
            txtMedDA.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel2.add(txtMedDA);
        txtMedDA.setBounds(320, 90, 130, 30);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 20, 520, 200);

        jPanel3.setBackground(new java.awt.Color(211, 215, 221));
        jPanel3.setLayout(null);

        txtExaPesExa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtExaPesExaKeyPressed(evt);
            }
        });
        jPanel3.add(txtExaPesExa);
        txtExaPesExa.setBounds(10, 30, 170, 30);
        jPanel3.add(jLabel18);
        jLabel18.setBounds(179, 43, 33, 0);

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel19.setText(" Especialidade");
        jPanel3.add(jLabel19);
        jLabel19.setBounds(10, 10, 74, 16);

        tblConPesEsp = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblConPesEsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Especialidade", "disponibilidade"
            }
        ));
        tblConPesEsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConPesEspMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblConPesEsp);

        jPanel3.add(jScrollPane3);
        jScrollPane3.setBounds(10, 70, 230, 110);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(560, 20, 250, 200);

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

    private void txtMedNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMedNomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMedNomActionPerformed

    private void tbtMedicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbtMedicoMouseClicked
        setar_campos();
    }//GEN-LAST:event_tbtMedicoMouseClicked

    private void txtMedPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMedPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisar_medico();
    }//GEN-LAST:event_txtMedPesquisarKeyReleased

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // chamando o método pra adicionar pacientes
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // chamando método alterar
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // chamando o método remover
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void txtExaPesExaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExaPesExaKeyPressed
        consultar_especialidade();
    }//GEN-LAST:event_txtExaPesExaKeyPressed

    private void tblConPesEspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConPesEspMouseClicked
        setar_campoEsp();
    }//GEN-LAST:event_tblConPesEspMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblConPesEsp;
    private javax.swing.JTable tbtMedico;
    private javax.swing.JTextField txtExaPesExa;
    private javax.swing.JFormattedTextField txtMedCrm;
    private javax.swing.JFormattedTextField txtMedDA;
    private javax.swing.JTextField txtMedEma;
    private javax.swing.JTextField txtMedEsp;
    private javax.swing.JTextField txtMedId;
    private javax.swing.JTextField txtMedNom;
    private javax.swing.JTextField txtMedPesquisar;
    private javax.swing.JFormattedTextField txtMedTel;
    // End of variables declaration//GEN-END:variables
}
