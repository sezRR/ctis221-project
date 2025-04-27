package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.log;

import java.io.IOException;
import java.time.LocalDateTime;

public abstract class Log<TE>
{
    protected String prefix;
    protected LocalDateTime actionFiredAt;
    protected TE eventType;

    public abstract void log(String message) throws IOException;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public LocalDateTime getActionFiredAt() {
        return actionFiredAt;
    }

    public void setActionFiredAt(LocalDateTime actionFiredAt) {
        this.actionFiredAt = actionFiredAt;
    }

    public TE getEventType() {
        return eventType;
    }

    public void setEventType(TE eventType) {
        this.eventType = eventType;
    }
}