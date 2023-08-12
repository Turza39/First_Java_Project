module com.example.kingslayer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kingslayer to javafx.fxml;
    exports com.example.kingslayer;
}