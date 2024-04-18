module com.example.dogughnuts {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jakarta.persistence;
    requires com.zaxxer.hikari;
    requires org.hibernate.orm.core;

    opens com.example to javafx.fxml;
    exports com.example;
    exports com.example.Controller;
    opens com.example.Controller to javafx.fxml;
}