module co.edu.uniquindio {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires org.apache.poi.ooxml;
    requires org.apache.pdfbox;
    requires java.logging;

    opens co.edu.uniquindio to javafx.fxml;
    exports co.edu.uniquindio;
    exports co.edu.uniquindio.model;
    exports co.edu.uniquindio.exception;
    opens co.edu.uniquindio.viewController to javafx.fxml;
    exports co.edu.uniquindio.controller;
    opens co.edu.uniquindio.controller to javafx.fxml;
    opens co.edu.uniquindio.model to java.xml.bind;

}
