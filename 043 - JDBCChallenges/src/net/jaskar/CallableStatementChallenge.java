package net.jaskar;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Map;

import static net.jaskar.PreparedStatementChallenge.readCSV;

public class CallableStatementChallenge {
    private static final int ARTIST_COLUMN = 0;
    private static final int ALBUM_COLUMN = 1;
    private static final int SONG_COLUMN = 3;

    public static void main(String[] args) {
        Map<String, Map<String, String>> albums = null;

//        try (var lines = Files.lines(Path.of("NewAlbums.csv"))) {
//            albums = lines.map(s -> s.split(","))
//                    .collect(Collectors.groupingBy(s -> s[ARTIST_COLUMN],
//                            Collectors.groupingBy(s -> s[ALBUM_COLUMN],
//                                    Collectors.mapping(s -> s[SONG_COLUMN],
//                                            Collectors.joining(
//                                                    "\",\"",
//                                                    "[\"",
//                                                    "\"]")))));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        albums.forEach((artist, artistAlbums) -> {
//            artistAlbums.forEach((key, value) -> {
//                System.out.println(key + " : " + value);
//            });
//        });

        var orders = readCSV(Path.of("orders.csv"));

        var dataSource = new MysqlDataSource();

        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
//        dataSource.setDatabaseName("music");
        dataSource.setDatabaseName("storefront");

        try (Connection connection = dataSource.getConnection(
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"));
        ) {
//            CallableStatement cs = connection.prepareCall("CALL music.addAlbumInOutCounts(?,?,?,?)");
//            albums.forEach((artist, artistAlbums) -> artistAlbums.forEach((album, songs) -> {
//                try {
//                    cs.setString(1, artist);
//                    cs.setString(2, album);
//                    cs.setString(3, songs);
//                    cs.setInt(4, 10);
//                    cs.registerOutParameter(4, Types.INTEGER);
//                    cs.execute();
//                    System.out.printf("%d songs were added for %s%n", cs.getInt(4), album);
//                } catch (SQLException e) {
//                    System.err.println(e.getErrorCode() + " " + e.getMessage());
//                }
//            }));

//            String sql = "SELECT * FROM music.albumview WHERE artist_name = ?";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1, "Bob Dylan");
//            ResultSet resultSet = ps.executeQuery();
//            StatementChallenge.printRecords(resultSet);
//
//            CallableStatement csf = connection.prepareCall("{ ? = CALL music.calcAlbumLength(?) }");
//            csf.registerOutParameter(1, Types.DOUBLE);
//
//            albums.forEach((artist, artistAlbums) -> {
//                artistAlbums.keySet().forEach((albumName) -> {
//                    try {
//                        csf.setString(2, albumName);
//                        csf.execute();
//                        double result = csf.getDouble(1);
//                        System.out.printf("Length of %s is %.1f%n", albumName, result);
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//            });

            CallableStatement csAddOrder = connection.prepareCall("CALL storefront.addOrder(?,?,?,?)");
            csAddOrder.registerOutParameter("orderId", Types.INTEGER);
            csAddOrder.registerOutParameter("insertedRecords", Types.INTEGER);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")
                    .withResolverStyle(ResolverStyle.STRICT);

            orders.forEach((o) -> {
                try {
                    LocalDateTime localDateTime = LocalDateTime.parse(o.dateTime(), dtf);
                    Timestamp timestamp = Timestamp.valueOf(localDateTime);
                    csAddOrder.setTimestamp(1, timestamp);
                    csAddOrder.setString(2, o.getDetailsJSON());
                    csAddOrder.execute();
                    System.out.printf("%d records inserted for %d (%s)%n",
                            csAddOrder.getInt("insertedRecords"),
                            csAddOrder.getInt("orderId"),
                            o.dateTime());
                } catch (Exception e) {
                    System.err.printf("Problem with %s : %s%n%n", o.dateTime(), e.getMessage());
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
