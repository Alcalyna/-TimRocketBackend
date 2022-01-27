package com.example.timrocket_backend.security;

import com.google.common.collect.Lists;

import java.util.*;

import static com.example.timrocket_backend.security.Feature.*;

public enum SecurityRole {
    ADMIN(GET_USER_INFORMATION, GET_COACH_INFORMATION),
    COACH(GET_CLASS_TEST, ADD_CLASS_TEST, GET_USER_INFORMATION, GET_COACH_INFORMATION),
    COACHEE(GET_USER_INFORMATION);

    private final List<Feature> features;

    SecurityRole(Feature... features) {
        this.features = Lists.newArrayList(features);
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public String getRoleName() {
        return this.name().toLowerCase();
    }
}


