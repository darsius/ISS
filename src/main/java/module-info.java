module com.example.proiecto {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.naming;
    opens com.example.proiecto.Model to javafx.base, javafx.fxml, org.hibernate.orm.core;

    requires org.controlsfx.controls;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;

    opens com.example.proiecto to javafx.fxml;
    exports com.example.proiecto;
    exports com.example.proiecto.Controller;
    opens com.example.proiecto.Controller to javafx.fxml;
    opens com.example.proiecto.View to javafx.fxml;
}
