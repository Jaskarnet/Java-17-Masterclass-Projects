package net.jaskar;


import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;

record Item(String itemDescription, int quantity){}
record Order(String dateTime, List<Item> items){}

public class Challenge2 {
    public static void main(String[] args) {
        var datasource = new MysqlDataSource();
        datasource.setServerName("localhost");
        datasource.setPort(3306);
        datasource.setUser(System.getenv("MYSQLUSER"));
        datasource.setPassword(System.getenv("MYSQLPASS"));

        List<Order> orders = readCSV(Path.of("Orders.csv"));
        System.out.println(orders);

        try (Connection conn = datasource.getConnection()) {
            addOrders(orders, conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Order> readCSV(Path path) {
        List<Order> orders = new ArrayList<>();
        List<Item> currentItems = new ArrayList<>();
        String currentOrderDateTime = null;
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(",");
                if (line[0].equalsIgnoreCase("order")) {
                    if (!currentItems.isEmpty()) {
                        orders.add(new Order(currentOrderDateTime, currentItems));
                        currentItems = new ArrayList<>();
                    }
                    currentOrderDateTime = line[1];
                } else if (line[0].equalsIgnoreCase("item")) {
                    String itemDescription = line[2];
                    int quantity = Integer.parseInt(line[1]);
                    currentItems.add(new Item(itemDescription, quantity));
                }
            }
            if (!currentItems.isEmpty()) {
                orders.add(new Order(currentOrderDateTime, currentItems));
            }
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    private static void addOrder(Order order, Connection conn,
                                 PreparedStatement addOrder, PreparedStatement addItem) throws SQLException {
        try {
            conn.setAutoCommit(false);
            addOrder.setString(1, order.dateTime());
            addOrder.execute();
            ResultSet rs = addOrder.getGeneratedKeys();
            int orderId = (rs != null && rs.next()) ? rs.getInt(1) : -1;
            System.out.println(orderId);
            for (Item item : order.items()) {
                addItem.setString(1, item.itemDescription());
                addItem.setInt(2, item.quantity());
                addItem.setInt(3, orderId);
                addItem.addBatch();
            }
            addItem.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            System.err.println("Couldn't add order (" + e.getMessage() + "): " + order);

            conn.rollback();
            conn.setAutoCommit(true);
        }
    }

    private static void addOrders(List<Order> orders, Connection conn) {
        String addOrderQuery = "INSERT INTO storefront.order (order_date) VALUES (?)";
        String addItemQuery = "INSERT INTO storefront.order_details (item_description, quantity, order_id) VALUES (?, ?, ?)";
        try (
                PreparedStatement addOrderStatement = conn.prepareStatement(addOrderQuery,
                        Statement.RETURN_GENERATED_KEYS);
                PreparedStatement addItemStatement = conn.prepareStatement(addItemQuery);
        ) {
            for (Order order : orders) {
                addOrder(order, conn, addOrderStatement, addItemStatement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
