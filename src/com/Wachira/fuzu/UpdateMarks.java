//Michael Wahome Wachira ICS 135360 28/03/2021
package com.Wachira.fuzu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

public class UpdateMarks extends JFrame{
    private JPanel pnlWall;
    private JLabel lblFuzu;
    private JLabel lblPrimary;
    private JLabel lblcopyright;
    private JPanel pnlUpdateMarks;
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
    private JButton btnUpdate;
    private JButton btnMenu;
    private JPanel pnlUpdateSection;
    private JScrollPane scrTable;
    private JTable tblTable;
    String username;

    public UpdateMarks(String user) {
        super("Update Marks");
        this.setContentPane(pnlUpdateMarks);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setVisible(true);
        this.pack();
        this.username = user;

        DefaultTableModel DefaultTable = (DefaultTableModel)tblTable.getModel();
        DefaultTable.addColumn("Admission No");
        DefaultTable.addColumn("First Name");
        DefaultTable.addColumn("Last Name");
        DefaultTable.addColumn("Mathematics");
        DefaultTable.addColumn("English");
        DefaultTable.addColumn("Kiswahili");
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite","wahome","mw@home02");
            String s6 = "SELECT * FROM tbl_students";
            PreparedStatement ps6 = connection.prepareStatement(s6);
            ResultSet rs6 = ps6.executeQuery();
            while(rs6.next()){
                String data[]= {String.valueOf(rs6.getInt("admissionNo")), rs6.getString("fName"), rs6.getString("lName"), String.valueOf(rs6.getInt("mathematics")), String.valueOf(rs6.getInt("english")), String.valueOf(rs6.getInt("kiswahili"))};
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

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite","wahome","mw@home02");
                    String s1 = "SELECT * FROM tbl_students WHERE admissionNo = ? AND fName = ?";
                    PreparedStatement ps1 = connection.prepareStatement(s1);
                    ps1.setInt(1, Integer.parseInt(txtAdmNo.getText()));
                    ps1.setString(2, txtFirst.getText());
                    ResultSet rs = ps1.executeQuery();
                    if (!(rs.next())) {
                        JOptionPane.showMessageDialog(null, "The admission number given does not match this student. Do not leave the first name field blank", "Admission Number Mismatch", JOptionPane.ERROR_MESSAGE);
                        txtFirst.setText("");
                        txtFirst.grabFocus();
                    }else{
                        int confirmed = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to enter these marks into the system?", "Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (confirmed == JOptionPane.YES_OPTION) {
                            try {
                                String s = "UPDATE tbl_students SET mathematics = ?, english = ?, kiswahili = ?, average = ? WHERE admissionNo = ?";
                                PreparedStatement ps = connection.prepareStatement(s);
                                ps.setInt(1, Integer.parseInt(txtMath.getText()));
                                ps.setInt(2, Integer.parseInt(txtEnglish.getText()));
                                ps.setInt(3, Integer.parseInt(txtKiswahili.getText()));
                                int average = (Integer.parseInt(txtMath.getText()) + Integer.parseInt(txtEnglish.getText()) + Integer.parseInt(txtKiswahili.getText())) / 3;
                                ps.setInt(4, average);
                                ps.setInt(5, Integer.parseInt(txtAdmNo.getText()));
                                ps.executeUpdate();
                                ps.close();
                                JOptionPane.showMessageDialog(null, "Update successful!", "Update Successful", JOptionPane.PLAIN_MESSAGE);
                                dispose();
                                UpdateMarks frame = new UpdateMarks(username);
                            }catch(NumberFormatException b){
                                JOptionPane.showMessageDialog(null,"All marks must be updated. If no mark is available indicate a 0","Empty Mark Field",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    ps1.close();
                    connection.close();
                }catch(Exception a){
                    JOptionPane.showMessageDialog(null,"The server is currently unreachable. Please try again later.","Server Unreachable",JOptionPane.ERROR_MESSAGE);
                    a.printStackTrace();
                }
            }
        });

        txtAdmNo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try{
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elite","wahome","mw@home02");
                    String s = "SELECT * FROM tbl_students WHERE admissionNo = ?";
                    PreparedStatement ps = connection.prepareStatement(s);
                    ps.setInt(1, Integer.parseInt(txtAdmNo.getText()));
                    ResultSet rs = ps.executeQuery();
                    if (!(rs.next())){
                        txtAdmNo.setText("");
                        JOptionPane.showMessageDialog(null,"There are no students registered with this admission number. Do not leave this field blank","Students Unavailable", JOptionPane.ERROR_MESSAGE);
                        txtAdmNo.grabFocus();
                    }
                    ps.close();
                    connection.close();
                }catch(NumberFormatException b){
                    JOptionPane.showMessageDialog(null,"The admission number field cannot be left blank and must be a number","Blank Admission Number",JOptionPane.ERROR_MESSAGE);
                } catch(Exception a){
                    JOptionPane.showMessageDialog(null,"The server is currently unreachable. Please try again later.","Server Unreachable",JOptionPane.ERROR_MESSAGE);
                    a.printStackTrace();
                }
            }
        });


        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TeacherMenu2 frame = new TeacherMenu2(username);
            }
        });
    }

}
