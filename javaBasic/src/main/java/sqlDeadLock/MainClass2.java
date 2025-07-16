package sqlDeadLock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 *
 * @author mash
 * @date 2025/1/5 下午3:27
 */
public class MainClass2 {

    public static void main(String[] args) throws Exception {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/cm?allowPublicKeyRetrieval=true", "root", "1234qaz.");
        Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/cm?allowPublicKeyRetrieval=true", "root", "1234qaz.");
        Connection con3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/cm?allowPublicKeyRetrieval=true", "root", "1234qaz.");
        Connection con4 = DriverManager.getConnection("jdbc:mysql://localhost:3306/cm?allowPublicKeyRetrieval=true", "root", "1234qaz.");
        Connection con5 = DriverManager.getConnection("jdbc:mysql://localhost:3306/cm?allowPublicKeyRetrieval=true", "root", "1234qaz.");


        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 1; i < 200; i++) {

            System.out.println(i);
            con5.createStatement().executeUpdate("delete from crowd_metadata_info where crowd_id like 'CROWD_2024-12-13%'");

            Future<?> submit2 = executorService.submit(() -> {
                insert2(con2);
            });
            Future<?> submit = executorService.submit(() -> {
                insert1(con1);
            });
//
//            Future<?> submit3 = executorService.submit(() -> {
//                insert3(con3);
//            });
//
//            Future<?> submit4 = executorService.submit(() -> {
//                insert4(con4);
//            });


            submit.get();
            submit2.get();
//            submit3.get();
//            submit4.get();

        }

        executorService.shutdown();

    }

    public static void insert1(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO crowd_metadata_info (app_id, crowd_id, store_type, id_type, file_path, path_config, `count`, user_column, update_time)\n" +
                    "VALUES ('vuVqiPwNbl6FCfHxiTZox4', 'CROWD_2024-12-13_952036327f5b4b2198f4d1f0381ae4fa', 's3', 'gtcid', '', '{\"clientKey\":\"IDO\"}', 0, '', NOW())\n" +
//                    "";
                    "ON DUPLICATE KEY UPDATE store_type = VALUES(store_type), file_path = VALUES(file_path), path_config = VALUES(path_config), `count` = VALUES(`count`), update_time = VALUES(update_time)";
            statement.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void insert2(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            String sql = " INSERT INTO crowd_metadata_info (app_id, crowd_id, store_type, id_type, file_path, path_config, `count`, user_column, update_time)\n" +
                    "VALUES ('vuVqiPwNbl6FCfHxiTZox4', 'CROWD_2024-12-13_504f1b92df6643f6bbf8492478357a06', 's3', 'gtcid', '', '{\"clientKey\":\"IDO\"}', 0, '', NOW())\n" +
//                    "";
                    "ON DUPLICATE KEY UPDATE store_type = VALUES(store_type), file_path = VALUES(file_path), path_config = VALUES(path_config), `count` = VALUES(`count`), update_time = VALUES(update_time);";
            statement.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void insert3(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            String sql = " INSERT INTO crowd_metadata_info (app_id, crowd_id, store_type, id_type, file_path, path_config, `count`, user_column, update_time)\n" +
                    "VALUES ('vuVqiPwNbl6FCfHxiTZox4', 'CROWD_2024-12-13_3481595e4cdf4c2a8f2584a797d7e662', 's3', 'gtcid', '', '{\"clientKey\":\"IDO\"}', 0, '', NOW())\n" +
                    "";
//                    "ON DUPLICATE KEY UPDATE store_type = VALUES(store_type), file_path = VALUES(file_path), path_config = VALUES(path_config), `count` = VALUES(`count`), update_time = VALUES(update_time);";
            statement.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void insert4(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            String sql = " INSERT INTO crowd_metadata_info (app_id, crowd_id, store_type, id_type, file_path, path_config, `count`, user_column, update_time)\n" +
                    "VALUES ('vuVqiPwNbl6FCfHxiTZox4', 'CROWD_2024-12-13_153ecd5904aa48e8b081cc044147bd03', 's3', 'gtcid', '', '{\"clientKey\":\"IDO\"}', 0, '', NOW())\n" +
                    "";
//                    "ON DUPLICATE KEY UPDATE store_type = VALUES(store_type), file_path = VALUES(file_path), path_config = VALUES(path_config), `count` = VALUES(`count`), update_time = VALUES(update_time);";
            statement.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
