/**
 * Author: lamlevungan
 * Created date: Wed 15/11/2023
 * Package: StudentManagement
 * File name: StudentManagementApp.java
 */

package StudentManagement;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class StudentManagementApp {
    private static StudentManagementCore db;
    private static JList<String> studentsJList;

    private static void studentsList(Container contentPane) {
        // TITLE
        JPanel titleContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JLabel studentTitle = new JLabel("Students List");
        studentTitle.setHorizontalAlignment(JLabel.CENTER);
        studentTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton refresh = new JButton("Refresh");

        JLabel helper = new JLabel("Click to a student to see full information");
        helper.setHorizontalAlignment(JLabel.CENTER);
        helper.setAlignmentX(Component.CENTER_ALIGNMENT);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<String> rs = db.getListIdAndName();
                    studentsJList.setListData(rs.toArray(new String[0]));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        titleContainer.add(studentTitle);
        titleContainer.add(refresh);

        contentPane.add(titleContainer);
        contentPane.add(helper);
        contentPane.add(Box.createVerticalStrut(10));

        // LIST
        String[] students = {};

        studentsJList = new JList<>(students);
        studentsJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Single-click detected
                    int selectedIndex = studentsJList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        String selectedStudent = studentsJList.getModel().getElementAt(selectedIndex);
                        try {
                            showFullInformation(contentPane, selectedStudent);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(studentsJList);

        // Set some properties for the list
        studentsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        studentsJList.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    private static void operationsList(Container contentPane) {
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("List Operations:");

        JButton addStudent = new JButton("Add New Student");
        addStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddDialog(contentPane);
            }
        });

        JButton updateStudent = new JButton("Update Student");
        updateStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateDialog(contentPane);
            }
        });
        JButton removeStudent = new JButton("Remove Student");
        removeStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteDialog(contentPane);
            }
        });
        JButton importList = new JButton("Import List Student");
        importList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showImportDialog(contentPane);
            }
        });
        JButton exportList = new JButton("Export List Student");
        exportList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExportDialog(contentPane);
            }
        });

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

    private static void showFullInformation(Container contentPane, String info) throws SQLException {
        String infoId = info.split(" - ")[0];
        List<String> rs = db.getFullInformation(Integer.parseInt(infoId));
        JDialog showDeleteDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(contentPane), "Full Information Of  "+infoId, true);
        showDeleteDialog.setLayout(new BorderLayout());

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        // Add your components to the dialog
        for (String data : rs){
            JLabel singleData = new JLabel(data);
            dialogPanel.add(singleData);
        }

        // Show dialog
        showDeleteDialog.add(dialogPanel);

        showDeleteDialog.setLocationRelativeTo(contentPane);
        showDeleteDialog.pack();
        showDeleteDialog.setVisible(true);

    }

    private static void showAddDialog(Container contentPane) {
        JDialog showAddDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(contentPane), "Add Data", true);
        showAddDialog.setLayout(new BorderLayout());

        // Add your components to the dialog
        JLabel id = new JLabel("Id:");
        JTextField idField = new JTextField(16);
        JLabel name = new JLabel("Name:");
        JTextField nameField = new JTextField(16);
        JLabel gpa = new JLabel("GPA:");
        JTextField gpaField = new JTextField(16);
        JLabel image = new JLabel("Image:");
        JTextField imageField = new JTextField(16);
        JLabel address = new JLabel("Address:");
        JTextField addressField = new JTextField(16);
        JLabel notes = new JLabel("Notes:");
        JTextField notesField = new JTextField(16);

        JButton addButton = new JButton("Add");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                float gpa = Float.parseFloat(gpaField.getText());
                String image = imageField.getText();
                String address = addressField.getText();
                String notes = notesField.getText();

                try {
                    db.insertData(id, name, gpa, image, address, notes);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


                showAddDialog.dispose();
            }
        });

        // Add components to inside dialog

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        dialogPanel.add(id);
        dialogPanel.add(idField);
        dialogPanel.add(name);
        dialogPanel.add(nameField);
        dialogPanel.add(gpa);
        dialogPanel.add(gpaField);
        dialogPanel.add(image);
        dialogPanel.add(imageField);
        dialogPanel.add(address);
        dialogPanel.add(addressField);
        dialogPanel.add(notes);
        dialogPanel.add(notesField);

        dialogPanel.add(addButton);

        // Show dialog
        showAddDialog.add(dialogPanel, BorderLayout.CENTER);

        showAddDialog.setLocationRelativeTo(contentPane);
        showAddDialog.pack();
        showAddDialog.setVisible(true);
    }
    private static void showUpdateDialog(Container contentPane) {
        JDialog showUpdateDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(contentPane), "Update Data", true);
        showUpdateDialog.setLayout(new BorderLayout());

        // Add your components to the dialog
        JLabel id = new JLabel("Id:");
        JTextField idField = new JTextField(16);
        JLabel name = new JLabel("Name:");
        JTextField nameField = new JTextField(16);
        JLabel gpa = new JLabel("GPA:");
        JTextField gpaField = new JTextField(16);
        JLabel image = new JLabel("Image:");
        JTextField imageField = new JTextField(16);
        JLabel address = new JLabel("Address:");
        JTextField addressField = new JTextField(16);
        JLabel notes = new JLabel("Notes:");
        JTextField notesField = new JTextField(16);

        JButton updateButton = new JButton("Edit");

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                float gpa = Float.parseFloat(gpaField.getText());
                String image = imageField.getText();
                String address = addressField.getText();
                String notes = notesField.getText();

                try {
                    db.editData(id, name, gpa, image, address, notes);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                showUpdateDialog.dispose();
            }
        });

        // Add components to inside dialog

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        dialogPanel.add(id);
        dialogPanel.add(idField);
        dialogPanel.add(name);
        dialogPanel.add(nameField);
        dialogPanel.add(gpa);
        dialogPanel.add(gpaField);
        dialogPanel.add(image);
        dialogPanel.add(imageField);
        dialogPanel.add(address);
        dialogPanel.add(addressField);
        dialogPanel.add(notes);
        dialogPanel.add(notesField);

        dialogPanel.add(updateButton);

        // Show dialog
        showUpdateDialog.add(dialogPanel, BorderLayout.CENTER);

        showUpdateDialog.setLocationRelativeTo(contentPane);
        showUpdateDialog.pack();
        showUpdateDialog.setVisible(true);
    }

    private static void showDeleteDialog(Container contentPane) {
        JDialog showDeleteDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(contentPane), "Delete Data", true);
        showDeleteDialog.setLayout(new BorderLayout());

        // Add your components to the dialog
        JLabel id = new JLabel("Id:");
        JTextField idField = new JTextField(16);

        JButton addButton = new JButton("Delete");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());

                try {
                    db.deleteData(id);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                showDeleteDialog.dispose();
            }
        });

        // Add components to inside dialog

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new FlowLayout());
        dialogPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        dialogPanel.add(id);
        dialogPanel.add(idField);

        dialogPanel.add(addButton);

        // Show dialog
        showDeleteDialog.add(dialogPanel);

        showDeleteDialog.setLocationRelativeTo(contentPane);
        showDeleteDialog.pack();
        showDeleteDialog.setVisible(true);
    }

    private static void showImportDialog(Container contentPane) {
        JDialog importDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(contentPane), "Import Data", true);
        importDialog.setLayout(new BorderLayout());

        // Add your components to the dialog
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importDialog.dispose();
            }
        });

        JLabel title = new JLabel("Enter data file name:");
        JTextField filePathField = new JTextField(16); // width = 16 cols

        JButton importButton = new JButton("Import");

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = filePathField.getText();

                try {
                    db.importData(fileName);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


                importDialog.dispose();
            }
        });

        // Add components to inside dialog

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        dialogPanel.add(title);
        dialogPanel.add(filePathField);
        dialogPanel.add(importButton);
        dialogPanel.add(closeButton);

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        filePathField.setAlignmentX(Component.CENTER_ALIGNMENT);
        importButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Show dialog
        importDialog.add(dialogPanel, BorderLayout.CENTER);

        importDialog.setLocationRelativeTo(contentPane);
        importDialog.pack();
        importDialog.setVisible(true);
    }

    private static void showExportDialog(Container contentPane) {
        JDialog exportDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(contentPane), "Export Data", true);
        exportDialog.setLayout(new BorderLayout());

        // Add your components to the dialog
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportDialog.dispose();
            }
        });

        JLabel title = new JLabel("Enter file name you want to export data to:");
        JTextField filePathField = new JTextField(16); // width = 16 cols
        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = filePathField.getText();

                try {
                    db.exportData(fileName);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                exportDialog.dispose();
            }
        });
        // Add components to inside dialog

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        dialogPanel.add(title);
        dialogPanel.add(filePathField);
        dialogPanel.add(exportButton);
        dialogPanel.add(closeButton);

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        filePathField.setAlignmentX(Component.CENTER_ALIGNMENT);
        exportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Show dialog
        exportDialog.add(dialogPanel, BorderLayout.CENTER);

        exportDialog.setLocationRelativeTo(contentPane);
        exportDialog.pack();
        exportDialog.setVisible(true);
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
        centerPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        // Default size
//        Dimension preferredSize = new Dimension(300, centerPanel.getPreferredSize().height);
//        centerPanel.setPreferredSize(preferredSize);

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

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        db = new StudentManagementCore();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }
}