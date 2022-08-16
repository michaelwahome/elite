//Michael Wahome Wachira ICS 135360 06/04/2021
package com.Wachira.fuzu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinanceMenu2 extends JFrame{
    private JPanel pnlFinanceMenu2;
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JLabel lblQuestion;
    private JButton btnUpdate;
    private JPanel pnlSignin;
    private JLabel lblcopyright;
    private JButton btnhome;
    private JButton btnReset;
    private JButton btnTotal;
    String username;

    public FinanceMenu2(String user) {
        super("Finance Menu");
        this.setContentPane(pnlFinanceMenu2);
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
                UpdateAmountPaid frame = new UpdateAmountPaid(username);
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FinanceReset frame = new FinanceReset(username);
            }
        });
        btnTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UpdateTotal frame = new UpdateTotal(username);
            }
        });
    }
}
