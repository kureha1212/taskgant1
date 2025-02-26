package TaskManager;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GanttChartTable extends Application {
    
    public static class Task {
        private final SimpleStringProperty name;
        private final SimpleDoubleProperty progress;
        
        public Task(String name, double progress) {
            this.name = new SimpleStringProperty(name);
            this.progress = new SimpleDoubleProperty(progress);
        }
        
        public String getName() { return name.get(); }
        public double getProgress() { return progress.get(); }
    }

    // 安全な addColumns メソッド
    @SafeVarargs
    private static <T> void addColumns(TableView<T> table, TableColumn<T, ?>... columns) {
        table.getColumns().addAll(columns);
    }

    @Override
    public void start(Stage stage) {
        TableView<Task> table = new TableView<>();
        
        TableColumn<Task, String> nameCol = new TableColumn<>("Task Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Task, ProgressBar> progressCol = new TableColumn<>("Progress");
        progressCol.setCellValueFactory(cellData -> {
            ProgressBar progressBar = new ProgressBar(cellData.getValue().getProgress());
            return new javafx.beans.property.SimpleObjectProperty<>(progressBar);
        });

        // ここで addColumns を使用
        addColumns(table, nameCol, progressCol);

        table.getItems().addAll(
            new Task("Task 1", 0.3),
            new Task("Task 2", 0.7),
            new Task("Task 3", 1.0)
        );

        Scene scene = new Scene(table, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Gantt Chart (TableView)");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
