package TaskManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteExample {

    public static void main(String[] args) {
        // JDBCドライバを明示的に読み込む
        try {
            Class.forName("org.sqlite.JDBC"); // JDBCドライバをロード
        } catch (ClassNotFoundException e) {
            System.out.println("JDBCドライバの読み込みに失敗しました: " + e.getMessage());
            return;
        }

        // データベースのURL（絶対パス）
        String url = "jdbc:sqlite:C:/Users/kenta/Desktop/japp/タスク管理アプリ/src/tasks.db"; // 絶対パス

        // SQLiteの接続を作成
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                // テーブル作成のSQL
                String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                             "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                             "title TEXT NOT NULL," +
                             "description TEXT," +
                             "priority INTEGER," +
                             "deadline DATE," +
                             "status TEXT" +
                             ");";
                             
                // ステートメントを作成し、SQLを実行
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(sql);
                    System.out.println("テーブルが作成されました。");
                } catch (SQLException e) {
                    System.out.println("テーブル作成中にエラーが発生しました: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("接続に失敗しました: " + e.getMessage());
        }
    }
}
