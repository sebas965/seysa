package com.seysa.domain.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

@AutoValue
public abstract class Face {

    public static Face.Builder builder() {
        return new AutoValue_Face.Builder();
    }

    @Nullable
    public abstract String id();

    public abstract String videoScanId();

    @Nullable
    public abstract Date createdDate();

    @Nullable
    public abstract Date updatedDate();

    public abstract Long timestamp();

    @Nullable
    public abstract String boundingBox();

    @Nullable
    public abstract Integer maxAge();

    @Nullable
    public abstract Integer minAge();

    @Nullable
    public abstract Boolean smile();

    @Nullable
    public abstract Boolean eyeglasses();

    @Nullable
    public abstract Boolean sunglasses();

    @Nullable
    public abstract String gender();

    @Nullable
    public abstract Boolean beard();

    @Nullable
    public abstract Boolean mustache();

    @Nullable
    public abstract Boolean eyeOpen();

    @Nullable
    public abstract Boolean mouthOpen();

    @Nullable
    public abstract List<String> emotions();

    @Nullable
    public abstract List<String> landmarks();

    @Nullable
    public abstract String pose();

    @Nullable
    public abstract String imageQuality();

    @Nullable
    public abstract Float confidence();

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder id(final String id);

        public abstract Builder videoScanId(final String videoScanId);

        public abstract Builder createdDate(final Date createdDate);

        public abstract Builder updatedDate(final Date updatedDate);

        public abstract Builder timestamp(final Long timestamp);

        public abstract Builder boundingBox(final String boundingBox);

        public abstract Builder maxAge(final Integer maxAge);

        public abstract Builder minAge(final Integer minAge);

        public abstract Builder smile(final Boolean smile);

        public abstract Builder eyeglasses(final Boolean eyeglasses);

        public abstract Builder sunglasses(final Boolean sunglasses);

        public abstract Builder gender(final String gender);

        public abstract Builder beard(final Boolean beard);

        public abstract Builder mustache(final Boolean mustache);

        public abstract Builder eyeOpen(final Boolean eyeOpen);

        public abstract Builder mouthOpen(final Boolean mouthOpen);

        public abstract Builder emotions(final List<String> emotions);

        public abstract Builder landmarks(final List<String> landmarks);

        public abstract Builder pose(final String pose);

        public abstract Builder imageQuality(final String imageQuality);

        public abstract Builder confidence(final Float confidence);

        public abstract Face build();
    }
}
