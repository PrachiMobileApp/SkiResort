package edu.neu.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

    // User to connect to your database instance. By default, this is "root".
    private static final String USER = "Administrator";
    // Password for the user.
    private static final String PASSWORD = "<your-password>";
    // URI to your database server. If running on the same machine, then this is "localhost".
    private static final String HOST_NAME = "mysql-instance.czpsrugswfv0.us-west-2.rds.amazonaws.com";
    // Port to your database server. By default, this is 3307.
    private static final int PORT = 3306;
    // Name of the MySQL schema that contains your tables.
    private static final String SCHEME = "skier";

    /**
     * Get the connection to the database instance.
     */
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Properties connectionProperties = new Properties();
            connectionProperties.put("user", this.USER);
            connectionProperties.put("password", this.PASSWORD);
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
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + this.HOST_NAME + ":" +
                    this.PORT + "/" + this.SCHEME, connectionProperties);
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

