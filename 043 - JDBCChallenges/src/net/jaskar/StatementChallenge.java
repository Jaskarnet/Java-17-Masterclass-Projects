package net.jaskar;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatementChallenge {
    private static String USE_SCHEMA = "USE storefront";

    private static int MYSQL_DB_NOT_FOUND = 1049;

    public static void main(String[] args) {
        var datasource = new MysqlDataSource();
        datasource.setServerName("localhost");
        datasource.setPort(3306);
        datasource.setUser(System.getenv("MYSQLUSER"));
        datasource.setPassword(System.getenv("MYSQLPASS"));
        try {
            datasource.setContinueBatchOnError(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = datasource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println(metaData.getSQLStateType());
            if (!checkSchema(conn)) {
                System.out.println("storefront schema does not exist");
                setUpSchema(conn);
            } else {
//                addOrder(conn, new String[]{"shoes", "shirt", "socks"});
//                deleteOrder(conn, 4);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean checkSchema(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(USE_SCHEMA);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Error Message: " + e.getMessage());

            if (conn.getMetaData().getDatabaseProductName().equals("MySQL") && e.getErrorCode() == MYSQL_DB_NOT_FOUND) {
                return false;
            } else throw e;
        }
        return true;
    }

    private static void setUpSchema(Connection conn) throws SQLException {
        String createSchema = "CREATE SCHEMA storefront";

        String createOrder = """
                CREATE TABLE storefront.order (
                order_id int NOT NULL AUTO_INCREMENT,
                order_date DATETIME NOT NULL,
                PRIMARY KEY (order_id)
                )""";

        String createOrderDetails = """
                CREATE TABLE storefront.order_details (
                order_detail_id int NOT NULL AUTO_INCREMENT,
                item_description text,
                order_id int DEFAULT NULL,
                PRIMARY KEY (order_detail_id),
                KEY FK_ORDERID (order_id),
                CONSTRAINT FK_ORDERID FOREIGN KEY (order_id)
                REFERENCES storefront.order (order_id) ON DELETE CASCADE
                ) """;

        try (Statement statement = conn.createStatement()) {
            System.out.println("Creating storefront Database");
            statement.execute(createSchema);
            if (checkSchema(conn)) {
                statement.execute(createOrder);
                System.out.println("Successfully Created Order");
                statement.execute(createOrderDetails);
                System.out.println("Successfully Created Order Details");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addOrder(Connection conn, String[] items) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTime = LocalDateTime.now().format(dtf);
            String insertOrder = "INSERT INTO storefront.order (order_date) VALUES (%s)"
                    .formatted(statement.enquoteLiteral(dateTime));
            System.out.println(insertOrder);
            statement.execute(insertOrder, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            int orderId = (rs != null && rs.next()) ? rs.getInt(1) : -1;
            System.out.println("order_id = " + orderId);
            for (String item : items) {
                String insertOrderDetail =
                        "INSERT INTO storefront.order_details (item_description, order_id) VALUES (%s, %d)"
                                .formatted(statement.enquoteLiteral(item), orderId);
                System.out.println(insertOrderDetail);
                statement.addBatch(insertOrderDetail);
            }
            // Validate the results
            int[] resultCounts = statement.executeBatch();
            int totalInserted = 0;
            for (int count : resultCounts) {
                totalInserted += count;
            }
            if (totalInserted != items.length) {
                conn.rollback();
                System.out.println("Number of records inserted doesn't equal items received");
            } else {
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // Reminder: When deleting records, consider whether cascading deletes are
    // configured in the database schema. If foreign key constraints are set with
    // ON DELETE CASCADE, child records will automatically be deleted when the
    // parent record is removed. This can simplify delete operations and maintain
    // data integrity, but it's important to ensure that the constraints are
    // correctly defined to avoid unintended data loss.
    private static void deleteOrder(Connection conn, int orderId) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            String deleteOrderDetails = "DELETE FROM storefront.order_details WHERE order_id = %d"
                    .formatted(orderId);
            String deleteOrder = "DELETE FROM storefront.order WHERE order_id = %d"
                    .formatted(orderId);
            statement.addBatch(deleteOrderDetails);
            statement.addBatch(deleteOrder);
            statement.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public static boolean printRecords(ResultSet resultSet) throws SQLException {

        boolean foundData = false;
        var meta = resultSet.getMetaData();

        System.out.println("===================");

        for (int i = 1; i <= meta.getColumnCount(); i++) {
            System.out.printf("%-17s", meta.getColumnName(i).toUpperCase());
        }
        System.out.println();

        while (resultSet.next()) {
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.printf("%-17s", resultSet.getString(i));
            }
            System.out.println();
            foundData = true;
        }
        return foundData;
    }
}
