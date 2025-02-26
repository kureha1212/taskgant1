package TaskManager;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




public class TaskManagerApp extends Application {

    private final ObservableList<String> tasks = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        // データベーステーブル作成
        DatabaseManager.createTable();

        // UIコンポーネント
        ListView<String> taskListView = new ListView<>(tasks);
        TextField titleField = new TextField();
        titleField.setPromptText("タイトル");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("説明");
        TextField deadlineField = new TextField();
        deadlineField.setPromptText("締切日 (YYYY-MM-DD)");
        TextField priorityField = new TextField();
        priorityField.setPromptText("優先度 (1-5)");

        Button addButton = new Button("タスクを追加");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            String deadline = deadlineField.getText();
            int priority = Integer.parseInt(priorityField.getText());
            DatabaseManager.addTask(title, description, deadline, priority);
            updateTaskList();
            titleField.clear();
            descriptionField.clear();
            deadlineField.clear();
            priorityField.clear();
        });

        // レイアウト
        VBox layout = new VBox(10, titleField, descriptionField, deadlineField, priorityField, addButton, taskListView);
        layout.setPadding(new Insets(10));

        // シーン設定
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("タスク管理アプリ");
        primaryStage.show();

        // 初期タスクリストの表示
        updateTaskList();
    }

    // タスクリストを更新
    private void updateTaskList() {
        tasks.clear();
        try (ResultSet rs = DatabaseManager.getTasks()) {
            while (rs != null && rs.next()) {
                String task = rs.getInt("id") + ". " + rs.getString("title") + " (締切: " + rs.getString("deadline") + ", 優先度: " + rs.getInt("priority") + ")";
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
