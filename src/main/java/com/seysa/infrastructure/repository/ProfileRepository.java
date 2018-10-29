package com.seysa.infrastructure.repository;

import com.seysa.infrastructure.repository.model.ProfileItem;

public interface ProfileRepository {

    ProfileItem get(final String id);

    ProfileItem create(final ProfileItem item);
}
