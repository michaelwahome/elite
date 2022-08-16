//Michael Wahome Wachira ICS 135360 26/03/2021
package com.Wachira.fuzu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Index extends JFrame {
    private JPanel pnlIndex;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JLabel lblQuestion;
    private JButton btnParent;
    private JButton btnTeacher;
    private JPanel pnlWall;
    private JLabel lblcopyright;
    private JButton btnFinance;

    public Index() {
        super("Main menu");
        this.setContentPane(pnlIndex);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setVisible(true);
        this.pack();

        btnParent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ParentMenu frame = new ParentMenu();
            }
        });

        btnTeacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TeacherMenu frame = new TeacherMenu();
            }
        });

        btnFinance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FinanceLogin frame = new FinanceLogin();
            }
        });
    }

}
