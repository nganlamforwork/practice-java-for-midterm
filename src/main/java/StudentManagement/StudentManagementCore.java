/**
 * Author: lamlevungan
 * Created date: Wed 15/11/2023
 * Package: StudentManagement
 * File name: StudentManagementCore.java
 */

package StudentManagement;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementCore {
    private static Connection con = null;
    private static String databaseName = "StudentDatabase";
    private static String url = "jdbc:mysql://localhost:3306/" + databaseName;

    private static String username = "root";
    private static String password = "12345678";

    StudentManagementCore() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        con = DriverManager.getConnection(url, username, password);
    }

    public static void closeConnection() throws SQLException {
        con.close();
    }

    public static void importData(String fileName) throws SQLException {
        Statement st = con.createStatement();
        try (
                BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = in.readLine(); // Header line
            while ((line = in.readLine()) != null) {
                String[] infoFields = line.split(",");
                // id, name, gpa, image, address, notes
                if (infoFields.length == 6) {
                    int id = Integer.parseInt(infoFields[0]);
                    String name = infoFields[1];
                    double gpa = Double.parseDouble(infoFields[2]);
                    String image = infoFields[3];
                    String address = infoFields[4];
                    String notes = infoFields[5];
                    insertData(id, name, gpa, image, address, notes);
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void insertData(int id, String name, double gpa, String image, String address, String notes) throws SQLException {
        String insertQuery = "INSERT INTO Student (id, name, gpa, image, address, notes) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, gpa);
            preparedStatement.setString(4, image);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, notes);

            preparedStatement.executeUpdate();
        }
    }

    public static void editData(int id, String name, double gpa, String image, String address, String notes) throws SQLException {
        String insertQuery = "UPDATE Student SET name = ?, gpa = ?, image = ?, address = ?, notes = ? where id = ?;";

        try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, gpa);
            preparedStatement.setString(3, image);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, notes);
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
        }
    }
    public static void deleteData(int id) throws SQLException {
        String deleteQuery = "DELETE FROM Student WHERE id = ?;";

        try (PreparedStatement preparedStatement = con.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }
    public static List<String> getListIdAndName() throws SQLException {
        List<String> idAndNameList = new ArrayList<>();
        Statement st = con.createStatement();
        String query = "select id, name from Student";

        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");

            String idAndName = id + " - " + name;
            idAndNameList.add(idAndName);
        }
        return idAndNameList;
    }

    public static List<String> getFullInformation(int infoId) throws SQLException{
        List<String> student = new ArrayList<>();

        String query = "select * from Student where id = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, infoId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double gpa = rs.getDouble("gpa");
                String image = rs.getString("image");
                String address = rs.getString("address");
                String notes = rs.getString("notes");

                student.add("Id: "+id);
                student.add("Name: "+name);
                student.add("GPA: "+gpa);
                student.add("Image: "+image);
                student.add("Address: "+address);
                student.add("Notes: "+notes);
            }
        }

        return student;
    }

    public static void exportData(String fileName) throws SQLException {
        Statement st = con.createStatement();
        String query = "select * from Student";

        ResultSet rs = st.executeQuery(query);

        try (
                BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            // Header line: id, name, gpa, image, address, notes
            out.write("id,name,gpa,image,address,notes\n");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float gpa = rs.getFloat("gpa");
                String image = rs.getString("image");
                String address = rs.getString("address");
                String notes = rs.getString("notes");

                out.write(id + "," + name + "," + gpa + ","
                        + image + "," + address + "," + notes + "\n");
            }
            out.close();
            System.out.println("Export successfully to " + fileName);
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
}