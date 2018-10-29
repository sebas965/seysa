package com.seysa.domain.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.Date;

@AutoValue
public abstract class VideoScan {

    public static VideoScan.Builder builder() {
        return new AutoValue_VideoScan.Builder();
    }

    @Nullable
    public abstract String id();

    @Nullable
    public abstract String scanId();

    @Nullable
    public abstract Long timestamp();

    public abstract Video video();

    @Nullable
    public abstract Date createdDate();

    @Nullable
    public abstract Date updatedDate();

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder id(final String id);

        public abstract Builder video(final Video video);

        public abstract Builder scanId(final String scanId);

        public abstract Builder timestamp(final Long timestamp);

        public abstract Builder createdDate(final Date createdDate);

        public abstract Builder updatedDate(final Date updatedDate);

        public abstract VideoScan build();
    }
}
