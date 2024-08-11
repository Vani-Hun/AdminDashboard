package com.projection;

public interface AdminProjection {
    String getId();

    String getUsername();

    String getName();

    String getLogo();

    String getPermission();

    interface AdminProjectionWithAll extends AdminProjection {

    }
}

