module com.example.bai2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bai2 to javafx.fxml;
    exports com.example.bai2;
}