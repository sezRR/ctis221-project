module dev.sezrr.llmchatwrapper.frontendjavafxgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens dev.sezrr.llmchatwrapper.frontendjavafxgui to javafx.fxml;
    exports dev.sezrr.llmchatwrapper.frontendjavafxgui;
}