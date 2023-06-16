package views;

import dal.ModuloConexao;
import java.sql.*;
import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class TelaAdcExame extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaAdcExame() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void adicionar() {
        String sql = "insert into `TbExame` (`tipoExame`, `preco`, `disponivelExame`) values (?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtAdcExaExa.getText());
            pst.setString(2, txtAdcExaValor.getText());
            pst.setString(3, cboAdcExaDis.getSelectedItem().toString());
            if ((txtAdcExaExa.getText().isEmpty()) || (txtAdcExaValor.getText().isEmpty()) || (cboAdcExaDis.getSelectedItem().equals("Selecionar"))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Exame adicionado com sucesso!");
                    limpar();
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_especialidade() {
        String sql = "select `IdExame` as `Id`, `tipoExame` as  `Exame`,`preco` as  `Valor`, `disponivelExame` as  `Disponível` from `TbExame` where tipoExame like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtAdcExaPes.getText() + "%");
            rs = pst.executeQuery();
            tblAdcExa.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campos() {
        int setar = tblAdcExa.getSelectedRow();
        txtAdcExaId.setText(tblAdcExa.getModel().getValueAt(setar, 0).toString());
        txtAdcExaExa.setText(tblAdcExa.getModel().getValueAt(setar, 1).toString());
        txtAdcExaValor.setText(tblAdcExa.getModel().getValueAt(setar, 2).toString());
        String disponibilidade = tblAdcExa.getModel().getValueAt(setar, 3).toString();
        if (disponibilidade.equals("Sim")) {
            cboAdcExaDis.setSelectedIndex(1);
        } else {
            cboAdcExaDis.setSelectedIndex(2);
        }
        btnAdicionar.setEnabled(false);
    }

    private void alterar() {
        String sql = "update TbExame set tipoExame=?, preco=?, disponivelExame=? where idExame=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(3, cboAdcExaDis.getSelectedItem().toString());
            pst.setString(1, txtAdcExaExa.getText());
            pst.setString(2, txtAdcExaValor.getText());
            pst.setString(4, txtAdcExaId.getText());
            if ((txtAdcExaExa.getText().isEmpty()) || (txtAdcExaValor.getText().isEmpty()) || (cboAdcExaDis.getSelectedItem().equals("Selecionar"))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do exame alterados com sucesso!");
                    limpar();
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void limpar() {
        txtAdcExaExa.setText(null);
        txtAdcExaId.setText(null);
        txtAdcExaValor.setText(null);
        cboAdcExaDis.setSelectedIndex(0);
        ((DefaultTableModel) tblAdcExa.getModel()).setRowCount(0);
    }

    private void remover() {
        int confima = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este exame?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confima == JOptionPane.YES_OPTION) {
            String sql = "delete from TbExame where idExame=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtAdcExaId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Exame removido com sucesso!");
                    limpar();
                    btnAdicionar.setEnabled(true);
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtAdcExaId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtAdcExaExa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboAdcExaDis = new javax.swing.JComboBox<>();
        btnRemover = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtAdcExaValor = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAdcExa = new javax.swing.JTable();
        txtAdcExaPes = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gerenciamento de Exames");
        setMaximumSize(new java.awt.Dimension(1020, 560));
        setMinimumSize(new java.awt.Dimension(1020, 560));
        setPreferredSize(new java.awt.Dimension(1020, 560));

        jPanel1.setBackground(new java.awt.Color(234, 236, 239));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(211, 215, 221));
        jPanel2.setEnabled(false);
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel1.setText("Id");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(50, 30, 30, 16);

        txtAdcExaId.setEnabled(false);
        txtAdcExaId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdcExaIdActionPerformed(evt);
            }
        });
        jPanel2.add(txtAdcExaId);
        txtAdcExaId.setBounds(50, 50, 30, 30);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel2.setText("Exame");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(50, 110, 110, 16);

        txtAdcExaExa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdcExaExaActionPerformed(evt);
            }
        });
        jPanel2.add(txtAdcExaExa);
        txtAdcExaExa.setBounds(50, 140, 300, 30);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("Disponível");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(50, 210, 90, 16);

        cboAdcExaDis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar", "Sim", "Não" }));
        jPanel2.add(cboAdcExaDis);
        cboAdcExaDis.setBounds(50, 230, 110, 30);

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
        jPanel2.add(btnRemover);
        btnRemover.setBounds(310, 400, 100, 40);

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
        jPanel2.add(btnAlterar);
        btnAlterar.setBounds(170, 400, 100, 40);

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
        jPanel2.add(btnAdicionar);
        btnAdicionar.setBounds(30, 400, 100, 40);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel4.setText("Valor");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(50, 300, 28, 16);
        jPanel2.add(txtAdcExaValor);
        txtAdcExaValor.setBounds(50, 320, 130, 30);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(20, 20, 450, 480);

        jPanel3.setBackground(new java.awt.Color(211, 215, 221));

        tblAdcExa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Exame", "Valor", "Disponivel"
            }
        ));
        tblAdcExa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAdcExaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAdcExa);

        txtAdcExaPes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdcExaPesActionPerformed(evt);
            }
        });
        txtAdcExaPes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAdcExaPesKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("Pesquisar:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAdcExaPes, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(400, 400, 400))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAdcExaPes, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(490, 20, 500, 490);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAdcExaIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdcExaIdActionPerformed

    }//GEN-LAST:event_txtAdcExaIdActionPerformed

    private void txtAdcExaExaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdcExaExaActionPerformed

    }//GEN-LAST:event_txtAdcExaExaActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txtAdcExaPesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdcExaPesKeyReleased
        pesquisar_especialidade();
    }//GEN-LAST:event_txtAdcExaPesKeyReleased

    private void tblAdcExaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAdcExaMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblAdcExaMouseClicked

    private void txtAdcExaPesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdcExaPesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdcExaPesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cboAdcExaDis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAdcExa;
    private javax.swing.JTextField txtAdcExaExa;
    private javax.swing.JTextField txtAdcExaId;
    private javax.swing.JTextField txtAdcExaPes;
    private javax.swing.JTextField txtAdcExaValor;
    // End of variables declaration//GEN-END:variables
}
