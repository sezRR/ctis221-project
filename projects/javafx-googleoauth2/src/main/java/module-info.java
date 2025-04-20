module dev.sezrr.examples.javafx_googleoauth2 {
    /* JavaFX */
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    /* JDK built‑ins */
    requires java.net.http;   // HttpClient
    requires jdk.httpserver;  // loop‑back mini server

    /* Third‑party */
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;

    /* Runtime reflection for FXML + Jackson */
    opens dev.sezrr.examples.javafx_googleoauth2
            to javafx.fxml, com.fasterxml.jackson.databind;

    exports dev.sezrr.examples.javafx_googleoauth2;
}
