package TaskManager;

import java.time.LocalDate;
import java.time.YearMonth;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MonthlyCalendarApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        YearMonth currentMonth = YearMonth.now();
        GridPane calendar = new GridPane();

        // 曜日のラベルを追加
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            calendar.add(dayLabel, i, 0);
        }

        // 月の日付を追加
        LocalDate firstDayOfMonth = currentMonth.atDay(1);
        int dayOfWeekIndex = firstDayOfMonth.getDayOfWeek().getValue() % 7; // 日曜日を0にする
        int daysInMonth = currentMonth.lengthOfMonth();

        int row = 1;
        for (int day = 1; day <= daysInMonth; day++) {
            Label dayLabel = new Label(String.valueOf(day));
            calendar.add(dayLabel, (dayOfWeekIndex + day - 1) % 7, row);
            if ((dayOfWeekIndex + day) % 7 == 0) {
                row++;
            }
        }

        Scene scene = new Scene(calendar, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("月間カレンダー");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
