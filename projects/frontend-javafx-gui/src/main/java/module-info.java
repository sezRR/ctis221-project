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
    requires java.rmi;
    requires org.yaml.snakeyaml;

    exports dev.sezrr.llmchatwrapper.frontendjavafxgui;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model to com.fasterxml.jackson.databind;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity to com.fasterxml.jackson.databind;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene to javafx.fxml, com.fasterxml.jackson.databind;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui to javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token to com.fasterxml.jackson.databind, javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth to com.fasterxml.jackson.databind, javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils to com.fasterxml.jackson.databind, javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.controller;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.controller to com.fasterxml.jackson.databind, javafx.fxml;
}
