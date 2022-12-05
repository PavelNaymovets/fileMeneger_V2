module com.example.filemeneger_v2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.filemeneger_v2 to javafx.fxml;
    exports com.example.filemeneger_v2;
}