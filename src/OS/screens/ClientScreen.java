/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package OS.screens;

import java.sql.*;
import OS.dal.DB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Robson47
 */
public class ClientScreen extends javax.swing.JInternalFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private void CreateClient() {
        String sql = "insert into clients(client_name, client_email, client_phone, client_address) values (?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtClientName.getText());
            pst.setString(3, txtClientPhone.getText());
            pst.setString(4, txtClientAddress.getText());
            pst.setString(2, txtClientEmail.getText());

            if (txtClientName.getText().isEmpty() || txtClientPhone.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int info = pst.executeUpdate();
                if (info > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente " + txtClientName.getText() + " foi registrado com sucesso");
                    CleanFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro ao registrar o cliente.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar cliente: " + e);
        }
    }

    private void SearchClient() {
        String sql = "select client_id as ID, client_name as Nome, client_address as Endereço, client_email as Email, client_phone as Telefone from clients where client_name like ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtSearch.getText() + "%");

            rs = pst.executeQuery();

            tblClients.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }

    private void SetField() {
        int set = tblClients.getSelectedRow();

        lblClientId.setText(tblClients.getModel().getValueAt(set, 0).toString());
        txtClientName.setText(tblClients.getModel().getValueAt(set, 1).toString());
        txtClientPhone.setText(tblClients.getModel().getValueAt(set, 4).toString());
        txtClientEmail.setText(tblClients.getModel().getValueAt(set, 3).toString());
        txtClientAddress.setText(tblClients.getModel().getValueAt(set, 2).toString());
    }

    private void UpdateClient() {
        String sql = "update clients set client_name=?, client_phone=?, client_email=?, client_address=? where client_id=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtClientName.getText());
            pst.setString(2, txtClientPhone.getText());
            pst.setString(3, txtClientEmail.getText());
            pst.setString(4, txtClientAddress.getText());
            pst.setString(5, lblClientId.getText());

            if (txtClientName.getText().isEmpty() || txtClientPhone.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int info = pst.executeUpdate();
                if (info > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente " + txtClientName.getText() + " foi editado com sucesso");
                    CleanFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar cliente");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar cliente: " + e);
        }
    }

    private void DeleteClient() {
        int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o cliente " + lblClientId.getText() + " ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "delete from clients where client_id=?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, lblClientId.getText());
                int info = pst.executeUpdate();
                if (info > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente " + txtClientName.getText() + " foi excluído com sucesso");
                    CleanFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir cliente");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir cliente: " + e);
            }
        }
    }

    private void CleanFields() {
        txtSearch.setText("");
        txtClientName.setText("");
        txtClientAddress.setText("");
        txtClientPhone.setText("");
        txtClientEmail.setText("");
        lblClientId.setText("0");
        ((DefaultTableModel) tblClients.getModel()).setRowCount(0);
    }

    /**
     * Creates new form ClientScreen
     */
    public ClientScreen() {
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnClientCreate = new javax.swing.JButton();
        btnClientRead = new javax.swing.JButton();
        btnClientDelete = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtClientName = new javax.swing.JTextField();
        txtClientPhone = new javax.swing.JTextField();
        txtClientAddress = new javax.swing.JTextField();
        txtClientEmail = new javax.swing.JTextField();
        txtSearch = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClients = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        lblClientId = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Clientes");

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("* Nome");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel2.setText("Endereço");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel3.setText("E-mail");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel4.setText("* Telefone");

        btnClientCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OS/icons/addfile.png"))); // NOI18N
        btnClientCreate.setToolTipText("Registrar Cliente");
        btnClientCreate.setPreferredSize(new java.awt.Dimension(96, 96));
        btnClientCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientCreateActionPerformed(evt);
            }
        });

        btnClientRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OS/icons/editfile.png"))); // NOI18N
        btnClientRead.setToolTipText("Editar Cliente");
        btnClientRead.setPreferredSize(new java.awt.Dimension(96, 96));
        btnClientRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientReadActionPerformed(evt);
            }
        });

        btnClientDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OS/icons/deletefile.png"))); // NOI18N
        btnClientDelete.setToolTipText("Deletar Cliente");
        btnClientDelete.setPreferredSize(new java.awt.Dimension(96, 96));
        btnClientDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientDeleteActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setText("* Campo obrigatório");

        txtClientName.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        txtClientPhone.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        txtClientAddress.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        txtClientEmail.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        txtSearch.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OS/icons/search-icon.png"))); // NOI18N

        tblClients = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblClients.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tblClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Endereço", "Email"
            }
        ));
        tblClients.setFocusable(false);
        tblClients.getTableHeader().setReorderingAllowed(false);
        tblClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClients);

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel7.setText("ID: ");

        lblClientId.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblClientId.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)))
                .addGap(0, 51, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel1))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addComponent(txtClientName, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(69, 69, 69)
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblClientId))
                                        .addGroup(layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtClientPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                    .addGap(24, 24, 24)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtClientEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtClientAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(202, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnClientCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(btnClientRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addComponent(btnClientDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtClientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(lblClientId))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtClientPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtClientEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtClientAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClientRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClientDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClientCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        setBounds(0, 0, 733, 700);
    }// </editor-fold>//GEN-END:initComponents

    private void btnClientCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientCreateActionPerformed
        CreateClient();
    }//GEN-LAST:event_btnClientCreateActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        SearchClient();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tblClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientsMouseClicked
        SetField();
    }//GEN-LAST:event_tblClientsMouseClicked

    private void btnClientReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientReadActionPerformed
        UpdateClient();
    }//GEN-LAST:event_btnClientReadActionPerformed

    private void btnClientDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientDeleteActionPerformed
        DeleteClient();
    }//GEN-LAST:event_btnClientDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClientCreate;
    private javax.swing.JButton btnClientDelete;
    private javax.swing.JButton btnClientRead;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClientId;
    private javax.swing.JTable tblClients;
    private javax.swing.JTextField txtClientAddress;
    private javax.swing.JTextField txtClientEmail;
    private javax.swing.JTextField txtClientName;
    private javax.swing.JTextField txtClientPhone;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
