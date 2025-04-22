module dev.sezrr.llmchatwrapper.frontendjavafxgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens dev.sezrr.llmchatwrapper.frontendjavafxgui to javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.model to com.fasterxml.jackson.databind;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui.core.custom_response_entity to com.fasterxml.jackson.databind;
}
