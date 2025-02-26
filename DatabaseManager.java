package TaskManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:tasks.db";

    // データベース接続メソッド
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // テーブルを作成 (初回のみ)
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "title TEXT NOT NULL, " +
                     "description TEXT, " +
                     "deadline TEXT, " +
                     "priority INTEGER)";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // タスクを追加
    public static void addTask(String title, String description, String deadline, int priority) {
        String sql = "INSERT INTO tasks(title, description, deadline, priority) VALUES(?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, deadline);
            pstmt.setInt(4, priority);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // タスクを取得
    public static ResultSet getTasks() {
        String sql = "SELECT * FROM tasks ORDER BY deadline, priority DESC";
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
