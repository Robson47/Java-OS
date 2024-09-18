/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package OS.screens;

import OS.dal.DB;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import OS.screens.AboutScreen;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;

/**
 *
 * @author Robson47
 */
public class MainScreen extends javax.swing.JFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form MainScreen
     */
    public MainScreen() {
        initComponents();
        conn = DB.connector();
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

            Paragraph titulo = new Paragraph(new Phrase(20F, "Relatório de Ordem de Serviço & Orçamento"));
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

            String sql = "select os.os_id, date_format(os.data_os, '%d/%m/%Y - %H:%i'), os.service_status, os.os_type, os.equipment, os.defect, os.service, os.service_provider, os.os_value, c.client_name " +
             "from service_order os " +
             "join clients c ON os.client_id = c.client_id";
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

    private void PrintClients() {
        Document document = new Document(PageSize.A4);
        document.setMargins(40f, 40f, 40f, 40f);

        try {
            PdfWriter.getInstance(document, new FileOutputStream("Clientes.pdf"));
            document.open();

            Date data = new Date();
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
            document.add(new Paragraph(formatador.format(data)));

            Paragraph titulo = new Paragraph(new Phrase(20F, "Lista de Clientes"));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("  "));

            PdfPTable tabela = new PdfPTable(new float[]{1f, 2.5f, 2.5f, 2.5f, 2.5f});
            tabela.setWidthPercentage(100);

            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font fontContent = new Font(Font.FontFamily.HELVETICA, 10);

            String[] colunas = {"ID", "Nome", "Endereço", "Email", "Telefone"};
            PdfPCell header;

            for (String coluna : colunas) {
                header = new PdfPCell(new Paragraph(coluna, fontHeader));
                header.setHorizontalAlignment(Element.ALIGN_LEFT);
                header.setPadding(5f);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                tabela.addCell(header);
            }

            String sql = "select * from clients";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                PdfPCell cell;

                for (int i = 1; i <= 5; i++) {
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
            Desktop.getDesktop().open(new File("Clientes.pdf"));
        } catch (Exception e2) {
            System.out.println(e2);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop = new javax.swing.JDesktopPane();
        lblUser = new javax.swing.JLabel();
        lblData = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuCad = new javax.swing.JMenu();
        menuCadCli = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuCadUsu = new javax.swing.JMenuItem();
        menuRel = new javax.swing.JMenu();
        menuRelServ = new javax.swing.JMenuItem();
        menuRelCli = new javax.swing.JMenuItem();
        menuAju = new javax.swing.JMenu();
        menuAjuSobre = new javax.swing.JMenuItem();
        menuOpc = new javax.swing.JMenu();
        menuOpcSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Ordem de Serviço");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 733, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );

        lblUser.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblUser.setText("Usuário");

        lblData.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblData.setText("Data");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OS/icons/logo-icon.png"))); // NOI18N

        menuCad.setText("Cadastro");
        menuCad.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        menuCadCli.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuCadCli.setText("Clientes");
        menuCadCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCadCliActionPerformed(evt);
            }
        });
        menuCad.add(menuCadCli);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem2.setText("OS");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menuCad.add(jMenuItem2);

        menuCadUsu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuCadUsu.setText("Usuários");
        menuCadUsu.setEnabled(false);
        menuCadUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCadUsuActionPerformed(evt);
            }
        });
        menuCad.add(menuCadUsu);

        jMenuBar1.add(menuCad);

        menuRel.setText("Relatório");
        menuRel.setEnabled(false);
        menuRel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        menuRel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRelActionPerformed(evt);
            }
        });

        menuRelServ.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuRelServ.setText("Serviços");
        menuRelServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRelServActionPerformed(evt);
            }
        });
        menuRel.add(menuRelServ);

        menuRelCli.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuRelCli.setText("Clientes");
        menuRelCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRelCliActionPerformed(evt);
            }
        });
        menuRel.add(menuRelCli);

        jMenuBar1.add(menuRel);

        menuAju.setText("Ajuda");
        menuAju.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        menuAjuSobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuAjuSobre.setText("Sobre");
        menuAjuSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAjuSobreActionPerformed(evt);
            }
        });
        menuAju.add(menuAjuSobre);

        jMenuBar1.add(menuAju);

        menuOpc.setText("Opções");
        menuOpc.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        menuOpcSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuOpcSair.setText("Sair");
        menuOpcSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpcSairActionPerformed(evt);
            }
        });
        menuOpc.add(menuOpcSair);

        jMenuBar1.add(menuOpc);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblData)
                            .addComponent(lblUser)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel3)))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktop)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(lblUser)
                .addGap(45, 45, 45)
                .addComponent(lblData)
                .addGap(134, 134, 134)
                .addComponent(jLabel3)
                .addContainerGap(309, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1024, 768));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        //Informações da tela principal
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        lblData.setText(formatador.format(data));
    }//GEN-LAST:event_formWindowActivated

    private void menuOpcSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpcSairActionPerformed
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_menuOpcSairActionPerformed

    private void menuAjuSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAjuSobreActionPerformed
        AboutScreen about = new AboutScreen();
        about.setVisible(true);
    }//GEN-LAST:event_menuAjuSobreActionPerformed

    private void menuCadUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCadUsuActionPerformed
        UserScreen userscreen = new UserScreen();
        userscreen.setVisible(true);
        desktop.add(userscreen);
    }//GEN-LAST:event_menuCadUsuActionPerformed

    private void menuCadCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCadCliActionPerformed
        ClientScreen clientscreen = new ClientScreen();
        clientscreen.setVisible(true);
        desktop.add(clientscreen);
    }//GEN-LAST:event_menuCadCliActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        OsScreen osscreen = new OsScreen();
        osscreen.setVisible(true);
        desktop.add(osscreen);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void menuRelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRelActionPerformed

    }//GEN-LAST:event_menuRelActionPerformed

    private void menuRelCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRelCliActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja exibir este relatório?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            PrintClients();
        }
    }//GEN-LAST:event_menuRelCliActionPerformed

    private void menuRelServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRelServActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja exibir este relatório?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            PrintOS();
        }
    }//GEN-LAST:event_menuRelServActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JLabel lblData;
    public static javax.swing.JLabel lblUser;
    private javax.swing.JMenu menuAju;
    private javax.swing.JMenuItem menuAjuSobre;
    private javax.swing.JMenu menuCad;
    private javax.swing.JMenuItem menuCadCli;
    public static javax.swing.JMenuItem menuCadUsu;
    private javax.swing.JMenu menuOpc;
    private javax.swing.JMenuItem menuOpcSair;
    public static javax.swing.JMenu menuRel;
    private javax.swing.JMenuItem menuRelCli;
    private javax.swing.JMenuItem menuRelServ;
    // End of variables declaration//GEN-END:variables
}
