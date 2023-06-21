package views;

import java.sql.*;
import dal.ModuloConexao;
import java.awt.HeadlessException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class TelaPaciente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaPaciente() {
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
        String anoP = txtCliDT.getText().substring(6);
        String dataCadastro = txtCliDC.getText();
        int anoPconvertido = Integer.parseInt(anoP);
        java.util.Date data = new java.util.Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        String dataDesktop = formatador.format(data);
        String anoDesktop = dataDesktop.substring(6);
        int anoDconvertido = Integer.parseInt(anoDesktop);
        int idade = anoDconvertido - anoPconvertido;

        if ((idade >= 1 && idade <= 105)) {

            if (dataCadastro.equals(dataDesktop)) {
                String sqlConsultarCpf = "SELECT cpf FROM TbPaciente WHERE cpf = ?";
                String sqlConsultarTelefone = "SELECT telefone FROM TbPaciente WHERE telefone = ?";
                String sqlConsultarEmail = "SELECT email FROM TbPaciente WHERE email = ?";

                try {
                    if (valorJaExiste(sqlConsultarCpf, txtCliCpf.getText())) {
                        JOptionPane.showMessageDialog(null, "CPF já cadastrado!");
                        limpar();
                        return;
                    }

                    if (valorJaExiste(sqlConsultarTelefone, txtCliTel.getText())) {
                        JOptionPane.showMessageDialog(null, "Telefone já cadastrado!");
                        limpar();
                        return;
                    }

                    if (valorJaExiste(sqlConsultarEmail, txtCliEma.getText())) {
                        JOptionPane.showMessageDialog(null, "Email já cadastrado!");
                        limpar();
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String sql = "insert into `TbPaciente` (`nome`, `dataNascimento`, `sexo`, `cpf`, `telefone`, `email`, `endereco`, `dataCadastro`) values (?,?,?,?,?,?,?,?)";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtCliNome.getText());
                    String diaN = txtCliDT.getText().substring(0, 2);
                    String mesN = txtCliDT.getText().substring(3, 5);
                    String anoN = txtCliDT.getText().substring(6);
                    String dataNasMylql = anoN + "-" + mesN + "-" + diaN;
                    pst.setString(2, dataNasMylql);
                    pst.setString(3, cboCliSexo.getSelectedItem().toString());
                    pst.setString(4, txtCliCpf.getText());
                    pst.setString(5, txtCliTel.getText());
                    pst.setString(6, txtCliEma.getText());
                    pst.setString(7, txtCliEnd.getText());
                    String diaC = txtCliDC.getText().substring(0, 2);
                    String mesC = txtCliDC.getText().substring(3, 5);
                    String anoC = txtCliDC.getText().substring(6);
                    String dataCasMylql = anoC + "-" + mesC + "-" + diaC;
                    pst.setString(8, dataCasMylql);

                    if ((txtCliNome.getText().isEmpty()) || (txtCliCpf.getText().isEmpty()) || (txtCliTel.getText().isEmpty()) || (txtCliDT.getText().isEmpty()) || (txtCliEnd.getText().isEmpty()) || (txtCliDC.getText().isEmpty())) {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
                    } else {
                        int adicionado = pst.executeUpdate();
                        if (adicionado > 0) {
                            JOptionPane.showMessageDialog(null, "Paciente adicionado com sucesso!");
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

        } else {
            JOptionPane.showMessageDialog(null, "Data Inválida!");
            limpar();
        }

    }

    private void pesquisar_paciente() {
        String sql = "select `idPaciente` as `Id`, `nome` as  `Nome`,`dataNascimento` as  `Data de Nascimento`,`sexo` as  `Sexo`, `cpf` as  `CPF`, `telefone` as  `Telefone`, `email` as  `Email`,`endereco` as  `Endereço`, `dataCadastro` as  `Data de Cadastro` from `TbPaciente` where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            tblPacientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campos() {
        int setar = tblPacientes.getSelectedRow();
        txtCliId.setText(tblPacientes.getModel().getValueAt(setar, 0).toString());
        txtCliNome.setText(tblPacientes.getModel().getValueAt(setar, 1).toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtCliDT.setText(sdf.format(tblPacientes.getModel().getValueAt(setar, 2)));
        String sexo = tblPacientes.getModel().getValueAt(setar, 3).toString();
        if (sexo.equals("F")) {
            cboCliSexo.setSelectedIndex(0);
        } else {
            cboCliSexo.setSelectedIndex(1);
        }
        txtCliCpf.setText(tblPacientes.getModel().getValueAt(setar, 4).toString());
        txtCliTel.setText(tblPacientes.getModel().getValueAt(setar, 5).toString());
        txtCliEma.setText(tblPacientes.getModel().getValueAt(setar, 6).toString());
        txtCliEnd.setText(tblPacientes.getModel().getValueAt(setar, 7).toString());
        txtCliDC.setText(sdf.format(tblPacientes.getModel().getValueAt(setar, 8)));
        btnAdicionar.setEnabled(false);
    }

    private void alterar() {
        String sql = "update TbPaciente set nome=?, dataNascimento=?, sexo=?, cpf=?, telefone=?, email=?, endereco=?, dataCadastro=? where IdPaciente=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            String diaN = txtCliDT.getText().substring(0, 2);
            String mesN = txtCliDT.getText().substring(3, 5);
            String anoN = txtCliDT.getText().substring(6);
            String dataNasMylql = anoN + "-" + mesN + "-" + diaN;
            pst.setString(2, dataNasMylql);
            pst.setString(3, cboCliSexo.getSelectedItem().toString());
            pst.setString(4, txtCliCpf.getText());
            pst.setString(5, txtCliTel.getText());
            pst.setString(6, txtCliEma.getText());
            pst.setString(7, txtCliEnd.getText());
            String diaC = txtCliDC.getText().substring(0, 2);
            String mesC = txtCliDC.getText().substring(3, 5);
            String anoC = txtCliDC.getText().substring(6);
            String dataCasMylql = anoC + "-" + mesC + "-" + diaC;
            pst.setString(8, dataCasMylql);
            pst.setString(9, txtCliId.getText());
            if ((txtCliNome.getText().isEmpty()) || (txtCliCpf.getText().isEmpty()) || (txtCliTel.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do paciente alterados com sucesso!");
                    limpar();
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void remover() {
        int confima = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este paciente?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confima == JOptionPane.YES_OPTION) {
            String sql = "delete from TbPaciente where idPaciente=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCliId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
                    limpar();
                    btnAdicionar.setEnabled(true);
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void limpar() {
        txtCliPesquisar.setText(null);
        txtCliId.setText(null);
        txtCliNome.setText(null);
        cboCliSexo.setSelectedIndex(0);
        txtCliCpf.setText(null);
        txtCliTel.setText(null);
        txtCliEma.setText(null);
        txtCliDT.setText(null);
        txtCliDC.setText(null);
        txtCliEnd.setText(null);
        ((DefaultTableModel) tblPacientes.getModel()).setRowCount(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtCliPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPacientes = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtCliDC = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCliTel = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCliCpf = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCliDT = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        cboCliSexo = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCliEma = new javax.swing.JTextField();
        txtCliEnd = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gerenciamento de Dados do Paciente");
        setMaximumSize(new java.awt.Dimension(1000, 520));
        setMinimumSize(new java.awt.Dimension(1000, 520));
        setPreferredSize(new java.awt.Dimension(1000, 520));

        jPanel1.setBackground(new java.awt.Color(234, 236, 239));
        jPanel1.setLayout(null);

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel12.setText("Pesquisar:");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 240, 60, 16);

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });
        jPanel1.add(txtCliPesquisar);
        txtCliPesquisar.setBounds(80, 230, 510, 30);

        tblPacientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblPacientes.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tblPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Data Nascimento", "Sexo", "CPF", "Telefone", "Email", "Endereço", "Data Cadastro"
            }
        ));
        tblPacientes.setFocusable(false);
        tblPacientes.getTableHeader().setReorderingAllowed(false);
        tblPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPacientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPacientes);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 270, 970, 200);

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
        btnAdicionar.setBounds(820, 20, 100, 40);

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
        btnAlterar.setBounds(820, 90, 100, 40);

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
        btnRemover.setBounds(820, 160, 100, 40);

        jPanel2.setBackground(new java.awt.Color(211, 215, 221));
        jPanel2.setLayout(null);

        try {
            txtCliDC.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel2.add(txtCliDC);
        txtCliDC.setBounds(630, 140, 80, 30);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel8.setText("*Data de Cadastro");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(630, 120, 110, 16);

        try {
            txtCliTel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel2.add(txtCliTel);
        txtCliTel.setBounds(630, 80, 100, 30);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel4.setText("*Telefone");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(630, 60, 60, 16);

        try {
            txtCliCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel2.add(txtCliCpf);
        txtCliCpf.setBounds(520, 80, 100, 30);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("*CPF");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(520, 60, 25, 20);

        try {
            txtCliDT.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCliDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliDTActionPerformed(evt);
            }
        });
        jPanel2.add(txtCliDT);
        txtCliDT.setBounds(400, 80, 80, 30);

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel11.setText("*Data Nascimento");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(400, 60, 100, 16);

        cboCliSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "F", "M" }));
        cboCliSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCliSexoActionPerformed(evt);
            }
        });
        jPanel2.add(cboCliSexo);
        cboCliSexo.setBounds(330, 80, 50, 30);

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel10.setText("Sexo");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(330, 60, 30, 16);

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("*Email");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(330, 120, 34, 16);
        jPanel2.add(txtCliEma);
        txtCliEma.setBounds(330, 140, 290, 30);
        jPanel2.add(txtCliEnd);
        txtCliEnd.setBounds(20, 140, 290, 30);

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel9.setText("*Endereço");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(20, 120, 60, 16);
        jPanel2.add(txtCliNome);
        txtCliNome.setBounds(20, 80, 290, 30);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel2.setText("*Nome");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 60, 40, 16);

        txtCliId.setEnabled(false);
        jPanel2.add(txtCliId);
        txtCliId.setBounds(20, 30, 40, 30);

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel7.setText("Id Paciente");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 10, 60, 16);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 20, 750, 180);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void tblPacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacientesMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblPacientesMouseClicked

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        pesquisar_paciente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void cboCliSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCliSexoActionPerformed

    }//GEN-LAST:event_cboCliSexoActionPerformed

    private void txtCliDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDTActionPerformed

    }//GEN-LAST:event_txtCliDTActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cboCliSexo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPacientes;
    private javax.swing.JFormattedTextField txtCliCpf;
    private javax.swing.JFormattedTextField txtCliDC;
    private javax.swing.JFormattedTextField txtCliDT;
    private javax.swing.JTextField txtCliEma;
    private javax.swing.JTextField txtCliEnd;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JFormattedTextField txtCliTel;
    // End of variables declaration//GEN-END:variables
}
