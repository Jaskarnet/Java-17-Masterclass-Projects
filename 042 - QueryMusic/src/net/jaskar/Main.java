package net.jaskar;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
                props.load(Files.newInputStream(Path.of("music.properties"),
                        StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));


        // The 'LIMIT' clause in the SQL query is specific to MySQL and is not part of the ANSI SQL standard,
        // which aims to provide vendor-independent SQL functionality.
        // To ensure broader compatibility, an alternative approach is to limit the result set directly
        // through the data source, which behaves similarly across databases.
        try {
            dataSource.setMaxRows(10); // Limits the result set to 10 rows at the data source level
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String query = "SELECT * FROM music.artists LIMIT 10"; // MySQL-specific query

        try (var connection = dataSource.getConnection(
                props.getProperty("user"),
                System.getenv("MYSQL_PASS"));
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(query);

            var meta = resultSet.getMetaData();

            System.out.println("================================");

            for (int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.printf("%-15s", meta.getColumnName(i).toUpperCase());
            }
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    System.out.printf("%-15s", resultSet.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
