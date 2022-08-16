//Michael Wahome Wachira ICS 135360 29/03/2021
package com.Wachira.fuzu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherMenu2 extends JFrame{
    private JPanel pnlTeacherMenu2;
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JLabel lblQuestion;
    private JButton btnUpdate;
    private JPanel pnlSignin;
    private JLabel lblcopyright;
    private JButton btnhome;
    private JButton btnReset;
    String username;

    public TeacherMenu2(String user) {
        super("Teacher Menu");
        this.setContentPane(pnlTeacherMenu2);
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

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UpdateMarks frame = new UpdateMarks(username);
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TeacherReset frame = new TeacherReset(username);
            }
        });
    }
}
