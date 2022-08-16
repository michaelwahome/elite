//Michael Wahome Wachira ICS 135360 27/03/2021
package com.Wachira.fuzu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.util.Arrays;

public class TeacherRegistration extends JFrame{
    private JPanel pnlTeacherRegistration;
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JTextField txtUsername;
    private JTextField txtFirst;
    private JTextField txtLast;
    private JPasswordField pswPassword;
    private JPasswordField pswPassword2;
    private JTextField txtPhone;
    private JLabel lblUsername;
    private JLabel lblFirst;
    private JLabel lblLast;
    private JLabel lblPassword;
    private JLabel lblPassword2;
    private JLabel lblPhone;
    private JButton btnRegister;
    private JLabel lblCopyright;
    private JButton btnhome;

    public TeacherRegistration(){
        super("Teacher Registration Screen");
        this.setContentPane(pnlTeacherRegistration);
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
                        String s = "INSERT INTO tbl_teachers(username,fName,lName,password,phoneNo) VALUES(?,?,?,?,?)";
                        PreparedStatement ps = connection.prepareStatement(s);
                        ps.setString(1, txtUsername.getText());
                        ps.setString(2, txtFirst.getText());
                        ps.setString(3, txtLast.getText());
                        ps.setString(4, String.valueOf(pswPassword.getPassword()));
                        ps.setString(5, txtPhone.getText());
                        ps.executeUpdate();
                        ps.close();
                        connection.close();
                        JOptionPane.showMessageDialog(null, "Registration succesful!", "Registration Successful", JOptionPane.PLAIN_MESSAGE);
                        dispose();
                        String username = txtUsername.getText();
                        TeacherMenu2 frame = new TeacherMenu2(username);
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
                    String s = "SELECT * FROM tbl_teachers WHERE username = ?";
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
