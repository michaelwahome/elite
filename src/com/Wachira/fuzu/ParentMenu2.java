//Michael Wahome Wachira ICS 135360 29/03/2021
package com.Wachira.fuzu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParentMenu2 extends JFrame{
    private JPanel pnlParentMenu2;
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JLabel lblQuestion;
    private JButton btnView;
    private JButton btnRegister;
    private JPanel pnlSignin;
    private JLabel lblcopyright;
    private JButton btnhome;
    private JButton btnReset;
    String username;

    public ParentMenu2(String user) {
        super("Parent Menu");
        this.setContentPane(pnlParentMenu2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setVisible(true);
        this.pack();
        this.username = user;

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

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ViewMarks frame = new ViewMarks(username);
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                StudentRegistration frame = new StudentRegistration(username);
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ParentReset frame = new ParentReset(username);
            }
        });
    }
}
