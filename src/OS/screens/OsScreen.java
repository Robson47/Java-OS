/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package OS.screens;

import java.sql.*;
import java.util.Date;
import OS.dal.DB;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import javax.swing.*;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Robson47
 */
public class OsScreen extends javax.swing.JInternalFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    String type;

    private void CleanFields() {
        txtValue.setText("");
        type = "Orçamento";
        txtEquip.setText("");
        txtDefect.setText("");
        txtService.setText("");
        txtProvider.setText("");
        lblClientId.setText("");
        txtOsDate.setText("");
        txtOsId.setText("");
        cboStatus.setSelectedItem(" ");
        searchClient.setText("");
        btnOsCreate.setEnabled(true);
        btnOsRead.setEnabled(true);
        searchClient.setEnabled(true);
        tblClients.setVisible(true);
    }

    private void SearchClient() {
        String sql = "select client_id as ID, client_name as Nome, client_phone as Telefone from clients where client_name like ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, searchClient.getText() + "%");

            rs = pst.executeQuery();

            tblClients.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }

    private void CreateOS() {
        String sql = "insert into service_order(service_status, os_type, equipment, defect, service, service_provider, os_value, client_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(2, type);
            pst.setString(1, cboStatus.getSelectedItem().toString());
            pst.setString(3, txtEquip.getText());
            pst.setString(4, txtDefect.getText());
            pst.setString(5, txtService.getText());
            pst.setString(6, txtProvider.getText());
            pst.setString(7, txtValue.getText().replace(",", "."));
            pst.setString(8, lblClientId.getText());

            if (lblClientId.getText().isEmpty() || txtEquip.getText().isEmpty() || txtDefect.getText().isEmpty() || cboStatus.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int info = pst.executeUpdate();
                if (info > 0) {
                    JOptionPane.showMessageDialog(null, type + " foi criado com sucesso");
                    CleanFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro ao registrar " + type);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao emitir ordem de serviço: " + e);
        }
    }

    private void SearchOS() {
        String os_id = JOptionPane.showInputDialog(null, "Insira o Nº da OS", "Pesquisar OS", JOptionPane.QUESTION_MESSAGE);
        String sql = "select os_id, date_format(data_os, \"%d/%m/%Y - %H:%i\"), service_status, os_type, equipment, defect, service, service_provider, os_value, client_id from service_order where os_id=" + os_id;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                txtOsId.setText(rs.getString(1));
                txtOsDate.setText(rs.getString(2));
                cboStatus.setSelectedItem(rs.getString(3));
                String rbtType = rs.getString(4);
                if (rbtType.equals("Orçamento")) {
                    radioBtnOrcamento.setSelected(true);
                    type = "Orçamento";
                } else {
                    radioBtnOS.setSelected(true);
                    type = "Ordem de Serviço";
                }
                txtEquip.setText(rs.getString(5));
                txtDefect.setText(rs.getString(6));
                txtService.setText(rs.getString(7));
                txtProvider.setText(rs.getString(8));
                txtValue.setText(rs.getString(9));
                lblClientId.setText(rs.getString(10));

                btnOsCreate.setEnabled(false);
                searchClient.setEnabled(false);
                tblClients.setVisible(false);
                btnOsPrint.setEnabled(true);
                btnOsDelete.setEnabled(true);
                btnOsEdit.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "OS inexistente.");
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS inválida");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar ordem de serviço: " + e2);
        }
    }

    private void EditOS() {
        String sql = "update service_order set service_status=?, os_type=?, equipment=?, defect=?, service=?, service_provider=?, os_value=? where os_id=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(2, type);
            pst.setString(1, cboStatus.getSelectedItem().toString());
            pst.setString(3, txtEquip.getText());
            pst.setString(4, txtDefect.getText());
            pst.setString(5, txtService.getText());
            pst.setString(6, txtProvider.getText());
            pst.setString(7, txtValue.getText().replace(",", "."));
            pst.setString(8, txtOsId.getText());

            if (txtOsId.getText().isEmpty() || txtEquip.getText().isEmpty() || txtDefect.getText().isEmpty() || cboStatus.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int info = pst.executeUpdate();
                if (info > 0) {
                    JOptionPane.showMessageDialog(null, type + " foi editado com sucesso");
                    CleanFields();
                    btnOsPrint.setEnabled(false);
                    btnOsDelete.setEnabled(false);
                    btnOsEdit.setEnabled(false);

                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar " + type);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar ordem de serviço: " + e);
        }
    }

    private void DeleteOS() {
        int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir " + type + " ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "delete from service_order where os_id=?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtOsId.getText());
                int info = pst.executeUpdate();
                if (info > 0) {
                    JOptionPane.showMessageDialog(null, type + " foi excluído com sucesso");
                    CleanFields();
                    btnOsPrint.setEnabled(false);
                    btnOsDelete.setEnabled(false);
                    btnOsEdit.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir " + type);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir ordem de serviço: " + e);
            }
        }
    }

    private void PrintOS() {
        Document document = new Document(PageSize.A4);
        document.setMargins(40f, 40f, 40f, 40f);

        try {
            PdfWriter.getInstance(document, new FileOutputStream("OS.pdf"));
            document.open();

            Date data = new Date();
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
            document.add(new Paragraph(formatador.format(data)));

            Paragraph titulo = new Paragraph(new Phrase(20F, "Lista de Ordem de Serviço & Orçamento"));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("  "));

            PdfPTable tabela = new PdfPTable(new float[]{1f, 2f, 2.5f, 2.5f, 4f, 3.5f, 3f, 2.5f, 2f, 2.5f});
            tabela.setWidthPercentage(100);

            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font fontContent = new Font(Font.FontFamily.HELVETICA, 8);

            String[] colunas = {"ID", "Data", "Status", "Tipo", "Equipamento", "Defeito", "Serviço", "Profissional", "Valor", "Cliente"};
            PdfPCell header;

            for (String coluna : colunas) {
                header = new PdfPCell(new Paragraph(coluna, fontHeader));
                header.setHorizontalAlignment(Element.ALIGN_LEFT);
                header.setPadding(5f);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                tabela.addCell(header);
            }

            String os_id = txtOsId.getText();
            String sql = "select os.os_id, date_format(os.data_os, '%d/%m/%Y - %H:%i'), os.service_status, os.os_type, os.equipment, os.defect, os.service, os.service_provider, os.os_value, c.client_name "
                    + "from service_order os "
                    + "join clients c on os.client_id = c.client_id "
                    + "where os.os_id=" + os_id;
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                PdfPCell cell;

                for (int i = 1; i <= 10; i++) {
                    cell = new PdfPCell(new Paragraph(rs.getString(i), fontContent));
                    cell.setPadding(5f);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    tabela.addCell(cell);
                }
            }

            document.add(tabela);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            document.close();
        }

        try {
            Desktop.getDesktop().open(new File("OS.pdf"));
        } catch (Exception e2) {
            System.out.println(e2);
        }
    }

    /**
     * Creates new form OsScreen
     */
    public OsScreen() {
        initComponents();
        conn = DB.connector();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OSbtn = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOsId = new javax.swing.JTextField();
        txtOsDate = new javax.swing.JTextField();
        radioBtnOrcamento = new javax.swing.JRadioButton();
        radioBtnOS = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboStatus = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        searchClient = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClients = new javax.swing.JTable();
        lblClientId = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtEquip = new javax.swing.JTextField();
        txtService = new javax.swing.JTextField();
        txtDefect = new javax.swing.JTextField();
        txtProvider = new javax.swing.JTextField();
        txtValue = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnOsRead = new javax.swing.JButton();
        btnOsEdit = new javax.swing.JButton();
        btnOsDelete = new javax.swing.JButton();
        btnOsCreate = new javax.swing.JButton();
        btnOsPrint = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("OS");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Nº OS");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Data");

        txtOsId.setEditable(false);
        txtOsId.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtOsId.setFocusable(false);
        txtOsId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsIdActionPerformed(evt);
            }
        });

        txtOsDate.setEditable(false);
        txtOsDate.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtOsDate.setFocusable(false);
        txtOsDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsDateActionPerformed(evt);
            }
        });

        OSbtn.add(radioBtnOrcamento);
        radioBtnOrcamento.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        radioBtnOrcamento.setText("Orçamento");
        radioBtnOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBtnOrcamentoActionPerformed(evt);
            }
        });

        OSbtn.add(radioBtnOS);
        radioBtnOS.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        radioBtnOS.setText("Ordem de Serviço");
        radioBtnOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBtnOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioBtnOrcamento)
                    .addComponent(jLabel1)
                    .addComponent(txtOsId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioBtnOS)
                    .addComponent(jLabel2)
                    .addComponent(txtOsDate))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOsId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOsDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioBtnOrcamento)
                    .addComponent(radioBtnOS))
                .addGap(19, 19, 19))
        );

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel3.setText("Situação");

        cboStatus.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Na bancada", "Entrega OK", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando Peças", "Abandonado por Cliente", "Retornou" }));

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Cliente");

        searchClient.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        searchClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchClientActionPerformed(evt);
            }
        });
        searchClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchClientKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setText("ID: ");

        tblClients.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tblClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClients);

        lblClientId.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblClientId.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(searchClient, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel5)
                                .addGap(36, 36, 36)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblClientId)))
                        .addContainerGap(163, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(lblClientId)))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel7.setText("* Equipamento");

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel8.setText("* Defeito");

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel9.setText("Serviço");

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel10.setText("Profissional");

        txtEquip.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEquipActionPerformed(evt);
            }
        });

        txtService.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtServiceActionPerformed(evt);
            }
        });

        txtDefect.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtDefect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDefectActionPerformed(evt);
            }
        });

        txtProvider.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtProvider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProviderActionPerformed(evt);
            }
        });

        txtValue.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValueActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel11.setText("Valor Total");

        btnOsRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OS/icons/searchfile.png"))); // NOI18N
        btnOsRead.setToolTipText("Consultar OS");
        btnOsRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsRead.setPreferredSize(new java.awt.Dimension(96, 96));
        btnOsRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsReadActionPerformed(evt);
            }
        });

        btnOsEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OS/icons/editfile.png"))); // NOI18N
        btnOsEdit.setToolTipText("Editar OS");
        btnOsEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsEdit.setEnabled(false);
        btnOsEdit.setPreferredSize(new java.awt.Dimension(96, 96));
        btnOsEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsEditActionPerformed(evt);
            }
        });

        btnOsDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OS/icons/deletefile.png"))); // NOI18N
        btnOsDelete.setToolTipText("Deletar OS");
        btnOsDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsDelete.setEnabled(false);
        btnOsDelete.setPreferredSize(new java.awt.Dimension(96, 96));
        btnOsDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsDeleteActionPerformed(evt);
            }
        });

        btnOsCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OS/icons/addfile.png"))); // NOI18N
        btnOsCreate.setToolTipText("Emitir OS");
        btnOsCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsCreate.setPreferredSize(new java.awt.Dimension(96, 96));
        btnOsCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsCreateActionPerformed(evt);
            }
        });

        btnOsPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OS/icons/pdf-icon.png"))); // NOI18N
        btnOsPrint.setToolTipText("Gerar PDF");
        btnOsPrint.setEnabled(false);
        btnOsPrint.setPreferredSize(new java.awt.Dimension(96, 96));
        btnOsPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnOsCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btnOsRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btnOsEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btnOsDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                                .addComponent(btnOsPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(23, 23, 23))
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtService)
                    .addComponent(txtDefect)
                    .addComponent(txtEquip, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtValue)))
                .addContainerGap(96, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDefect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOsDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsRead, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        setBounds(0, 0, 733, 700);
    }// </editor-fold>//GEN-END:initComponents

    private void txtOsIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsIdActionPerformed

    private void txtOsDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsDateActionPerformed

    private void searchClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchClientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchClientActionPerformed

    private void txtEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEquipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEquipActionPerformed

    private void txtServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtServiceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtServiceActionPerformed

    private void txtDefectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDefectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDefectActionPerformed

    private void txtProviderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProviderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProviderActionPerformed

    private void txtValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValueActionPerformed

    private void btnOsReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsReadActionPerformed
        SearchOS();
    }//GEN-LAST:event_btnOsReadActionPerformed

    private void btnOsEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsEditActionPerformed
        EditOS();
    }//GEN-LAST:event_btnOsEditActionPerformed

    private void btnOsDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsDeleteActionPerformed
        DeleteOS();
    }//GEN-LAST:event_btnOsDeleteActionPerformed

    private void btnOsCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsCreateActionPerformed
        CreateOS();
    }//GEN-LAST:event_btnOsCreateActionPerformed

    private void searchClientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchClientKeyReleased
        SearchClient();
    }//GEN-LAST:event_searchClientKeyReleased

    private void tblClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientsMouseClicked
        int set = tblClients.getSelectedRow();
        lblClientId.setText(tblClients.getModel().getValueAt(set, 0).toString());
    }//GEN-LAST:event_tblClientsMouseClicked

    private void radioBtnOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBtnOrcamentoActionPerformed
        type = "Orçamento";
    }//GEN-LAST:event_radioBtnOrcamentoActionPerformed

    private void radioBtnOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBtnOSActionPerformed
        type = "Ordem de Serviço";
    }//GEN-LAST:event_radioBtnOSActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        radioBtnOrcamento.setSelected(true);
        type = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPrintActionPerformed
        PrintOS();
    }//GEN-LAST:event_btnOsPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup OSbtn;
    private javax.swing.JButton btnOsCreate;
    private javax.swing.JButton btnOsDelete;
    private javax.swing.JButton btnOsEdit;
    private javax.swing.JButton btnOsPrint;
    private javax.swing.JButton btnOsRead;
    private javax.swing.JComboBox<String> cboStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClientId;
    private javax.swing.JRadioButton radioBtnOS;
    private javax.swing.JRadioButton radioBtnOrcamento;
    private javax.swing.JTextField searchClient;
    private javax.swing.JTable tblClients;
    private javax.swing.JTextField txtDefect;
    private javax.swing.JTextField txtEquip;
    private javax.swing.JTextField txtOsDate;
    private javax.swing.JTextField txtOsId;
    private javax.swing.JTextField txtProvider;
    private javax.swing.JTextField txtService;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables
}
