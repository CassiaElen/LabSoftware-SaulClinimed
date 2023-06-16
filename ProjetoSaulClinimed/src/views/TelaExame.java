package views;

import java.sql.*;
import dal.ModuloConexao;
import java.awt.HeadlessException;
import java.text.DateFormat;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class TelaExame extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaExame() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void adicionar() {
        java.util.Date dataJCalenDE = txtExaDE.getDate();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        String dataFormatadaDE = formatador.format(dataJCalenDE);
        LocalDate dataDigitada = LocalDate.parse(dataFormatadaDE);
        LocalDate dataAtual = LocalDate.now();

        if (dataDigitada.isBefore(dataAtual)) {
            JOptionPane.showMessageDialog(null, "Data inválida");
        } else {
            String sql = "insert into TbAgenExame (dataExame, horario, tipoExame, pacienteId, nomePaciente, medicoId, nomeMedico, precoExame, concluidoExame) values (?,?,?,?,?,?,?,?,?)";
            try {
                pst = conexao.prepareStatement(sql);
                String diaC = dataFormatadaDE.substring(0, 2);
                String mesC = dataFormatadaDE.substring(3, 5);
                String anoC = dataFormatadaDE.substring(6);
                String dataMylql = anoC + "-" + mesC + "-" + diaC;
                pst.setString(1, dataMylql);
                pst.setString(2, cboExaHor.getSelectedItem().toString());
                pst.setString(3, txtExaExa.getText());
                pst.setString(4, txtExaIP.getText());
                pst.setString(5, txtExaNomPac.getText());
                pst.setString(6, txtExaIM.getText());
                pst.setString(7, txtExaNomMed.getText());
                pst.setString(8, txtExaValor.getText());
                pst.setString(9, cboExaCon.getSelectedItem().toString());

                if ((txtExaDE.getDate() == null) || (cboExaCon.getSelectedItem().equals("Selecionar")) || (txtExaIP.getText().isEmpty()) || (cboExaHor.getSelectedItem().equals("Selecionar Horario")) || (txtExaIM.getText().isEmpty()) || (txtExaNomMed.getText().isEmpty()) || (txtExaNomPac.getText().isEmpty()) || (txtExaValor.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
                } else {
                    int adicionado = pst.executeUpdate();
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "Exame Registrado com sucesso!");
                        limpar();
                    }
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void pesquisar_Exame() {

        String filtro = cboConFilPes.getSelectedItem().toString();

        try {
            if (filtro.equals("Paciente")) {
                String sqlP = "SELECT `idExame` AS `Id`, `tipoExame` AS `Tipo de Exame`, `dataExame` AS `Data do Exame`, `horario` AS `Horas`, `pacienteId` AS `Id do Paciente`, `nomePaciente` AS `Nome do Paciente`, `medicoId` AS `Id do Médico`, `nomeMedico` AS `Nome do Medico`, `precoExame` AS `Valor`, `concluidoExame` AS `Concluído` FROM `TbAgenExame` where nomePaciente like ?";
                try {
                    pst = conexao.prepareStatement(sqlP);
                    pst.setString(1, txtExaPesquisar.getText() + "%");
                    rs = pst.executeQuery();
                    tblExame.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else if (filtro.equals("Médico")) {
                String sqlM = "SELECT `idExame` AS `Id`, `tipoExame` AS `Tipo de Exame`, `dataExame` AS `Data do Exame`, `horario` AS `Horas`, `pacienteId` AS `Id do Paciente`, `nomePaciente` AS `Nome do Paciente`, `medicoId` AS `Id do Médico`, `nomeMedico` AS `Nome do Medico`, `precoExame` AS `Valor`, `concluidoExame` AS `Concluído` FROM `TbAgenExame` where nomeMedico like ?";
                try {
                    pst = conexao.prepareStatement(sqlM);
                    pst.setString(1, txtExaPesquisar.getText() + "%");
                    rs = pst.executeQuery();
                    tblExame.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else if (filtro.equals("Exame")) {
                String sqlE = "SELECT `idExame` AS `Id`, `tipoExame` AS `Tipo de Exame`, `dataExame` AS `Data do Exame`, `horario` AS `Horas`, `pacienteId` AS `Id do Paciente`, `nomePaciente` AS `Nome do Paciente`, `medicoId` AS `Id do Médico`, `nomeMedico` AS `Nome do Medico`, `precoExame` AS `Valor`, `concluidoExame` AS `Concluído` FROM `TbAgenExame` where tipoExame like ?";
                try {
                    pst = conexao.prepareStatement(sqlE);
                    pst.setString(1, txtExaPesquisar.getText() + "%");
                    rs = pst.executeQuery();
                    tblExame.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else if (filtro.equals("Data")) {
                String sqlD = "SELECT `idExame` AS `Id`, `tipoExame` AS `Tipo de Exame`, `dataExame` AS `Data do Exame`, `horario` AS `Horas`, `pacienteId` AS `Id do Paciente`, `nomePaciente` AS `Nome do Paciente`, `medicoId` AS `Id do Médico`, `nomeMedico` AS `Nome do Medico`, `precoExame` AS `Valor`, `concluidoExame` AS `Concluído` FROM `TbAgenExame` where dataExame like ?";
                try {
                    pst = conexao.prepareStatement(sqlD);
                    pst.setString(1, txtExaPesquisar.getText() + "%");
                    rs = pst.executeQuery();
                    tblExame.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                String sqlH = "SELECT `idExame` AS `Id`, `tipoExame` AS `Tipo de Exame`, `dataExame` AS `Data do Exame`, `horario` AS `Horas`, `pacienteId` AS `Id do Paciente`, `nomePaciente` AS `Nome do Paciente`, `medicoId` AS `Id do Médico`, `nomeMedico` AS `Nome do Medico`, `precoExame` AS `Valor`, `concluidoExame` AS `Concluído` FROM `TbAgenExame` where horario like ?";
                try {
                    pst = conexao.prepareStatement(sqlH);
                    pst.setString(1, txtExaPesquisar.getText() + "%");
                    rs = pst.executeQuery();
                    tblExame.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        } catch (HeadlessException e) {
        }

    }

    public void setar_campos() {
        int setar = tblExame.getSelectedRow();
        txtExaId.setText(tblExame.getModel().getValueAt(setar, 0).toString());
        txtExaExa.setText(tblExame.getModel().getValueAt(setar, 1).toString());
        Date dataDE = (Date) tblExame.getModel().getValueAt(setar, 2);
        txtExaDE.setDate(dataDE);
        String hora = tblExame.getModel().getValueAt(setar, 3).toString();
        if (hora.equals("08:00 / 09:00")) {
            cboExaCon.setSelectedIndex(1);
        } else if (hora.equals("09:00 /10:00")) {
            cboExaHor.setSelectedIndex(2);
        } else if (hora.equals("10:00 /11:00")) {
            cboExaHor.setSelectedIndex(3);
        } else if (hora.equals("11:00 /12:00")) {
            cboExaHor.setSelectedIndex(4);
        } else if (hora.equals("12:00 /13:00")) {
            cboExaHor.setSelectedIndex(5);
        } else if (hora.equals("13:00 /14:00")) {
            cboExaHor.setSelectedIndex(6);
        } else if (hora.equals("14:00 /15:00")) {
            cboExaHor.setSelectedIndex(7);
        } else {
            cboExaHor.setSelectedIndex(8);
        }
        txtExaIP.setText(tblExame.getModel().getValueAt(setar, 4).toString());
        txtExaNomPac.setText(tblExame.getModel().getValueAt(setar, 5).toString());
        txtExaIM.setText(tblExame.getModel().getValueAt(setar, 6).toString());
        txtExaNomMed.setText(tblExame.getModel().getValueAt(setar, 7).toString());
        txtExaValor.setText(tblExame.getModel().getValueAt(setar, 8).toString());
        String concluido = tblExame.getModel().getValueAt(setar, 9).toString();
        if (concluido.equals("Sim")) {
            cboExaCon.setSelectedIndex(1);
        } else {
            cboExaCon.setSelectedIndex(2);
        }
        btnAdicionar.setEnabled(false);
    }

    private void alterar() {
        String sql = "update TbAgenExame set dataExame=?, horario=?, tipoExame=?, pacienteId=?, nomePaciente=?, medicoId=?, nomeMedico=?, precoExame=?, concluidoExame=? where IdExame=?";
        java.util.Date dataJCalenDE = txtExaDE.getDate();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        String dataFormatadaDE = formatador.format(dataJCalenDE);
        try {
            pst = conexao.prepareStatement(sql);
            String dia = dataFormatadaDE.substring(0, 2);
            String mes = dataFormatadaDE.substring(3, 5);
            String ano = dataFormatadaDE.substring(6);
            String dataMylql = ano + "-" + mes + "-" + dia;
            pst.setString(1, dataMylql);
            pst.setString(2, cboExaHor.getSelectedItem().toString());
            pst.setString(3, txtExaExa.getText());
            pst.setString(4, txtExaIP.getText());
            pst.setString(5, txtExaNomPac.getText());
            pst.setString(6, txtExaIM.getText());
            pst.setString(7, txtExaNomMed.getText());
            pst.setString(8, txtExaValor.getText());
            pst.setString(9, cboExaCon.getSelectedItem().toString());
            pst.setString(10, txtExaId.getText());
            if ((txtExaDE.getDate() == null) || (cboExaCon.getSelectedItem().equals("Selecionar")) || (txtExaIP.getText().isEmpty()) || (cboExaHor.getSelectedItem().equals("Selecionar Horario")) || (txtExaIM.getText().isEmpty()) || (txtExaNomMed.getText().isEmpty()) || (txtExaNomPac.getText().isEmpty()) || (txtExaValor.getText().isEmpty())) {
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

    private void remover() {
        int confima = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este exame?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confima == JOptionPane.YES_OPTION) {
            String sql = "delete from TbAgenExame where idExame=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtExaId.getText());
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

    private void limpar() {
        txtExaId.setText(null);
        txtExaDE.setDate(null);
        txtExaIP.setText(null);
        txtExaExa.setText(null);
        txtExaNomPac.setText(null);
        txtExaIM.setText(null);
        txtExaNomMed.setText(null);
        txtExaValor.setText(null);
        txtExaPesMed.setText(null);
        txtExaPesPac.setText(null);
        txtExaPesExa.setText(null);
        cboExaHor.setSelectedIndex(0);
        cboExaCon.setSelectedIndex(0);
        ((DefaultTableModel) tblExame.getModel()).setRowCount(0);
        ((DefaultTableModel) tblExaPesExa.getModel()).setRowCount(0);
        ((DefaultTableModel) tblExaPesMed.getModel()).setRowCount(0);
        ((DefaultTableModel) tblExaPesPac.getModel()).setRowCount(0);
    }

    private void consultar_medico() {
        String sql = "SELECT `idMedico` AS `Id`, `nome` AS `Nome` FROM `TbMedico` where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtExaPesMed.getText() + "%");
            rs = pst.executeQuery();
            tblExaPesMed.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campoMed() {
        int setar = tblExaPesMed.getSelectedRow();
        txtExaIM.setText(tblExaPesMed.getModel().getValueAt(setar, 0).toString());
        txtExaNomMed.setText(tblExaPesMed.getModel().getValueAt(setar, 1).toString());
    }

    private void consultar_paciente() {
        String sql = "SELECT `idPaciente` AS `Id`, `nome` AS `Nome` FROM `TbPaciente` WHERE `nome` LIKE ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtExaPesPac.getText() + "%");
            rs = pst.executeQuery();
            tblExaPesPac.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campoPac() {
        int setar = tblExaPesPac.getSelectedRow();
        txtExaIP.setText(tblExaPesPac.getModel().getValueAt(setar, 0).toString());
        txtExaNomPac.setText(tblExaPesPac.getModel().getValueAt(setar, 1).toString());
    }

    private void consultar_exame() {
        String sql = "SELECT `tipoExame` AS `Exame`, `preco` AS `Valor` FROM `TbExame` WHERE `tipoExame` LIKE ? AND `disponivelExame` = 'sim'";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtExaPesExa.getText() + "%");
            rs = pst.executeQuery();
            tblExaPesExa.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campoExa() {
        int setar = tblExaPesExa.getSelectedRow();
        txtExaExa.setText(tblExaPesExa.getModel().getValueAt(setar, 0).toString());
        txtExaValor.setText(tblExaPesExa.getModel().getValueAt(setar, 1).toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtExaId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtExaExa = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cboExaHor = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtExaNomPac = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtExaNomMed = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtExaIM = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtExaIP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboExaCon = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtExaValor = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtExaDE = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        txtExaPesPac = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblExaPesPac = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        txtExaPesMed = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblExaPesMed = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtExaPesExa = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblExaPesExa = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblExame = new javax.swing.JTable();
        txtExaPesquisar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        cboConFilPes = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(jList1);

        setBackground(new java.awt.Color(234, 236, 239));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Agendamento de Exames");
        setMaximumSize(new java.awt.Dimension(1020, 560));
        setMinimumSize(new java.awt.Dimension(1020, 560));
        setPreferredSize(new java.awt.Dimension(1020, 560));

        jPanel1.setBackground(new java.awt.Color(234, 236, 239));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(211, 215, 221));
        jPanel2.setLayout(null);

        txtExaId.setEnabled(false);
        jPanel2.add(txtExaId);
        txtExaId.setBounds(10, 40, 40, 30);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel1.setText("Id Exame");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(10, 20, 70, 16);

        txtExaExa.setEnabled(false);
        txtExaExa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExaExaActionPerformed(evt);
            }
        });
        jPanel2.add(txtExaExa);
        txtExaExa.setBounds(160, 40, 190, 30);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel4.setText("*Tipo de Exame");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(160, 20, 83, 16);

        cboExaHor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar Horario", "08:00 / 09:00", "09:00 /10:00", "10:00 /11:00 ", "11:00/12:00", "12:00/13:00", "13:00/14:00", "14:00/15:00", "15:00/16:00" }));
        cboExaHor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboExaHorActionPerformed(evt);
            }
        });
        jPanel2.add(cboExaHor);
        cboExaHor.setBounds(160, 90, 190, 30);

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("*Hora");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(160, 70, 31, 16);

        txtExaNomPac.setEnabled(false);
        jPanel2.add(txtExaNomPac);
        txtExaNomPac.setBounds(160, 140, 190, 30);

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel6.setText("Paciente");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(160, 120, 45, 16);

        txtExaNomMed.setEnabled(false);
        jPanel2.add(txtExaNomMed);
        txtExaNomMed.setBounds(160, 190, 190, 30);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel8.setText("Médico");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(160, 170, 40, 16);

        txtExaIM.setEnabled(false);
        jPanel2.add(txtExaIM);
        txtExaIM.setBounds(10, 190, 120, 30);

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel7.setText("*Id Médico");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(10, 170, 59, 16);

        txtExaIP.setEnabled(false);
        txtExaIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExaIPActionPerformed(evt);
            }
        });
        jPanel2.add(txtExaIP);
        txtExaIP.setBounds(10, 140, 120, 30);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("*Id Paciente");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(10, 120, 64, 16);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel2.setText("*Data");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 70, 30, 16);

        cboExaCon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar", "Sim", "Não" }));
        jPanel2.add(cboExaCon);
        cboExaCon.setBounds(160, 240, 190, 30);

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel11.setText("*Concluído");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(160, 220, 60, 20);

        txtExaValor.setEnabled(false);
        txtExaValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExaValorActionPerformed(evt);
            }
        });
        jPanel2.add(txtExaValor);
        txtExaValor.setBounds(10, 240, 120, 30);

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel9.setText("Valor");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(10, 220, 28, 16);
        jPanel2.add(txtExaDE);
        txtExaDE.setBounds(10, 90, 120, 30);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 10, 360, 300);

        jPanel4.setBackground(new java.awt.Color(211, 215, 221));
        jPanel4.setLayout(null);

        txtExaPesPac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtExaPesPacKeyPressed(evt);
            }
        });
        jPanel4.add(txtExaPesPac);
        txtExaPesPac.setBounds(10, 30, 170, 30);
        jPanel4.add(jLabel16);
        jLabel16.setBounds(185, 31, 33, 0);

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel17.setText("Pesquisar Paciente");
        jPanel4.add(jLabel17);
        jLabel17.setBounds(10, 10, 98, 16);

        tblExaPesPac = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblExaPesPac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                " Id", "Paciente"
            }
        ));
        tblExaPesPac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblExaPesPacMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblExaPesPac);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(10, 80, 170, 130);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(580, 10, 200, 230);

        jPanel5.setBackground(new java.awt.Color(211, 215, 221));
        jPanel5.setLayout(null);

        txtExaPesMed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtExaPesMedMouseClicked(evt);
            }
        });
        txtExaPesMed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtExaPesMedKeyPressed(evt);
            }
        });
        jPanel5.add(txtExaPesMed);
        txtExaPesMed.setBounds(10, 30, 170, 30);

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel14.setText("Pesquisar Médico");
        jPanel5.add(jLabel14);
        jLabel14.setBounds(10, 10, 126, 19);
        jPanel5.add(jLabel15);
        jLabel15.setBounds(151, 43, 33, 0);

        tblExaPesMed = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblExaPesMed.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id", "Médico"
            }
        ));
        tblExaPesMed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblExaPesMedMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblExaPesMed);

        jPanel5.add(jScrollPane4);
        jScrollPane4.setBounds(10, 80, 170, 130);

        jPanel1.add(jPanel5);
        jPanel5.setBounds(380, 10, 190, 230);

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
        jLabel19.setText("Pesquisar Exame");
        jPanel3.add(jLabel19);
        jLabel19.setBounds(20, 10, 120, 19);

        tblExaPesExa = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblExaPesExa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Exame", "Valor"
            }
        ));
        tblExaPesExa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblExaPesExaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblExaPesExa);

        jPanel3.add(jScrollPane3);
        jScrollPane3.setBounds(10, 80, 170, 130);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(790, 10, 200, 230);

        tblExame = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblExame.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tblExame.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Exame", "Data", "Hora", "Id Paciente", "Paciente", "Id Medico", "Médico", "Valor", "Concluído"
            }
        ));
        tblExame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblExameMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblExame);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 380, 990, 130);

        txtExaPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtExaPesquisarKeyPressed(evt);
            }
        });
        jPanel1.add(txtExaPesquisar);
        txtExaPesquisar.setBounds(80, 340, 510, 30);

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel13.setText("Pesquisar:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(20, 350, 60, 16);

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
        btnAdicionar.setBounds(460, 270, 100, 40);

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
        btnAlterar.setBounds(620, 270, 100, 40);

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
        btnRemover.setBounds(780, 270, 100, 40);

        cboConFilPes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Paciente", "Médico", "Exame", "Data", "Hora" }));
        cboConFilPes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboConFilPesItemStateChanged(evt);
            }
        });
        jPanel1.add(cboConFilPes);
        cboConFilPes.setBounds(870, 340, 130, 30);

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel10.setText("Pesquisar por:");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(790, 350, 80, 16);

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

    private void txtExaExaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExaExaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExaExaActionPerformed

    private void txtExaValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExaValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExaValorActionPerformed

    private void txtExaIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExaIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExaIPActionPerformed

    private void txtExaPesPacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExaPesPacKeyPressed
        consultar_paciente();
    }//GEN-LAST:event_txtExaPesPacKeyPressed

    private void tblExaPesPacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblExaPesPacMouseClicked
        setar_campoPac();
    }//GEN-LAST:event_tblExaPesPacMouseClicked

    private void txtExaPesMedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExaPesMedMouseClicked

    }//GEN-LAST:event_txtExaPesMedMouseClicked

    private void txtExaPesMedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExaPesMedKeyPressed
        consultar_medico();
    }//GEN-LAST:event_txtExaPesMedKeyPressed

    private void tblExaPesMedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblExaPesMedMouseClicked
        setar_campoMed();
    }//GEN-LAST:event_tblExaPesMedMouseClicked

    private void txtExaPesExaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExaPesExaKeyPressed
        consultar_exame();
    }//GEN-LAST:event_txtExaPesExaKeyPressed

    private void tblExaPesExaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblExaPesExaMouseClicked
        setar_campoExa();
    }//GEN-LAST:event_tblExaPesExaMouseClicked

    private void tblExameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblExameMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblExameMouseClicked

    private void txtExaPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExaPesquisarKeyPressed
        pesquisar_Exame();
    }//GEN-LAST:event_txtExaPesquisarKeyPressed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed

        /*     String dia = txtExaDE.getText().substring(0, 2);
            int diaE = Integer.parseInt(dia);
            String mes = txtExaDE.getText().substring(3, 5);
            int mesE = Integer.parseInt(mes);
            String ano = txtExaDE.getText().substring(6);
            int anoE = Integer.parseInt(ano);
            java.util.Date data = new java.util.Date();
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
            String dataDesktop = formatador.format(data);
            String diaD = dataDesktop.substring(0, 2);
            int diaS = Integer.parseInt(diaD);
            String mesD = dataDesktop.substring(3, 5);
            int mesS = Integer.parseInt(mesD);
            String anoD = dataDesktop.substring(6);
            int anoS = Integer.parseInt(anoD);
            if ((anoS == anoE) || (mesS <= mesE) || (diaS <= diaE)) {
            adicionar();
            } else if (anoS < anoE) || (mesE >= 11 && mesE <=12) || (diaE >= 1 ) {
            
            }else{
            
            }
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String diaC = txtExaDE.getText().substring(0, 2);
            String mesC = txtExaDE.getText().substring(3, 5);
            String anoC = txtExaDE.getText().substring(6);
            String dataMylql = anoC + "-" + mesC + "-" + diaC;
            Date date1;
        try {
            date1 = (Date) dateFormat.parse(dataMylql);
             java.util.Date data = new java.util.Date();
            if(date1.before(data)){
                adicionar();
            } else{
                JOptionPane.showMessageDialog(null, "faço cadastrto nao");
            }
     
        } catch (ParseException ex) {
            Logger.getLogger(TelaExame.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        adicionar();

    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void cboExaHorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboExaHorActionPerformed

    }//GEN-LAST:event_cboExaHorActionPerformed

    private void cboConFilPesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboConFilPesItemStateChanged

    }//GEN-LAST:event_cboConFilPesItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cboConFilPes;
    private javax.swing.JComboBox<String> cboExaCon;
    private javax.swing.JComboBox<String> cboExaHor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tblExaPesExa;
    private javax.swing.JTable tblExaPesMed;
    private javax.swing.JTable tblExaPesPac;
    private javax.swing.JTable tblExame;
    private com.toedter.calendar.JDateChooser txtExaDE;
    private javax.swing.JTextField txtExaExa;
    private javax.swing.JTextField txtExaIM;
    private javax.swing.JTextField txtExaIP;
    private javax.swing.JTextField txtExaId;
    private javax.swing.JTextField txtExaNomMed;
    private javax.swing.JTextField txtExaNomPac;
    private javax.swing.JTextField txtExaPesExa;
    private javax.swing.JTextField txtExaPesMed;
    private javax.swing.JTextField txtExaPesPac;
    private javax.swing.JTextField txtExaPesquisar;
    private javax.swing.JTextField txtExaValor;
    // End of variables declaration//GEN-END:variables
}
