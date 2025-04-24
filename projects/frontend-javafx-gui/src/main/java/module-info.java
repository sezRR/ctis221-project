module dev.sezrr.llmchatwrapper.frontendjavafxgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires jdk.httpserver;
    requires com.fasterxml.jackson.datatype.jsr310;

    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.controller.auth;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.model to com.fasterxml.jackson.databind;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.custom_response_entity to com.fasterxml.jackson.databind;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.controller.auth to javafx.fxml, com.fasterxml.jackson.databind;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene to javafx.fxml, com.fasterxml.jackson.databind;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui to javafx.fxml;
}
