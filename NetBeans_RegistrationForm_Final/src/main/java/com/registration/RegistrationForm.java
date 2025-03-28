
package com.registration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegistrationForm extends JFrame {
    private JTextField nameField, mobileField, addressField;
    private JRadioButton maleButton, femaleButton;
    private JComboBox<String> dobDay, dobMonth, dobYear;
    private JCheckBox termsCheckBox;
    private JButton submitButton, resetButton;
    private JTextArea displayArea;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Mobile:"));
        mobileField = new JTextField(20);
        add(mobileField);

        add(new JLabel("Gender:"));
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        add(maleButton);
        add(femaleButton);

        add(new JLabel("DOB:"));
        String[] days = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
        dobDay = new JComboBox<>(days);
        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        dobMonth = new JComboBox<>(months);
        String[] years = { "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005" };
        dobYear = new JComboBox<>(years);
        add(dobDay);
        add(dobMonth);
        add(dobYear);

        add(new JLabel("Address:"));
        addressField = new JTextField(20);
        add(addressField);

        termsCheckBox = new JCheckBox("Accept Terms and Conditions");
        add(termsCheckBox);

        submitButton = new JButton("Submit");
        resetButton = new JButton("Reset");
        add(submitButton);
        add(resetButton);

        displayArea = new JTextArea(10, 40);
        add(displayArea);

        submitButton.addActionListener(e -> submitForm());
        resetButton.addActionListener(e -> resetForm());

        setVisible(true);
    }

    private void submitForm() {
        if (!termsCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please accept the terms and conditions");
            return;
        }

        String name = nameField.getText();
        String mobile = mobileField.getText();
        String gender = maleButton.isSelected() ? "Male" : "Female";
        String dob = dobDay.getSelectedItem() + "-" + dobMonth.getSelectedItem() + "-" + dobYear.getSelectedItem();
        String address = addressField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db/users.db")) {
            String sql = "INSERT INTO users (name, mobile, gender, dob, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, mobile);
            pstmt.setString(3, gender);
            pstmt.setString(4, dob);
            pstmt.setString(5, address);
            pstmt.executeUpdate();
            displayArea.setText("Data inserted successfully!\n");
        } catch (Exception ex) {
            displayArea.setText("Error: " + ex.getMessage());
        }
    }

    private void resetForm() {
        nameField.setText("");
        mobileField.setText("");
        genderGroup.clearSelection();
        dobDay.setSelectedIndex(0);
        dobMonth.setSelectedIndex(0);
        dobYear.setSelectedIndex(0);
        addressField.setText("");
        termsCheckBox.setSelected(false);
    }

    public static void main(String[] args) {
        new RegistrationForm();
    }
}
