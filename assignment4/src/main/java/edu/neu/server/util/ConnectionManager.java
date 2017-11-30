package edu.neu.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

    // User to connect to your database instance. By default, this is "root".
    private static final String USER = "Administrator";
    // Password for the user.
    private static final String PASSWORD = "rdsdbski";
    // URI to your database server. If running on the same machine, then this is "localhost".
    private static final String HOST_NAME = "rdbsdsski.ctdnllbdreyy.us-west-2.rds.amazonaws.com";
    // Port to your database server. By default, this is 3307.
    private static final int PORT = 3306;
    // Name of the MySQL schema that contains your tables.
    private static final String SCHEME = "bsdsskidb";

    /**
     * Get the connection to the database instance.
     */
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        String createTable = "CREATE TABLE IF NOT EXISTS skierdata "
                + "(resort_id INT, "
                + " day_num INT, "
                + " skier_id INT, "
                + " lift_id INT, "
                + " time INT, "
                + " vertical INT)";
        try {
            Properties connectionProperties = new Properties();
            connectionProperties.put("user", USER);
            connectionProperties.put("password", PASSWORD);
            connectionProperties.put("useSSL", "false");
            // Ensure the JDBC driver is loaded by retrieving the runtime Class descriptor.
            // Otherwise, Tomcat may have issues loading libraries in the proper order.
            // One alternative is calling this in the HttpServlet init() override.
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                throw new SQLException(ex);
            }
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + HOST_NAME + ":" +
                    PORT + "/" + SCHEME, connectionProperties);
            statement = connection.createStatement();
            statement.executeUpdate(createTable);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return connection;
    }

    /**
     * Close the connection to the database instance.
     */
    public void closeConnection(Connection connection) throws SQLException {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}

