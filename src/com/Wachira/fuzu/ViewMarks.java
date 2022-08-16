//Michael Wahome Wachira ICS 135360 28/03/2021
package com.Wachira.fuzu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class ViewMarks extends JFrame{
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JLabel lblcopyright;
    private JPanel pnlViewMarks;
    private JButton btnhome;
    private JLabel lblAdmNo;
    private JTextField txtAdmNo;
    private JTextField txtFirst;
    private JLabel lblFirst;
    private JTextField txtMath;
    private JTextField txtEnglish;
    private JLabel lblMath;
    private JLabel lblEnglish;
    private JTextField txtKiswahili;
    private JLabel lblKiswahili;
    private JButton btnView;
    private JTextField txtAverage;
    private JLabel lblAverage;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JButton btnRefresh;
    private JLabel lblDisclaimer;
    private JButton btnMenu;
    private JPanel pnlViewSection;
    private JScrollPane scrTable;
    private JTable tblTable;
    String username;

    public ViewMarks(String user) {
        super("View Marks");
        this.setContentPane(pnlViewMarks);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setVisible(true);
        this.pack();
        this.username = user;
        lblMath.setVisible(false);
        lblEnglish.setVisible(false);
        lblKiswahili.setVisible(false);
        lblAverage.setVisible(false);
        txtMath.setVisible(false);
        txtEnglish.setVisible(false);
        txtKiswahili.setVisible(false);
        txtAverage.setVisible(false);
        btnRefresh.setVisible(false);
        lblUsername.setVisible(false);
        txtUsername.setVisible(false);
        txtUsername.setText(username);

        lblDisclaimer.setText("You can only view the marks of one student at a time");

        DefaultTableModel DefaultTable = (DefaultTableModel)tblTable.getModel();
        DefaultTable.addColumn("Admission No");
        DefaultTable.addColumn("First Name");
        DefaultTable.addColumn("Last Name");
        DefaultTable.addColumn("Total Fees");
        DefaultTable.addColumn("Amount Paid");
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite","wahome","mw@home02");
            String s6 = "SELECT * FROM tbl_students WHERE parentUsername = ?";
            PreparedStatement ps6 = connection.prepareStatement(s6);
            ps6.setString(1, username);
            ResultSet rs6 = ps6.executeQuery();
            while(rs6.next()){
                String data[]= {String.valueOf(rs6.getInt("admissionNo")), rs6.getString("fName"), rs6.getString("lName"), String.valueOf(rs6.getInt("totalFees")), String.valueOf(rs6.getInt("amountPaid"))};
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

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite", "wahome", "mw@home02");
                    String s2 = "SELECT * FROM tbl_students WHERE parentUsername = ? AND admissionNo = ?";
                    PreparedStatement ps2 = connection.prepareStatement(s2);
                    ps2.setString(1, txtUsername.getText());
                    ps2.setInt(2, Integer.parseInt(txtAdmNo.getText()));
                    ResultSet rs2 = ps2.executeQuery();
                    if (!(rs2.next())) {
                        txtAdmNo.setText("");
                        JOptionPane.showMessageDialog(null, "This student is not registered under you. You can only access the marks of your children", "Parent-Student Mismatch", JOptionPane.ERROR_MESSAGE);
                        txtAdmNo.grabFocus();
                    }else {
                        String s5 = "SELECT * FROM tbl_students WHERE admissionNo = ?";
                        PreparedStatement ps5 = connection.prepareStatement(s5);
                        ps5.setInt(1, Integer.parseInt(txtAdmNo.getText()));
                        ResultSet rs5 = ps5.executeQuery();
                        if(rs5.next()) {
                            int total = rs5.getInt("totalFees");
                            int paid = rs5.getInt("amountPaid");
                            int half = 0;
                            half = total/2;
                            if (half > paid) {
                                int balance = half - paid;
                                JOptionPane.showMessageDialog(null, "You have paid less than 50% of your school fees\n You have to top up at least " + balance + "Ksh to view the marks of this student", "Insufficient deposit", JOptionPane.ERROR_MESSAGE);
                                txtAdmNo.setText("");
                                txtAdmNo.grabFocus();
                                txtFirst.setText("");
                            } else {
                                try {
                                    String s = "SELECT * FROM tbl_students WHERE admissionNo = ? AND fName = ?";
                                    PreparedStatement ps = connection.prepareStatement(s);
                                    ps.setInt(1, Integer.parseInt(txtAdmNo.getText()));
                                    ps.setString(2, txtFirst.getText());
                                    ResultSet rs = ps.executeQuery();
                                    if (!(rs.next())) {
                                        JOptionPane.showMessageDialog(null, "The admission number given does not match this student. Do not leave the first name field blank", "Admission Number Mismatch", JOptionPane.ERROR_MESSAGE);
                                        txtFirst.setText("");
                                        txtFirst.grabFocus();
                                    } else {
                                        String s1 = "SELECT * FROM tbl_students WHERE admissionNo = ?";
                                        PreparedStatement ps1 = connection.prepareStatement(s1);
                                        ps1.setInt(1, Integer.parseInt(txtAdmNo.getText()));
                                        ResultSet rs1 = ps1.executeQuery();
                                        if (rs1.next()) {
                                            lblMath.setVisible(true);
                                            lblEnglish.setVisible(true);
                                            lblKiswahili.setVisible(true);
                                            lblAverage.setVisible(true);
                                            txtMath.setVisible(true);
                                            txtEnglish.setVisible(true);
                                            txtKiswahili.setVisible(true);
                                            txtAverage.setVisible(true);
                                            txtMath.setText(String.valueOf(rs1.getInt("mathematics")));
                                            txtEnglish.setText(String.valueOf(rs1.getInt("english")));
                                            txtKiswahili.setText(String.valueOf(rs1.getInt("kiswahili")));
                                            txtAverage.setText(String.valueOf(rs1.getInt("average")));
                                            btnView.setVisible(false);
                                            btnRefresh.setVisible(true);
                                            lblDisclaimer.setText("Note: A 0 may mean that the mark is not yet available.");
                                        }
                                        ps1.close();
                                    }
                                    ps.close();
                                } catch (NumberFormatException b) {
                                    txtAdmNo.grabFocus();
                                    JOptionPane.showMessageDialog(null, "The admission number field cannot be left blank and must be a number", "Blank Admission Number", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "This admission number does not exist. \nThe admission number field cannot be left blank and must be a number", "Invalid Admission Number", JOptionPane.ERROR_MESSAGE);
                            txtAdmNo.setText("");
                            txtAdmNo.grabFocus();
                            txtFirst.setText("");
                        }
                        ps5.close();
                    }
                    connection.close();
                } catch (NumberFormatException b) {
                    txtAdmNo.grabFocus();
                    JOptionPane.showMessageDialog(null, "The admission number field cannot be left blank and must be a number", "Blank Admission Number", JOptionPane.ERROR_MESSAGE);
                } catch (Exception a) {
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
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite","wahome","mw@home02");
                    String s = "SELECT * FROM tbl_students WHERE parentUsername = ?";
                    PreparedStatement ps = connection.prepareStatement(s);
                    ps.setString(1, txtUsername.getText());
                    ResultSet rs = ps.executeQuery();
                    if (!(rs.next())){
                        txtUsername.setText("");
                        JOptionPane.showMessageDialog(null,"There are no students registered under this parent. Do not leave this field blank","Students Unavailable", JOptionPane.ERROR_MESSAGE);
                        txtUsername.grabFocus();
                        txtUsername.setVisible(true);
                        int confirmed = JOptionPane.showConfirmDialog(null,
                                "Would you like to register a student?", "Student Registration",
                                JOptionPane.YES_NO_OPTION);
                        if (confirmed == JOptionPane.YES_OPTION) {
                            StudentRegistration frame = new StudentRegistration(username);
                        }else{
                            ParentMenu2 frame = new ParentMenu2(username);
                        }
                    }
                }catch (Exception a) {
                    JOptionPane.showMessageDialog(null,"The server is currently unreachable. Please try again later.","Server Unreachable",JOptionPane.ERROR_MESSAGE);
                    a.printStackTrace();
                }
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to refresh the page?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose();
                    ViewMarks frame = new ViewMarks(username);
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
