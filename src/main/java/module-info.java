module org.prova1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens org.prova1 to javafx.fxml;
    exports org.prova1;
}
