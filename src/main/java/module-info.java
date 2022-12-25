module com.example.filemeneger_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.netty.codec;
    requires io.netty.transport;
    requires org.slf4j;
    requires lombok;
    requires java.sql;
    requires mysql.connector.java;
    requires org.apache.commons.codec;

    exports com.example.filemeneger_v2.client;
    opens com.example.filemeneger_v2.client to javafx.fxml;
    exports com.example.filemeneger_v2.common.fileInfo;
    opens com.example.filemeneger_v2.common.fileInfo to javafx.fxml;
    exports com.example.filemeneger_v2.client.changeWindow;
    opens com.example.filemeneger_v2.client.changeWindow to javafx.fxml;
}