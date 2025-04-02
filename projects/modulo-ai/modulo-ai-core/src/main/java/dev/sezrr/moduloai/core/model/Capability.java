package dev.sezrr.moduloai.core.model;

public enum Capability {
    TEXT_GENERATION("text_generation"),
    EMBEDDINGS("embeddings"),
    IMAGE_GENERATION("image_generation"),
    AUDIO_GENERATION("audio_generation"),
    VIDEO_GENERATION("video_generation"),
    FUNCTION_CALLING("function_calling"),
    CHAT("chat"),
    FILE_UPLOAD("file_upload"),
    FILE_DOWNLOAD("file_download");

    private final String name;

    Capability(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
