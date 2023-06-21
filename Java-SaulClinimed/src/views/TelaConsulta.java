package views;

import java.sql.*;
import dal.ModuloConexao;
import java.awt.HeadlessException;
import java.text.DateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import java.time.LocalDate;

public class TelaConsulta extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaConsulta() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void adicionar() {

        java.util.Date dataJCalenDC = txtConDC.getDate();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        String dataFormatadaDC = formatador.format(dataJCalenDC);
        LocalDate dataDigitada = LocalDate.parse(dataFormatadaDC);
        LocalDate dataAtual = LocalDate.now();

        if (dataDigitada.isBefore(dataAtual)) {
            JOptionPane.showMessageDialog(null, "Data inválIda");
        } else {
            String sql = "insert into TbAgenConsulta (dataConsulta, horario, especialidadeCon, pacienteId, nomePaciente, medicoId, nomeMedico, precoEspecialidade, concluidoConsulta) values (?,?,?,?,?,?,?,?,?)";
            try {
                pst = conexao.prepareStatement(sql);
                String dia = dataFormatadaDC.substring(0, 2);
                String mes = dataFormatadaDC.substring(3, 5);
                String ano = dataFormatadaDC.substring(6);
                String dataMylql = ano + "-" + mes + "-" + dia;
                pst.setString(1, dataMylql);
                pst.setString(2, cboConHor.getSelectedItem().toString());
                pst.setString(3, txtConEsp.getText());
                pst.setString(4, txtConIP.getText());
                pst.setString(5, txtConNomPac.getText());
                pst.setString(6, txtConIM.getText());
                pst.setString(7, txtConNomMed.getText());
                pst.setString(8, txtConValor.getText());
                pst.setString(9, cboConCon.getSelectedItem().toString());

                if ((txtConEsp.getText().isEmpty()) || (txtConDC.getDate() == null) || (cboConCon.getSelectedItem().equals("Selecionar")) || (cboConHor.getSelectedItem().equals("Selecionar Horario")) || (txtConIP.getText().isEmpty()) || (txtConIM.getText().isEmpty()) || (txtConNomMed.getText().isEmpty()) || (txtConNomPac.getText().isEmpty()) || (txtConValor.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
                } else {
                    int adicionado = pst.executeUpdate();
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "Consulta Registrada com sucesso!");
                        limpar();
                    }
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void pesquisar_Consulta() {

        String filtro = cboConFilPes.getSelectedItem().toString();

        try {
            if (filtro.equals("Paciente")) {
                String sqlP = "SELECT `idConsulta` AS `Id`, `dataConsulta` AS `Data`, `horario` AS `Horas`, `especialidadeCon` AS `Especialidade`, `pacienteId` AS `Id do Paciente`, `nomePaciente` AS `Nome do Paciente`, `medicoId` AS `Id do Médico`, `nomeMedico` AS `Nome do Medico`, `precoEspecialidade` AS `Valor`,`concluidoConsulta` AS `Concluído` FROM `TbAgenConsulta` where nomePaciente like ?";
                try {
                    pst = conexao.prepareStatement(sqlP);
                    pst.setString(1, txtConPesquisar.getText() + "%");
                    rs = pst.executeQuery();
                    tblConsulta.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else if (filtro.equals("Médico")) {
                String sqlM = "SELECT `idConsulta` AS `Id`, `dataConsulta` AS `Data`, `horario` AS `Horas`, `especialidadeCon` AS `Especialidade`, `pacienteId` AS `Id do Paciente`, `nomePaciente` AS `Nome do Paciente`, `medicoId` AS `Id do Médico`, `nomeMedico` AS `Nome do Medico`, `precoEspecialidade` AS `Valor`,`concluidoConsulta` AS `Concluído` FROM `TbAgenConsulta` where nomeMedico like ?";
                try {
                    pst = conexao.prepareStatement(sqlM);
                    pst.setString(1, txtConPesquisar.getText() + "%");
                    rs = pst.executeQuery();
                    tblConsulta.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else if (filtro.equals("Especialidade")) {
                String sqlE = "SELECT `idConsulta` AS `Id`, `dataConsulta` AS `Data`, `horario` AS `Horas`, `especialidadeCon` AS `Especialidade`, `pacienteId` AS `Id do Paciente`, `nomePaciente` AS `Nome do Paciente`, `medicoId` AS `Id do Médico`, `nomeMedico` AS `Nome do Medico`, `precoEspecialidade` AS `Valor`,`concluidoConsulta` AS `Concluído` FROM `TbAgenConsulta` where especialidadeCon like ?";
                try {
                    pst = conexao.prepareStatement(sqlE);
                    pst.setString(1, txtConPesquisar.getText() + "%");
                    rs = pst.executeQuery();
                    tblConsulta.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else if (filtro.equals("Data")) {
                String sqlD = "SELECT `idConsulta` AS `Id`, `dataConsulta` AS `Data`, `horario` AS `Horas`, `especialidadeCon` AS `Especialidade`, `pacienteId` AS `Id do Paciente`, `nomePaciente` AS `Nome do Paciente`, `medicoId` AS `Id do Médico`, `nomeMedico` AS `Nome do Medico`, `precoEspecialidade` AS `Valor`,`concluidoConsulta` AS `Concluído` FROM `TbAgenConsulta` where dataConsulta like ?";
                try {
                    pst = conexao.prepareStatement(sqlD);
                    pst.setString(1, txtConPesquisar.getText() + "%");
                    rs = pst.executeQuery();
                    tblConsulta.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                String sqlH = "SELECT `idConsulta` AS `Id`, `dataConsulta` AS `Data`, `horario` AS `Horas`, `especialidadeCon` AS `Especialidade`, `pacienteId` AS `Id do Paciente`, `nomePaciente` AS `Nome do Paciente`, `medicoId` AS `Id do Médico`, `nomeMedico` AS `Nome do Medico`, `precoEspecialidade` AS `Valor`,`concluidoConsulta` AS `Concluído` FROM `TbAgenConsulta` where horario like ?";
                try {
                    pst = conexao.prepareStatement(sqlH);
                    pst.setString(1, txtConPesquisar.getText() + "%");
                    rs = pst.executeQuery();
                    tblConsulta.setModel(DbUtils.resultSetToTableModel(rs));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        } catch (HeadlessException e) {
        }
    }

    public void setar_campos() {
        int setar = tblConsulta.getSelectedRow();
        txtConId.setText(tblConsulta.getModel().getValueAt(setar, 0).toString());
        Date dataDC = (Date) tblConsulta.getModel().getValueAt(setar, 1);
        txtConDC.setDate(dataDC);
        String hora = tblConsulta.getModel().getValueAt(setar, 2).toString();
        if (hora.equals("08:00 / 09:00")) {
            cboConHor.setSelectedIndex(1);
        } else if (hora.equals("09:00 /10:00")) {
            cboConHor.setSelectedIndex(2);
        } else if (hora.equals("10:00 /11:00")) {
            cboConHor.setSelectedIndex(3);
        } else if (hora.equals("11:00 /12:00")) {
            cboConHor.setSelectedIndex(4);
        } else if (hora.equals("12:00 /13:00")) {
            cboConHor.setSelectedIndex(5);
        } else if (hora.equals("13:00 /14:00")) {
            cboConHor.setSelectedIndex(6);
        } else if (hora.equals("14:00 /15:00")) {
            cboConHor.setSelectedIndex(7);
        } else {
            cboConHor.setSelectedIndex(8);
        }
        txtConEsp.setText(tblConsulta.getModel().getValueAt(setar, 3).toString());
        txtConIP.setText(tblConsulta.getModel().getValueAt(setar, 4).toString());
        txtConNomPac.setText(tblConsulta.getModel().getValueAt(setar, 5).toString());
        txtConIM.setText(tblConsulta.getModel().getValueAt(setar, 6).toString());
        txtConNomMed.setText(tblConsulta.getModel().getValueAt(setar, 7).toString());
        txtConValor.setText(tblConsulta.getModel().getValueAt(setar, 8).toString());
        String concluido = tblConsulta.getModel().getValueAt(setar, 9).toString();
        if (concluido.equals("Sim")) {
            cboConCon.setSelectedIndex(1);
        } else {
            cboConCon.setSelectedIndex(2);
        }
        btnAdicionar.setEnabled(false);
    }

    private void alterar() {
        String sql = "update TbAgenConsulta set dataConsulta=?, horario=?, especialidadeCon=?, pacienteId=?, nomePaciente=?, medicoId=?, nomeMedico=?, precoEspecialidade=?, concluidoConsulta=? where IdConsulta=? ";
        java.util.Date dataJCalenDC = txtConDC.getDate();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        String dataFormatadaDC = formatador.format(dataJCalenDC);
        try {
            pst = conexao.prepareStatement(sql);
            String dia = dataFormatadaDC.substring(0, 2);
            String mes = dataFormatadaDC.substring(3, 5);
            String ano = dataFormatadaDC.substring(6);
            String dataMylql = ano + "-" + mes + "-" + dia;
            pst.setString(1, dataMylql);
            pst.setString(2, cboConHor.getSelectedItem().toString());
            pst.setString(3, txtConEsp.getText());
            pst.setString(4, txtConIP.getText());
            pst.setString(5, txtConNomPac.getText());
            pst.setString(6, txtConIM.getText());
            pst.setString(7, txtConNomMed.getText());
            pst.setString(8, txtConValor.getText());
            pst.setString(9, cboConCon.getSelectedItem().toString());
            pst.setString(10, txtConId.getText());
            if ((txtConDC.getDate() == null) || (cboConCon.getSelectedItem().equals("Selecionar")) || (cboConHor.getSelectedItem().equals("Selecionar Horario")) || (txtConEsp.getText().isEmpty()) || (txtConIP.getText().isEmpty()) || (txtConNomPac.getText().isEmpty()) || (txtConIM.getText().isEmpty()) || (txtConNomMed.getText().isEmpty()) || (txtConValor.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados da Consulta alterados com sucesso!");
                    limpar();
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void remover() {
        int confima = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esta Consulta?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confima == JOptionPane.YES_OPTION) {
            String sql = "delete from TbAgenConsulta where idConsulta=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtConId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Consulta removida com sucesso!");
                    limpar();
                    btnAdicionar.setEnabled(true);
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void limpar() {
        txtConId.setText(null);
        txtConDC.setDate(null);
        txtConIP.setText(null);
        txtConEsp.setText(null);
        txtConNomPac.setText(null);
        txtConIM.setText(null);
        txtConNomMed.setText(null);
        txtConValor.setText(null);
        txtExaPesMed.setText(null);
        txtExaPesPac.setText(null);
        txtExaPesExa.setText(null);
        cboConHor.setSelectedIndex(0);
        cboConCon.setSelectedIndex(0);
        ((DefaultTableModel) tblConsulta.getModel()).setRowCount(0);
        ((DefaultTableModel) tblConPesEsp.getModel()).setRowCount(0);
        ((DefaultTableModel) tblConPesMed.getModel()).setRowCount(0);
        ((DefaultTableModel) tblConPesPac.getModel()).setRowCount(0);
    }

    private void consultar_medico() {
        String sql = "select `idMedico` as `Id`, `nome` as `Nome` from `TbMedico` where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtExaPesMed.getText() + "%");
            rs = pst.executeQuery();
            tblConPesMed.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campoMed() {
        int setar = tblConPesMed.getSelectedRow();
        txtConIM.setText(tblConPesMed.getModel().getValueAt(setar, 0).toString());
        txtConNomMed.setText(tblConPesMed.getModel().getValueAt(setar, 1).toString());
    }

    private void consultar_paciente() {
        String sql = "select `idPaciente` as `Id`, `nome` as `Nome` from `TbPaciente` where `nome` like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtExaPesPac.getText() + "%");
            rs = pst.executeQuery();
            tblConPesPac.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campoPac() {
        int setar = tblConPesPac.getSelectedRow();
        txtConIP.setText(tblConPesPac.getModel().getValueAt(setar, 0).toString());
        txtConNomPac.setText(tblConPesPac.getModel().getValueAt(setar, 1).toString());
    }

    private void consultar_especialidade() {
        String sql = "SELECT `especialidade` AS `Especialidade` , `preco` AS `Valor` FROM `TbEspecialidade` WHERE `especialidade` LIKE ? AND `disponivelEspecialidade` = 'sim'";
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
        txtConEsp.setText(tblConPesEsp.getModel().getValueAt(setar, 0).toString());
        txtConValor.setText(tblConPesEsp.getModel().getValueAt(setar, 1).toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtConId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtConIP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtConIM = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtConNomMed = new javax.swing.JTextField();
        txtConNomPac = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cboConHor = new javax.swing.JComboBox<>();
        txtConEsp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cboConCon = new javax.swing.JComboBox<>();
        txtConValor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtConDC = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblConsulta = new javax.swing.JTable();
        txtConPesquisar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtExaPesExa = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblConPesEsp = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txtExaPesPac = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblConPesPac = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        txtExaPesMed = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblConPesMed = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnRemover = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        cboConFilPes = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Agendamentos de Consultas");
        setMaximumSize(new java.awt.Dimension(1020, 560));
        setMinimumSize(new java.awt.Dimension(1020, 560));
        setPreferredSize(new java.awt.Dimension(1020, 560));

        jPanel1.setBackground(new java.awt.Color(234, 236, 239));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(211, 215, 221));
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel2.setText("Id Consulta");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 20, 110, 19);

        txtConId.setEnabled(false);
        jPanel2.add(txtConId);
        txtConId.setBounds(20, 40, 120, 30);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel4.setText("*Data");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(20, 70, 70, 19);

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("*Id Paciente");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(20, 120, 100, 16);

        txtConIP.setEnabled(false);
        jPanel2.add(txtConIP);
        txtConIP.setBounds(20, 140, 120, 30);

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel7.setText("*Id Médico");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 170, 90, 16);

        txtConIM.setEnabled(false);
        jPanel2.add(txtConIM);
        txtConIM.setBounds(20, 190, 120, 30);

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel10.setText("*Médico");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(180, 170, 45, 16);

        txtConNomMed.setEnabled(false);
        jPanel2.add(txtConNomMed);
        txtConNomMed.setBounds(180, 190, 160, 30);

        txtConNomPac.setEnabled(false);
        jPanel2.add(txtConNomPac);
        txtConNomPac.setBounds(180, 140, 160, 30);

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel9.setText("Paciente");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(180, 120, 45, 16);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel8.setText("*Hora");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(180, 70, 31, 16);

        cboConHor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar Horario", "08:00 / 09:00", "09:00 /10:00", "10:00 /11:00 ", "11:00/12:00", "12:00/13:00", "13:00/14:00", "14:00/15:00", "15:00/16:00" }));
        jPanel2.add(cboConHor);
        cboConHor.setBounds(180, 90, 160, 30);

        txtConEsp.setEnabled(false);
        jPanel2.add(txtConEsp);
        txtConEsp.setBounds(180, 40, 160, 30);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("*Especialidade");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(180, 20, 76, 16);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel1.setText("*Concluído");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(180, 220, 60, 20);

        cboConCon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar", "Sim", "Não" }));
        jPanel2.add(cboConCon);
        cboConCon.setBounds(180, 240, 160, 30);

        txtConValor.setEnabled(false);
        jPanel2.add(txtConValor);
        txtConValor.setBounds(20, 240, 120, 30);

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel11.setText("*Valor");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(20, 220, 70, 19);
        jPanel2.add(txtConDC);
        txtConDC.setBounds(20, 90, 120, 30);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 10, 360, 300);

        tblConsulta = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Data", "Hora", "Especialidade", "Id ", "Paciente ", "Id", "Médico", "Valor", "Concluído"
            }
        ));
        tblConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblConsulta);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 370, 980, 140);

        txtConPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConPesquisarActionPerformed(evt);
            }
        });
        txtConPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtConPesquisarKeyPressed(evt);
            }
        });
        jPanel1.add(txtConPesquisar);
        txtConPesquisar.setBounds(70, 330, 480, 30);

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel13.setText("Pesquisar:");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 340, 53, 16);

        jPanel3.setBackground(new java.awt.Color(211, 215, 221));
        jPanel3.setLayout(null);

        txtExaPesExa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtExaPesExaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtExaPesExaKeyReleased(evt);
            }
        });
        jPanel3.add(txtExaPesExa);
        txtExaPesExa.setBounds(10, 30, 180, 30);
        jPanel3.add(jLabel18);
        jLabel18.setBounds(179, 43, 33, 0);

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel19.setText("Pesquisar Especialidade");
        jPanel3.add(jLabel19);
        jLabel19.setBounds(10, 10, 124, 16);

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
                "Especialidade", "Valor"
            }
        ));
        tblConPesEsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConPesEspMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblConPesEsp);

        jPanel3.add(jScrollPane3);
        jScrollPane3.setBounds(10, 70, 180, 150);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(800, 10, 200, 230);

        jPanel4.setBackground(new java.awt.Color(211, 215, 221));
        jPanel4.setLayout(null);

        txtExaPesPac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtExaPesPacKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtExaPesPacKeyReleased(evt);
            }
        });
        jPanel4.add(txtExaPesPac);
        txtExaPesPac.setBounds(10, 30, 180, 30);
        jPanel4.add(jLabel16);
        jLabel16.setBounds(185, 31, 33, 0);

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel17.setText("Pesquisar Paciente");
        jPanel4.add(jLabel17);
        jLabel17.setBounds(10, 10, 98, 16);

        tblConPesPac = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblConPesPac.setModel(new javax.swing.table.DefaultTableModel(
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
        tblConPesPac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConPesPacMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblConPesPac);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(10, 70, 180, 150);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(590, 10, 200, 230);

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtExaPesMedKeyReleased(evt);
            }
        });
        jPanel5.add(txtExaPesMed);
        txtExaPesMed.setBounds(10, 30, 170, 30);

        tblConPesMed = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblConPesMed.setModel(new javax.swing.table.DefaultTableModel(
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
        tblConPesMed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConPesMedMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblConPesMed);

        jPanel5.add(jScrollPane4);
        jScrollPane4.setBounds(10, 70, 170, 150);

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel14.setText("Pesquisar Médico");
        jPanel5.add(jLabel14);
        jLabel14.setBounds(10, 10, 126, 19);
        jPanel5.add(jLabel15);
        jLabel15.setBounds(151, 43, 33, 0);

        jPanel1.add(jPanel5);
        jPanel5.setBounds(390, 10, 190, 230);

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
        btnRemover.setBounds(760, 260, 100, 40);

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
        btnAlterar.setBounds(630, 260, 100, 40);

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
        btnAdicionar.setBounds(500, 260, 100, 40);

        cboConFilPes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Paciente", "Médico", "Especialidade", "Data", "Hora" }));
        cboConFilPes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboConFilPesItemStateChanged(evt);
            }
        });
        jPanel1.add(cboConFilPes);
        cboConFilPes.setBounds(860, 330, 130, 30);

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel6.setText("Pesquisar por:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(770, 340, 80, 16);

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

    private void txtConPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConPesquisarActionPerformed

    private void txtConPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConPesquisarKeyPressed
        pesquisar_Consulta();
    }//GEN-LAST:event_txtConPesquisarKeyPressed

    private void txtExaPesExaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExaPesExaKeyPressed

    }//GEN-LAST:event_txtExaPesExaKeyPressed

    private void tblConPesEspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConPesEspMouseClicked
        setar_campoEsp();
    }//GEN-LAST:event_tblConPesEspMouseClicked

    private void txtExaPesPacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExaPesPacKeyPressed

    }//GEN-LAST:event_txtExaPesPacKeyPressed

    private void tblConPesPacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConPesPacMouseClicked
        setar_campoPac();
    }//GEN-LAST:event_tblConPesPacMouseClicked

    private void txtExaPesMedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExaPesMedMouseClicked

    }//GEN-LAST:event_txtExaPesMedMouseClicked

    private void txtExaPesMedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExaPesMedKeyPressed

    }//GEN-LAST:event_txtExaPesMedKeyPressed

    private void tblConPesMedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConPesMedMouseClicked
        setar_campoMed();
    }//GEN-LAST:event_tblConPesMedMouseClicked

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void tblConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConsultaMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblConsultaMouseClicked

    private void cboConFilPesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboConFilPesItemStateChanged

    }//GEN-LAST:event_cboConFilPesItemStateChanged

    private void txtExaPesExaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExaPesExaKeyReleased
        consultar_especialidade();
    }//GEN-LAST:event_txtExaPesExaKeyReleased

    private void txtExaPesMedKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExaPesMedKeyReleased
        consultar_medico();
    }//GEN-LAST:event_txtExaPesMedKeyReleased

    private void txtExaPesPacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExaPesPacKeyReleased
        consultar_paciente();
    }//GEN-LAST:event_txtExaPesPacKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cboConCon;
    private javax.swing.JComboBox<String> cboConFilPes;
    private javax.swing.JComboBox<String> cboConHor;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblConPesEsp;
    private javax.swing.JTable tblConPesMed;
    private javax.swing.JTable tblConPesPac;
    private javax.swing.JTable tblConsulta;
    private com.toedter.calendar.JDateChooser txtConDC;
    private javax.swing.JTextField txtConEsp;
    private javax.swing.JTextField txtConIM;
    private javax.swing.JTextField txtConIP;
    private javax.swing.JTextField txtConId;
    private javax.swing.JTextField txtConNomMed;
    private javax.swing.JTextField txtConNomPac;
    private javax.swing.JTextField txtConPesquisar;
    private javax.swing.JTextField txtConValor;
    private javax.swing.JTextField txtExaPesExa;
    private javax.swing.JTextField txtExaPesMed;
    private javax.swing.JTextField txtExaPesPac;
    // End of variables declaration//GEN-END:variables
}
