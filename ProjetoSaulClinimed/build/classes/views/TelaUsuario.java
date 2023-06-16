package views;

import java.sql.*;
import dal.ModuloConexao;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void pesquisar_usuario() {
        String sql = "select `idUser` as `Id`, `nome` as `Nome`, `telefone` as `Telefone`, `email` as `Email`, `login` as `Login`, `senha` as `Senha`, `perfil` as `Perfil` from `TbUsuario` where `nome` like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuPesquisar1.getText() + "%");
            rs = pst.executeQuery();
            tbtUsuario1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campos() {
        int setar = tbtUsuario1.getSelectedRow();
        txtUsuId.setText(tbtUsuario1.getModel().getValueAt(setar, 0).toString());
        txtUsuNome.setText(tbtUsuario1.getModel().getValueAt(setar, 1).toString());
        txtUsuFone.setText(tbtUsuario1.getModel().getValueAt(setar, 2).toString());
        txtUsuEma.setText(tbtUsuario1.getModel().getValueAt(setar, 3).toString());
        txtUsuLog.setText(tbtUsuario1.getModel().getValueAt(setar, 4).toString());
        txtUsuSen.setText(tbtUsuario1.getModel().getValueAt(setar, 5).toString());
        String perfil = tbtUsuario1.getModel().getValueAt(setar, 6).toString();
        if (perfil.equals("Admin")) {
            cboUsoPerfil.setSelectedIndex(1);
        } else {
            cboUsoPerfil.setSelectedIndex(2);
        }
        btnAdicionar.setEnabled(false);

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
        String sqlConsultarLogin = "SELECT login FROM TbUsuario WHERE login= ?";
        String sqlConsultarTelefone = "SELECT telefone FROM TbUsuario WHERE telefone = ?";
        String sqlConsultarEmail = "SELECT email FROM TbUsuario WHERE email = ?";

        try {
            if (valorJaExiste(sqlConsultarLogin, txtUsuLog.getText())) {
                JOptionPane.showMessageDialog(null, "Login já cadastrado!");
                limpar();
                return;
            }

            if (valorJaExiste(sqlConsultarTelefone, txtUsuFone.getText())) {
                JOptionPane.showMessageDialog(null, "Telefone já cadastrado!");
                limpar();
                return;
            }

            if (valorJaExiste(sqlConsultarEmail, txtUsuEma.getText())) {
                JOptionPane.showMessageDialog(null, "Email já cadastrado!");
                limpar();
                return;
            }
        } catch (SQLException e) {
        }
        String sql = "insert into TbUsuario ( nome, telefone, login, senha, perfil, email) values (?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, txtUsuFone.getText());
            pst.setString(3, txtUsuLog.getText());
            pst.setString(4, txtUsuSen.getText());
            pst.setString(5, cboUsoPerfil.getSelectedItem().toString());
            pst.setString(6, txtUsuEma.getText());
            if ((txtUsuNome.getText().isEmpty()) || (txtUsuFone.getText().isEmpty()) || (txtUsuLog.getText().isEmpty()) || (txtUsuSen.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!");
                    limpar();
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void alterar() {
        String sql = "update TbUsuario set nome=?, telefone=?, login=?, senha=?, email=?, perfil=? where idUser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, txtUsuFone.getText());
            pst.setString(3, txtUsuLog.getText());
            pst.setString(4, txtUsuSen.getText());
            pst.setString(5, txtUsuEma.getText());
            pst.setString(6, cboUsoPerfil.getSelectedItem().toString());
            pst.setString(7, txtUsuId.getText());
            if ((txtUsuNome.getText().isEmpty()) || (cboUsoPerfil.getSelectedItem().equals("Selecionar")) || (txtUsuFone.getText().isEmpty()) || (txtUsuLog.getText().isEmpty()) || (txtUsuSen.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do usuário alterados com sucesso!");
                    limpar();
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void remover() {
        int confima = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confima == JOptionPane.YES_OPTION) {
            String sql = "delete from TbUsuario where idUser=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
                    limpar();
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void limpar() {
        txtUsuId.setText(null);
        txtUsuNome.setText(null);
        txtUsuFone.setText(null);
        txtUsuLog.setText(null);
        txtUsuSen.setText(null);
        txtUsuEma.setText(null);
        cboUsoPerfil.setSelectedIndex(0);
        ((DefaultTableModel) tbtUsuario1.getModel()).setRowCount(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDeletar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbtUsuario = new javax.swing.JTable();
        txtUsuPesquisar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbtUsuario1 = new javax.swing.JTable();
        txtUsuPesquisar1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnRemover = new javax.swing.JButton();
        btnAlterar1 = new javax.swing.JButton();
        btnAdicionar1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cboUsoPerfil = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtUsuLog = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtUsuFone = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        txtUsuSen = new javax.swing.JTextField();
        txtUsuEma = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuNome = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtUsuId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(204, 204, 219));
        jPanel2.setLayout(null);

        jLabel1.setForeground(new java.awt.Color(228, 19, 19));
        jLabel1.setText("*Campos Obrigatórios");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(960, 10, 118, 16);

        btnDeletar.setToolTipText("Excluir");
        btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });
        jPanel2.add(btnDeletar);
        btnDeletar.setBounds(290, 170, 80, 80);

        btnAlterar.setToolTipText("Alterar");
        btnAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAlterar);
        btnAlterar.setBounds(160, 170, 80, 80);

        tbtUsuario = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbtUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Telefone", "Email", "Login", "Senha", "Perfil"
            }
        ));
        tbtUsuario.setFocusable(false);
        tbtUsuario.getTableHeader().setResizingAllowed(false);
        tbtUsuario.getTableHeader().setReorderingAllowed(false);
        tbtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbtUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbtUsuario);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(140, 460, 1080, 220);

        txtUsuPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsuPesquisarKeyReleased(evt);
            }
        });
        jPanel2.add(txtUsuPesquisar);
        txtUsuPesquisar.setBounds(150, 420, 496, 22);
        jPanel2.add(jLabel9);
        jLabel9.setBounds(530, 310, 0, 0);

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setText("Pesquisar ");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(150, 400, 80, 19);

        btnAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAdicionar);
        btnAdicionar.setBounds(30, 170, 80, 80);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gerenciamento de Dados do Usuário");
        setMaximumSize(new java.awt.Dimension(1000, 520));
        setMinimumSize(new java.awt.Dimension(1000, 520));
        setPreferredSize(new java.awt.Dimension(1000, 520));

        jPanel1.setBackground(new java.awt.Color(234, 236, 239));
        jPanel1.setLayout(null);

        tbtUsuario = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbtUsuario1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Telefone", "Email", "Login", "Senha", "Perfil"
            }
        ));
        tbtUsuario1.setFocusable(false);
        tbtUsuario1.getTableHeader().setResizingAllowed(false);
        tbtUsuario1.getTableHeader().setReorderingAllowed(false);
        tbtUsuario1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbtUsuario1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbtUsuario1);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 300, 970, 170);

        txtUsuPesquisar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuPesquisar1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsuPesquisar1KeyReleased(evt);
            }
        });
        jPanel1.add(txtUsuPesquisar1);
        txtUsuPesquisar1.setBounds(70, 260, 500, 30);

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel13.setText("Pesquisar:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 270, 60, 16);

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
        btnRemover.setBounds(820, 180, 100, 40);

        btnAlterar1.setBackground(new java.awt.Color(67, 137, 186));
        btnAlterar1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        btnAlterar1.setForeground(new java.awt.Color(255, 255, 255));
        btnAlterar1.setText("Alterar");
        btnAlterar1.setToolTipText("Editar");
        btnAlterar1.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAlterar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnAlterar1);
        btnAlterar1.setBounds(820, 100, 100, 40);

        btnAdicionar1.setBackground(new java.awt.Color(0, 153, 0));
        btnAdicionar1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        btnAdicionar1.setForeground(new java.awt.Color(255, 255, 255));
        btnAdicionar1.setText("Cadastrar");
        btnAdicionar1.setToolTipText("Adicionar");
        btnAdicionar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar1.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAdicionar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdicionar1);
        btnAdicionar1.setBounds(820, 20, 100, 40);

        jPanel3.setBackground(new java.awt.Color(211, 215, 221));
        jPanel3.setLayout(null);

        cboUsoPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar", "Admin", "Atendente" }));
        jPanel3.add(cboUsoPerfil);
        cboUsoPerfil.setBounds(490, 160, 130, 30);

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel7.setText("*Perfil");
        jPanel3.add(jLabel7);
        jLabel7.setBounds(490, 140, 32, 16);
        jPanel3.add(txtUsuLog);
        txtUsuLog.setBounds(490, 90, 130, 30);

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel6.setText("*Login");
        jPanel3.add(jLabel6);
        jLabel6.setBounds(490, 70, 35, 20);

        try {
            txtUsuFone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel3.add(txtUsuFone);
        txtUsuFone.setBounds(330, 90, 130, 30);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("*Telefone");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(330, 70, 67, 20);
        jPanel3.add(txtUsuSen);
        txtUsuSen.setBounds(330, 160, 130, 30);
        jPanel3.add(txtUsuEma);
        txtUsuEma.setBounds(10, 160, 290, 30);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel4.setText("*Senha");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(330, 140, 38, 16);

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("Email");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(10, 140, 29, 16);
        jPanel3.add(txtUsuNome);
        txtUsuNome.setBounds(10, 90, 290, 30);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel8.setText("*Nome");
        jLabel8.setToolTipText("");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 70, 38, 20);

        txtUsuId.setEnabled(false);
        jPanel3.add(txtUsuId);
        txtUsuId.setBounds(10, 30, 40, 30);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel2.setText("Id Usuário");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(10, 10, 54, 16);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(20, 20, 640, 200);

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

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed

        remover();
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void tbtUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbtUsuarioMouseClicked

    }//GEN-LAST:event_tbtUsuarioMouseClicked

    private void txtUsuPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuPesquisarKeyReleased

    }//GEN-LAST:event_txtUsuPesquisarKeyReleased

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed

        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void tbtUsuario1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbtUsuario1MouseClicked
        setar_campos();
    }//GEN-LAST:event_tbtUsuario1MouseClicked

    private void txtUsuPesquisar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuPesquisar1KeyReleased
        pesquisar_usuario();
    }//GEN-LAST:event_txtUsuPesquisar1KeyReleased

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed

        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnAlterar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterar1ActionPerformed

        alterar();
    }//GEN-LAST:event_btnAlterar1ActionPerformed

    private void btnAdicionar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionar1ActionPerformed

        adicionar();
    }//GEN-LAST:event_btnAdicionar1ActionPerformed

    private void txtUsuPesquisar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuPesquisar1KeyPressed

    }//GEN-LAST:event_txtUsuPesquisar1KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAdicionar1;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAlterar1;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cboUsoPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbtUsuario;
    private javax.swing.JTable tbtUsuario1;
    private javax.swing.JTextField txtUsuEma;
    private javax.swing.JFormattedTextField txtUsuFone;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuLog;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuPesquisar;
    private javax.swing.JTextField txtUsuPesquisar1;
    private javax.swing.JTextField txtUsuSen;
    // End of variables declaration//GEN-END:variables
}
