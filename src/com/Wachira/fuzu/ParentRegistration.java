//Michael Wahome Wachira ICS 135360 27/03/2021
package com.Wachira.fuzu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.util.Arrays;

public class ParentRegistration extends JFrame{
    private JPanel pnlParentRegistration;
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JTextField txtAdmNo;
    private JTextField txtUsername;
    private JTextField txtParentF;
    private JTextField txtParentL;
    private JPasswordField pswPassword;
    private JPasswordField pswPassword2;
    private JTextField txtStudentF;
    private JTextField txtStudentL;
    private JLabel lblUsername;
    private JLabel lblParentF;
    private JLabel lblParentL;
    private JLabel lblPassword;
    private JLabel lblPassword2;
    private JLabel lblStudentF;
    private JLabel lblStudentL;
    private JLabel lblAdmNo;
    private JButton btnRegister;
    private JLabel lblPhone;
    private JTextField txtPhone;
    private JLabel lblcopyright;
    private JButton btnhome;

    public ParentRegistration(){
        super("Parent Registration Screen");
        this.setContentPane(pnlParentRegistration);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setVisible(true);
        this.pack();

        btnhome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Index frame = new Index();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to register an account?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite", "wahome", "mw@home02");
                        String s3 = "SELECT * FROM tbl_students WHERE admissionNo = ?";
                        PreparedStatement ps3 = connection.prepareStatement(s3);
                        ps3.setInt(1, Integer.parseInt(txtAdmNo.getText()));
                        ResultSet rs = ps3.executeQuery();
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(null, "This student admission number has already been registered with another student under another parent", "Admission Number Unavailable", JOptionPane.ERROR_MESSAGE);
                            txtAdmNo.setText("");
                            txtAdmNo.grabFocus();
                        } else {
                            String s1 = "INSERT INTO tbl_parents(username,fName,lName,password,phoneNo) VALUES(?,?,?,?,?)";
                            PreparedStatement ps1 = connection.prepareStatement(s1);
                            ps1.setString(1, txtUsername.getText());
                            ps1.setString(2, txtParentF.getText());
                            ps1.setString(3, txtParentL.getText());
                            ps1.setString(4, String.valueOf(pswPassword.getPassword()));
                            ps1.setString(5, txtPhone.getText());
                            ps1.executeUpdate();
                            ps1.close();
                            String s2 = "INSERT INTO tbl_students(admissionNo,fName,lName,parentUsername) VALUES(?,?,?,?)";
                            PreparedStatement ps2 = connection.prepareStatement(s2);
                            ps2.setString(1, txtAdmNo.getText());
                            ps2.setString(2, txtStudentF.getText());
                            ps2.setString(3, txtStudentL.getText());
                            ps2.setString(4, txtUsername.getText());
                            ps2.executeUpdate();
                            ps2.close();
                            connection.close();
                            JOptionPane.showMessageDialog(null, "Registration succesful!", "Registration Successful", JOptionPane.PLAIN_MESSAGE);
                            dispose();
                            String username = txtUsername.getText();
                            ParentMenu2 frame = new ParentMenu2(username);
                        }
                    } catch (NumberFormatException b) {
                        JOptionPane.showMessageDialog(null, "The admission number field cannot be left blank and must be a number", "Blank Admission Number", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception a) {
                        JOptionPane.showMessageDialog(null, "The server is currently unreachable. Please try again later.", "Server Unreachable", JOptionPane.ERROR_MESSAGE);
                        a.printStackTrace();
                    }
                }
            }
        });

        pswPassword2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!(Arrays.equals(pswPassword.getPassword(), pswPassword2.getPassword()))){
                    JOptionPane.showMessageDialog(null,"The re-entered password must match the first one","Password Mismatch",JOptionPane.ERROR_MESSAGE);
                    pswPassword.setText("");
                    pswPassword2.setText("");
                    pswPassword.grabFocus();
                }
            }
        });

        txtUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try{
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite","wahome","mw@home02");
                    String s = "SELECT * FROM tbl_parents WHERE username = ?";
                    PreparedStatement ps = connection.prepareStatement(s);
                    ps.setString(1, txtUsername.getText());
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        JOptionPane.showMessageDialog(null,"This username is already taken","Invalid username",JOptionPane.ERROR_MESSAGE);
                        txtUsername.setText("");
                        txtUsername.grabFocus();
                    }
                    ps.close();
                    connection.close();
                }catch(Exception a){
                    JOptionPane.showMessageDialog(null,"The server is currently unreachable. Please try again later.","Server Unreachable",JOptionPane.ERROR_MESSAGE);
                    a.printStackTrace();
                }
            }
        });
    }
}
