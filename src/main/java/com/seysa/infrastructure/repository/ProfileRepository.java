package com.seysa.infrastructure.repository;

import com.seysa.infrastructure.model.ProfileItem;

public interface ProfileRepository {

    ProfileItem getByUuid(final String uuid);

    ProfileItem create(final ProfileItem item);
}
