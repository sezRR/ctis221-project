module dev.sezrr.llmchatwrapper.frontendjavafxgui {

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires jdk.httpserver;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.rmi;
    requires org.yaml.snakeyaml;
    requires org.controlsfx.controls;
    requires java.net.http;
    requires reactor.core;
    requires one.jpro.platform.mdfx;
    requires com.sandec.mdfx;
    requires javafx.fxml;

    exports dev.sezrr.llmchatwrapper.frontendjavafxgui;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model to com.fasterxml.jackson.databind, javafx.fxml;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model to com.fasterxml.jackson.databind, javafx.fxml, javafx.base;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity to com.fasterxml.jackson.databind;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene to javafx.fxml, com.fasterxml.jackson.databind;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui to javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token to com.fasterxml.jackson.databind, javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth to com.fasterxml.jackson.databind, javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils to com.fasterxml.jackson.databind, javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.scene;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.scene to com.fasterxml.jackson.databind, javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.config;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.config to com.fasterxml.jackson.databind, javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.login;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.login to com.fasterxml.jackson.databind, javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.chat;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.chat to com.fasterxml.jackson.databind, javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.admin;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.admin to com.fasterxml.jackson.databind, javafx.fxml;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.component to javafx.fxml;
    opens dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.pagination to com.fasterxml.jackson.databind;
}
