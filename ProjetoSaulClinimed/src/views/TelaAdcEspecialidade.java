package views;

import java.sql.*;
import dal.ModuloConexao;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class TelaAdcEspecialidade extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaAdcEspecialidade() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void adicionar() {
        String sql = "insert into `TbEspecialidade` (`especialidade`, `preco`, `disponivelEspecialidade`) values (?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtAdcEspEsp.getText());
            pst.setString(2, txtAdcEsValor.getText());
            pst.setString(3, cboAdcEsDis.getSelectedItem().toString());
            if ((txtAdcEspEsp.getText().isEmpty()) || (txtAdcEsValor.getText().isEmpty()) || (cboAdcEsDis.getSelectedItem().equals("Selecionar"))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Especialidade adicionada com sucesso!");
                    limpar();
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_especialidade() {
        String sql = "select `idEspecialidade` as `Id`, `especialidade` as  `Especialidade`,`preco` as  `Valor`, `disponivelEspecialidade` as  `Disponível` from `TbEspecialidade` where especialidade like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtAdcEspPes.getText() + "%");
            rs = pst.executeQuery();
            tblEspecialidade.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campos() {
        int setar = tblEspecialidade.getSelectedRow();
        txtAdcEspId.setText(tblEspecialidade.getModel().getValueAt(setar, 0).toString());
        txtAdcEspEsp.setText(tblEspecialidade.getModel().getValueAt(setar, 1).toString());
        txtAdcEsValor.setText(tblEspecialidade.getModel().getValueAt(setar, 2).toString());
        String disponibilidade = tblEspecialidade.getModel().getValueAt(setar, 3).toString();
        if (disponibilidade.equals("Sim")) {
            cboAdcEsDis.setSelectedIndex(1);
        } else {
            cboAdcEsDis.setSelectedIndex(2);
        }
        btnAdicionar.setEnabled(false);
    }

    private void alterar() {
        String sql = "update TbEspecialidade set especialidade=?, preco=?, disponivelEspecialidade=? where idEspecialidade=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(3, cboAdcEsDis.getSelectedItem().toString());
            pst.setString(1, txtAdcEspEsp.getText());
            pst.setString(2, txtAdcEsValor.getText());
            pst.setString(4, txtAdcEspId.getText());
            if ((txtAdcEspEsp.getText().isEmpty()) || (txtAdcEsValor.getText().isEmpty()) || (cboAdcEsDis.getSelectedItem().equals("Selecionar"))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados da especialidade alterados com sucesso!");
                    limpar();
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void limpar() {
        txtAdcEspEsp.setText(null);
        txtAdcEspId.setText(null);
        txtAdcEsValor.setText(null);
        cboAdcEsDis.setSelectedIndex(0);
        ((DefaultTableModel) tblEspecialidade.getModel()).setRowCount(0);
    }

    private void remover() {
        int confima = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esta especialidade?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confima == JOptionPane.YES_OPTION) {
            String sql = "delete from TbEspecialidade where idEspecialidade=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtAdcEspId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Especialidade removido com sucesso!");
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
        txtAdcEspId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtAdcEspEsp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboAdcEsDis = new javax.swing.JComboBox<>();
        btnRemover = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        txtAdcEsValor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEspecialidade = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtAdcEspPes = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gerenciamento de  Especialidades");
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
        jLabel1.setBounds(60, 50, 30, 16);

        txtAdcEspId.setEnabled(false);
        txtAdcEspId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdcEspIdActionPerformed(evt);
            }
        });
        jPanel2.add(txtAdcEspId);
        txtAdcEspId.setBounds(60, 70, 30, 30);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel2.setText("Especialidade");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(60, 120, 110, 16);

        txtAdcEspEsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdcEspEspActionPerformed(evt);
            }
        });
        jPanel2.add(txtAdcEspEsp);
        txtAdcEspEsp.setBounds(60, 140, 300, 30);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("Disponível");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(60, 210, 90, 16);

        cboAdcEsDis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar", "Sim", "Não" }));
        jPanel2.add(cboAdcEsDis);
        cboAdcEsDis.setBounds(60, 230, 110, 30);

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
        btnRemover.setBounds(320, 400, 100, 40);

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
        btnAlterar.setBounds(180, 400, 100, 40);

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
        jPanel2.add(txtAdcEsValor);
        txtAdcEsValor.setBounds(60, 320, 130, 30);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel4.setText("Valor");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(60, 300, 28, 16);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(20, 30, 450, 470);

        jPanel3.setBackground(new java.awt.Color(211, 215, 221));

        tblEspecialidade = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblEspecialidade.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Especialidade", "Valor", "Disponível"
            }
        ));
        tblEspecialidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEspecialidadeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEspecialidade);

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("Pesquisar:");

        txtAdcEspPes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAdcEspPesKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtAdcEspPes))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5))
                    .addComponent(txtAdcEspPes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(490, 30, 500, 470);

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

    private void txtAdcEspIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdcEspIdActionPerformed

    }//GEN-LAST:event_txtAdcEspIdActionPerformed

    private void txtAdcEspEspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdcEspEspActionPerformed

    }//GEN-LAST:event_txtAdcEspEspActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txtAdcEspPesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdcEspPesKeyPressed
        pesquisar_especialidade();
    }//GEN-LAST:event_txtAdcEspPesKeyPressed

    private void tblEspecialidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEspecialidadeMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblEspecialidadeMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cboAdcEsDis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEspecialidade;
    private javax.swing.JTextField txtAdcEsValor;
    private javax.swing.JTextField txtAdcEspEsp;
    private javax.swing.JTextField txtAdcEspId;
    private javax.swing.JTextField txtAdcEspPes;
    // End of variables declaration//GEN-END:variables
}
