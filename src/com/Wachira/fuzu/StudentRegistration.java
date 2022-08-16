//Michael Wahome Wachira ICS 135360 29/03/2021
package com.Wachira.fuzu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class StudentRegistration extends JFrame{
    private JPanel pnlStudentRegistration;
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JTextField txtUsername;
    private JTextField txtFirst;
    private JTextField txtLast;
    private JTextField txtAdmNo;
    private JLabel lblUsername;
    private JLabel lblFirst;
    private JLabel lblLast;
    private JLabel lblAdmNo;
    private JButton btnRegister;
    private JLabel lblCopyright;
    private JButton btnhome;
    private JButton btnMenu;
    private JPanel pnlRegistrationSection;
    private JScrollPane scrTable;
    private JTable tblTable;
    String username;

    public StudentRegistration(String user){
        super("Student Registration Screen");
        this.setContentPane(pnlStudentRegistration);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setVisible(true);
        this.pack();
        this.username = user;
        lblUsername.setVisible(false);
        txtUsername.setVisible(false);
        txtUsername.setText(username);

        DefaultTableModel DefaultTable = (DefaultTableModel)tblTable.getModel();
        DefaultTable.addColumn("Admission No");
        DefaultTable.addColumn("First Name");
        DefaultTable.addColumn("Last Name");
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite","wahome","mw@home02");
            String s6 = "SELECT * FROM tbl_students WHERE parentUsername = ?";
            PreparedStatement ps6 = connection.prepareStatement(s6);
            ps6.setString(1, username);
            ResultSet rs6 = ps6.executeQuery();
            while(rs6.next()){
                String data[]= {String.valueOf(rs6.getInt("admissionNo")), rs6.getString("fName"), rs6.getString("lName")};
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

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to register a student?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite", "wahome", "mw@home02");
                        String s1 = "SELECT * FROM tbl_parents WHERE username = ?";
                        PreparedStatement ps1 = connection.prepareStatement(s1);
                        ps1.setString(1, txtUsername.getText());
                        ResultSet rs1 = ps1.executeQuery();
                        if(!(rs1.next())){
                            JOptionPane.showMessageDialog(null,"This user does not exist. Do not leave the username field blank","Invalid username",JOptionPane.ERROR_MESSAGE);
                            txtUsername.setText("");
                            txtUsername.grabFocus();
                            txtUsername.setVisible(true);
                        }
                        else {
                            try {
                                    String s2 = "SELECT * FROM tbl_students WHERE admissionNo = ?";
                                    PreparedStatement ps2 = connection.prepareStatement(s2);
                                    ps2.setInt(1, Integer.parseInt(txtAdmNo.getText()));
                                    ResultSet rs2 = ps2.executeQuery();
                                    if (rs2.next()) {
                                        JOptionPane.showMessageDialog(null, "This admission number is already taken. The admission number field cannot be left blank and must be a number", "Invalid Admission NUmber", JOptionPane.ERROR_MESSAGE);
                                        txtAdmNo.setText("");
                                        txtAdmNo.grabFocus();
                                    } else {
                                        String s = "INSERT INTO tbl_students(parentUsername,fName,lName,admissionNo) VALUES(?,?,?,?)";
                                        PreparedStatement ps = connection.prepareStatement(s);
                                        ps.setString(1, txtUsername.getText());
                                        ps.setString(2, txtFirst.getText());
                                        ps.setString(3, txtLast.getText());
                                        ps.setInt(4, Integer.parseInt(txtAdmNo.getText()));
                                        ps.executeUpdate();
                                        ps.close();
                                        ps2.close();
                                        connection.close();
                                        JOptionPane.showMessageDialog(null, "Registration succesful!", "Registration Successful", JOptionPane.PLAIN_MESSAGE);
                                        dispose();
                                        ParentMenu2 frame = new ParentMenu2(username);
                                    }
                            }catch(NumberFormatException b){
                                JOptionPane.showMessageDialog(null,"The admission number field cannot be left blank and must be a number","Blank Admission Number",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        ps1.close();
                        connection.close();
                    } catch (Exception a) {
                        JOptionPane.showMessageDialog(null, "The server is currently unreachable. Please try again later.", "Server Unreachable", JOptionPane.ERROR_MESSAGE);
                        a.printStackTrace();
                    }
                }
            }
        });

        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ParentMenu2 frame = new ParentMenu2(username);
            }
        });
    }
}
