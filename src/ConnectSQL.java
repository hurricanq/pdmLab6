import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.sql.*;

public class ConnectSQL {
    static final String SERVER_NAME = "sql.bsite.net\\MSSQL2016";
    static final String DATABASE_NAME = "hurricanq_Lab5";
    static final String USER = "hurricanq_Lab5";
    static final String PASSWORD = "123";

    static final String connectionUrl =
            "jdbc:sqlserver://" + SERVER_NAME + ";"
                    + "databaseName=" + DATABASE_NAME + ";"
                    + "user=" + USER + ";"
                    + "password=" + PASSWORD  + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;";

    public static void closeConnect(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error closing connection: " + e.getMessage(),
                        "Connection Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static String showQuery(String inputQuery) {
        Connection con = null;
        PreparedStatement stmt;
        ResultSet rs;
        String result = "";

        try {
            con = DriverManager.getConnection(connectionUrl);
            String preparedQuery =
                    """
                              SELECT *
                              FROM Student
                              WHERE StudentID = ?
                    """;
            stmt = con.prepareStatement(preparedQuery);
            stmt.setString(1, inputQuery);
            rs = stmt.executeQuery();

            /*
            if (rs == null)
                JOptionPane.showMessageDialog(null,
                        "No results found.",
                        "No Results",
                        JOptionPane.WARNING_MESSAGE);
            else {
                resultTable.setModel(DbUtils.resultSetToTableModel(rs));
                JOptionPane.showMessageDialog(null,
                        "Query executed successfully.",
                        "Successful Query",
                        JOptionPane.INFORMATION_MESSAGE);
            }
             */

            while (rs.next()) {
                result = rs.getString("StudentName");
            }
        } catch (SQLException e) {
            /*
            JOptionPane.showMessageDialog(null,
                    "Error executing query.",
                    "Query Error",
                    JOptionPane.WARNING_MESSAGE);
             */
            throw new RuntimeException(e);
        } finally {
            closeConnect(con);
        }
        return result;
    }

    public static boolean insertStudent(String studentName) {
        Connection con = null;
        PreparedStatement stmt;
        int rs;
        boolean isUpdated = false;

        try {
            con = DriverManager.getConnection(connectionUrl);
            con.setAutoCommit(false);
            String insertString = "INSERT INTO Student (StudentName) VALUES (?)";
            stmt = con.prepareStatement(insertString);
            stmt.setString(1, studentName);
            rs = stmt.executeUpdate();

            if (rs > 0) {
                isUpdated = true;
            }
            con.commit();
        } catch (SQLException e) {
            return isUpdated;
        } finally {
            closeConnect(con);
        }
        return isUpdated;
    }

    public static boolean deleteData(String inputQuery) {
        Connection con = null;
        PreparedStatement stmt;
        int rs;
        boolean isUpdated = false;

        try {
            con = DriverManager.getConnection(connectionUrl);
            con.setAutoCommit(false);
            String deleteString = "DELETE FROM ? WHERE ?";
            stmt = con.prepareStatement(deleteString);
            stmt.setString(1, inputQuery);
            rs = stmt.executeUpdate();

            if (rs > 0) {
                isUpdated = true;
            }
            con.commit();
        } catch (SQLException e) {
            return isUpdated;
        } finally {
            closeConnect(con);
        }
        return isUpdated;
    }

}