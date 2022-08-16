//Michael Wahome Wachira ICS 135360 27/03/2021
package com.Wachira.fuzu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class ParentLogin extends JFrame{
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JPanel pnlParentLogin;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JPasswordField pswPassword;
    private JLabel lblPassword;
    private JButton btnSignin;
    private JLabel lblcopyright;
    private JButton btnhome;
    private int loginCount = 0;

    public ParentLogin(){
        super("Parent Sign-in Screen");
        this.setContentPane(pnlParentLogin);
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

        btnSignin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fuzu","wahome","mw@home02");
                    String s = "SELECT * FROM tbl_parents WHERE username = ? AND password = ?";
                    PreparedStatement ps = connection.prepareStatement(s);
                    ps.setString(1, txtUsername.getText());
                    ps.setString(2, String.valueOf(pswPassword.getPassword()));
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        JOptionPane.showMessageDialog(null,"Login successful!","Login Successful",JOptionPane.PLAIN_MESSAGE);
                        dispose();
                        String username = txtUsername.getText();
                        ParentMenu2 frame = new ParentMenu2(username);
                    }else if (loginCount >1) {
                        JOptionPane.showMessageDialog(null,"There have been too many login attempts(3). You will be redirected to the password reset screen","Login Failed",JOptionPane.ERROR_MESSAGE);
                        dispose();
                        String username = txtUsername.getText();
                        ParentReset frame = new ParentReset(username);
                    }else{
                        JOptionPane.showMessageDialog(null,"The password is incorrect. Please try again","Login Failed",JOptionPane.ERROR_MESSAGE);
                        pswPassword.setText("");
                        pswPassword.grabFocus();
                        loginCount = loginCount+1;
                    }
                    ps.close();
                    connection.close();
                }catch(Exception a){
                    JOptionPane.showMessageDialog(null,"The server is currently unreachable. Please try again later.","Server Unreachable",JOptionPane.ERROR_MESSAGE);
                    a.printStackTrace();
                }
            }
        });

        txtUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try{
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fuzu","wahome","mw@home02");
                    String s = "SELECT * FROM tbl_parents WHERE username = ?";
                    PreparedStatement ps = connection.prepareStatement(s);
                    ps.setString(1, txtUsername.getText());
                    ResultSet rs = ps.executeQuery();
                    if(!(rs.next())){
                        JOptionPane.showMessageDialog(null,"This user does not exist. Do not leave this field blank","Invalid credentials",JOptionPane.ERROR_MESSAGE);
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
