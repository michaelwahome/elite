//Michael Wahome Wachira ICS 135360 27/03/2021
package com.Wachira.fuzu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherMenu extends JFrame{
    private JPanel pnlTeacherMenu;
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JLabel lblQuestion;
    private JButton btnSignin;
    private JButton btnRegister;
    private JButton btnYes;
    private JButton btnNo;
    private JPanel pnlSignin;
    private JPanel pnlButtons;
    private JLabel lblRegister;
    private JLabel lblSignin;
    private JLabel lblcopyright;
    private JButton btnhome;

    public TeacherMenu() {
        super("Teacher Menu");
        this.setContentPane(pnlTeacherMenu);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setVisible(true);
        this.pack();
        btnSignin.setVisible(false);
        btnRegister.setVisible(false);
        lblRegister.setVisible(false);
        lblSignin.setVisible(false);

        btnhome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Index frame = new Index();
            }
        });

        btnYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSignin.setVisible(true);
                btnYes.setVisible(false);
                btnNo.setVisible(false);
                lblQuestion.setVisible(false);
                lblSignin.setVisible(true);
            }
        });

        btnNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRegister.setVisible(true);
                btnYes.setVisible(false);
                btnNo.setVisible(false);
                lblQuestion.setVisible(false);
                lblRegister.setVisible(true);
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TeacherRegistration frame = new TeacherRegistration();
            }
        });

        btnSignin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TeacherLogin frame = new TeacherLogin();
            }
        });
    }
}
