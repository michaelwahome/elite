//Michael Wahome Wachira ICS 135360 06/04/2021
package com.Wachira.fuzu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class UpdateAmountPaid extends JFrame{
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JLabel lblcopyright;
    private JPanel pnlUpdateAmountPaid;
    private JButton btnhome;
    private JLabel lblAdmNo;
    private JTextField txtAdmNo;
    private JTextField txtFirst;
    private JLabel lblFirst;
    private JTextField txtPaid;
    private JLabel lblPaid;
    private JButton btnUpdate;
    private JButton btnMenu;
    private JPanel pnlUpdateSection;
    private JScrollPane scrTable;
    private JTable tblTable;
    String username;

    public UpdateAmountPaid(String user) {
        super("Update Amount Paid");
        this.setContentPane(pnlUpdateAmountPaid);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setVisible(true);
        this.pack();
        this.username = user;

        DefaultTableModel DefaultTable = (DefaultTableModel)tblTable.getModel();
        DefaultTable.addColumn("Admission No");
        DefaultTable.addColumn("First Name");
        DefaultTable.addColumn("Last Name");
        DefaultTable.addColumn("Amount Paid");
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite","wahome","mw@home02");
            String s6 = "SELECT * FROM tbl_students";
            PreparedStatement ps6 = connection.prepareStatement(s6);
            ResultSet rs6 = ps6.executeQuery();
            while(rs6.next()){
                String data[]= {String.valueOf(rs6.getInt("admissionNo")), rs6.getString("fName"), rs6.getString("lName"), String.valueOf(rs6.getInt("amountPaid"))};
                DefaultTable.addRow(data);
            }
            ps6.close();
            connection.close();
        }catch(Exception a){
            JOptionPane.showMessageDialog(null,"The server is currently unreachable. Please try again later.","Server Unreachable",JOptionPane.ERROR_MESSAGE);
            a.printStackTrace();
        }

        btnhome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to log out?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose();
                    Index frame = new Index();
                }
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite","wahome","mw@home02");
                    String s1 = "SELECT * FROM tbl_students WHERE admissionNo = ? AND fName = ?";
                    PreparedStatement ps1 = connection.prepareStatement(s1);
                    ps1.setInt(1, Integer.parseInt(txtAdmNo.getText()));
                    ps1.setString(2, txtFirst.getText());
                    ResultSet rs = ps1.executeQuery();
                    if (!(rs.next())) {
                        JOptionPane.showMessageDialog(null, "The admission number given does not match this student. Do not leave the first name field blank", "Admission Number Mismatch", JOptionPane.ERROR_MESSAGE);
                        txtFirst.setText("");
                        txtFirst.grabFocus();
                    }else{
                        int confirmed = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to update the amount paid by the student?", "Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (confirmed == JOptionPane.YES_OPTION) {
                            try {
                                String s = "UPDATE tbl_students SET amountPaid = ? WHERE admissionNo = ?";
                                PreparedStatement ps = connection.prepareStatement(s);
                                ps.setInt(1, Integer.parseInt(txtPaid.getText()));
                                ps.setInt(2, Integer.parseInt(txtAdmNo.getText()));
                                ps.executeUpdate();
                                ps.close();
                                JOptionPane.showMessageDialog(null, "Update successful!", "Update Successful", JOptionPane.PLAIN_MESSAGE);
                                dispose();
                                UpdateAmountPaid frame = new UpdateAmountPaid(username);
                            }catch(NumberFormatException b){
                                JOptionPane.showMessageDialog(null,"Do not leave the Amount Paid field blank", "Empty Field",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    ps1.close();
                    connection.close();
                }catch(Exception a){
                    JOptionPane.showMessageDialog(null,"The server is currently unreachable. Please try again later.","Server Unreachable",JOptionPane.ERROR_MESSAGE);
                    a.printStackTrace();
                }
            }
        });

        txtAdmNo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try{
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite","wahome","mw@home02");
                    String s = "SELECT * FROM tbl_students WHERE admissionNo = ?";
                    PreparedStatement ps = connection.prepareStatement(s);
                    ps.setInt(1, Integer.parseInt(txtAdmNo.getText()));
                    ResultSet rs = ps.executeQuery();
                    if (!(rs.next())){
                        txtAdmNo.setText("");
                        JOptionPane.showMessageDialog(null,"There are no students registered with this admission number. Do not leave this field blank","Students Unavailable", JOptionPane.ERROR_MESSAGE);
                        txtAdmNo.grabFocus();
                    }
                    ps.close();
                    connection.close();
                }catch(NumberFormatException b){
                    JOptionPane.showMessageDialog(null,"The admission number field cannot be left blank and must be a number","Blank Admission Number",JOptionPane.ERROR_MESSAGE);
                } catch(Exception a){
                    JOptionPane.showMessageDialog(null,"The server is currently unreachable. Please try again later.","Server Unreachable",JOptionPane.ERROR_MESSAGE);
                    a.printStackTrace();
                }
            }
        });


        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FinanceMenu2 frame = new FinanceMenu2(username);
            }
        });
    }

}
