package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.log;

import java.io.IOException;
import java.time.LocalDateTime;

abstract class Log <TE>
{

    protected String prefix;
    protected LocalDateTime actionFiredAt;
    protected TE eventType;


    abstract void log (String prefix) throws IOException;
}