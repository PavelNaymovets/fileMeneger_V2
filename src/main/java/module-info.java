module com.example.filemeneger_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.netty.codec;
    requires org.slf4j;
    requires lombok;
    requires io.netty.transport;

    exports com.example.filemeneger_v2.client;
    opens com.example.filemeneger_v2.client to javafx.fxml;
}