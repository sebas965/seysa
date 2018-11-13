package com.seysa.infrastructure.repository.model;

import java.util.Date;

public interface Item {

    String getId();

    Date getCreatedDate();
    
    Date getUpdatedDate();
}
