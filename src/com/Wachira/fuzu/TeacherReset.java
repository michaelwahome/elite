//Michael Wahome Wachira ICS 135360 27/03/2021
package com.Wachira.fuzu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

public class TeacherReset extends JFrame {
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JLabel lblCopyright;
    private JButton btnhome;
    private JLabel lblPassword;
    private JPasswordField pswPassword;
    private JLabel lblPassword2;
    private JPasswordField pswPassword2;
    private JPanel pnlTeacherReset;
    private JButton btnReset;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JLabel lblPhone;
    private JTextField txtPhone;
    String username;

    public TeacherReset(String user) {
        super("Teacher Password Reset Screen");
        this.setContentPane(pnlTeacherReset);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setVisible(true);
        this.pack();
        this.username = user;
        lblUsername.setVisible(false);
        txtUsername.setVisible(false);
        txtUsername.setText(username);

        btnhome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Index frame = new Index();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to reset your password?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    try{
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fuzu", "wahome", "mw@home02");
                        String s3 = "SELECT * FROM tbl_teachers WHERE username = ?";
                        PreparedStatement ps3 = connection.prepareStatement(s3);
                        ps3.setString(1, txtUsername.getText());
                        ResultSet rs3 = ps3.executeQuery();
                        if(!(rs3.next())){
                            JOptionPane.showMessageDialog(null,"This user does not exist. Do not leave this field blank","Invalid credentials",JOptionPane.ERROR_MESSAGE);
                            txtUsername.setText("");
                            txtUsername.grabFocus();
                            txtUsername.setVisible(true);
                        }else {
                            String s1 = "SELECT * FROM tbl_teachers WHERE username = ? AND phoneNo = ?";
                            PreparedStatement ps1 = connection.prepareStatement(s1);
                            ps1.setString(1, txtUsername.getText());
                            ps1.setString(2, txtPhone.getText());
                            ResultSet rs = ps1.executeQuery();
                            if (!(rs.next())) {
                                JOptionPane.showMessageDialog(null, "The username must match the phone number you entered when you registered your account", "Incorrect Phone Number", JOptionPane.ERROR_MESSAGE);
                                txtPhone.setText("");
                                pswPassword.setText("");
                                pswPassword2.setText("");
                                txtPhone.grabFocus();
                            } else if (!(Arrays.equals(pswPassword.getPassword(), pswPassword2.getPassword()))) {
                                JOptionPane.showMessageDialog(null, "The re-entered password must match the first one", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
                                pswPassword.setText("");
                                pswPassword2.setText("");
                                pswPassword.grabFocus();
                            } else {
                                String s = "UPDATE tbl_teachers SET password=? WHERE username = ?";
                                PreparedStatement ps = connection.prepareStatement(s);
                                ps.setString(1, String.valueOf(pswPassword.getPassword()));
                                ps.setString(2,txtUsername.getText());
                                ps.executeUpdate();
                                ps.close();
                                ps1.close();
                                connection.close();
                                JOptionPane.showMessageDialog(null, "Reset succesful!", "Reset Successful", JOptionPane.PLAIN_MESSAGE);
                                dispose();
                                TeacherLogin frame = new TeacherLogin();
                            }
                            ps1.close();
                            connection.close();
                        }
                        ps3.close();
                    }catch(Exception a){
                        JOptionPane.showMessageDialog(null, "The server is currently unreachable. Please try again later.", "Server Unreachable", JOptionPane.ERROR_MESSAGE);
                        a.printStackTrace();
                    }
                }
            }
        });
    }
}
