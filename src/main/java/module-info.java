module org.prova1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens org.prova1 to javafx.fxml;
    exports org.prova1;
    exports org.prova1.model;
    opens org.prova1.model to javafx.fxml;
    exports org.prova1.dao;
    opens org.prova1.dao to javafx.fxml;
    exports org.prova1.database;
    opens org.prova1.database to javafx.fxml;
    exports org.prova1.controller;
    opens org.prova1.controller to javafx.fxml;
}
