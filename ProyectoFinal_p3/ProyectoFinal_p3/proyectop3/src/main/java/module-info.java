module co.edu.uniquindio {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens co.edu.uniquindio to javafx.fxml;
    exports co.edu.uniquindio;
    exports co.edu.uniquindio.model;
    exports co.edu.uniquindio.exception;
    opens co.edu.uniquindio.viewController to javafx.fxml;
    exports co.edu.uniquindio.controller;
    exports co.edu.uniquindio.utils;
    opens co.edu.uniquindio.controller to javafx.fxml;
}
