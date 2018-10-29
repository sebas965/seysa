package com.seysa.domain.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.Date;

@AutoValue
public abstract class Label {

    public static Builder builder() {
        return new AutoValue_Label.Builder();
    }

    @Nullable
    public abstract String id();

    public abstract String name();

    public abstract Float confidence();

    public abstract Long timestamp();

    @Nullable
    public abstract Date createdDate();

    @Nullable
    public abstract Date updatedDate();

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder id(final String id);

        public abstract Builder name(final String name);

        public abstract Builder confidence(final Float confidence);

        public abstract Builder timestamp(final Long timestamp);

        public abstract Builder createdDate(final Date createdDate);

        public abstract Builder updatedDate(final Date updatedDate);

        public abstract Label build();
    }
}
