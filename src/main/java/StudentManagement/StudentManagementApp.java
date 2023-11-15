/**
 * Author: lamlevungan
 * Created date: Wed 15/11/2023
 * Package: StudentManagement
 * File name: StudentManagement.java
 */

package StudentManagement;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class StudentManagement {

    private static void importFile(Container contentPane){
        JPanel importPanel = new JPanel(new FlowLayout());

        JLabel title = new JLabel("Enter data file name:");
        JTextField filePathField = new JTextField(16); // width = 16 cols
        JButton importButton = new JButton("Import");

        importPanel.add(title);
        importPanel.add(filePathField);
        importPanel.add(importButton);

        importPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(importPanel);
    }

    private static void studentsList(Container contentPane){
        // TITLE
        JLabel studentTitle = new JLabel("Students List");
        studentTitle.setHorizontalAlignment(JLabel.CENTER);
        studentTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPane.add(studentTitle);
        contentPane.add(Box.createVerticalStrut(10));

        // LIST
        String[] students = {"Student 1", "Student 2", "Student 3", "Student 4", "Student 5", "Student 6"};

        JList<String> studentsJList = new JList<>(students);
        JScrollPane scrollPane = new JScrollPane(studentsJList);

        // Set some properties for the list
        studentsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        studentsJList.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    private static void operationsList(Container contentPane){
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setBorder(new EmptyBorder(16,16,16,16));

        JLabel title = new JLabel("List operations:");

        JButton addStudent = new JButton("Add New Student");
        JButton updateStudent = new JButton("Update Student");
        JButton removeStudent = new JButton("Remove Student");
        JButton importList = new JButton("Import List Student");
        JButton exportList = new JButton("Export List Student");

        buttonContainer.add(title);
        buttonContainer.add(Box.createVerticalStrut(10)); // Space between 2 components
        buttonContainer.add(addStudent);
        buttonContainer.add(updateStudent);
        buttonContainer.add(removeStudent);
        buttonContainer.add(importList);
        buttonContainer.add(exportList);

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        addStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        importList.setAlignmentX(Component.CENTER_ALIGNMENT);
        exportList.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPane.add(buttonContainer, BorderLayout.WEST);
    }

    private static void addComponents(Container contentPane) {
        contentPane.setLayout(new BorderLayout());

        JLabel title = new JLabel("Student Management System");
        title.setHorizontalAlignment(JLabel.CENTER); // Center the text in the label

        // Set font size, bold and padding
        Font titleFont = title.getFont();
        title.setFont(new Font(titleFont.getName(), Font.BOLD, 20));
        title.setBorder(new EmptyBorder(16, 16, 16, 16));

        contentPane.add(title, BorderLayout.NORTH);

        // CENTER
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(16,16,16,16));
        // Default size
        Dimension preferredSize = new Dimension(300, centerPanel.getPreferredSize().height);
        centerPanel.setPreferredSize(preferredSize);

        studentsList(centerPanel);

        contentPane.add(centerPanel, BorderLayout.CENTER);

        // LEFT
        operationsList(contentPane);

        // SOUTH
        JLabel name = new JLabel("Le Vu Ngan Lam - 21127334");
        name.setBorder(new EmptyBorder(16, 16, 16, 16));
        name.setHorizontalAlignment(JLabel.CENTER);
        contentPane.add(name, BorderLayout.SOUTH);
    }

    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame mainFrame = new JFrame("Student Management System");

        // Default size
        mainFrame.setSize(700, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setLocationRelativeTo(null);

        // Set up the content pane.
        addComponents(mainFrame.getContentPane());

        // Pack its components tightly, which means it will resize itself to fit its content
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}