package com.seysa.domain.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.Date;

@AutoValue
public abstract class Video {

    public static Video.Builder builder() {
        return new AutoValue_Video.Builder();
    }

    @Nullable
    public abstract String id();

    public abstract String name();

    public abstract String location();

    @Nullable
    public abstract Date createdDate();

    @Nullable
    public abstract Date updatedDate();

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder id(final String id);

        public abstract Builder name(final String videoName);

        public abstract Builder location(final String videoLocation);

        public abstract Builder createdDate(final Date createdDate);

        public abstract Builder updatedDate(final Date updatedDate);

        public abstract Video build();
    }
}
