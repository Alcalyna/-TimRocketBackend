package com.example.timrocket_backend.security;

import com.google.common.collect.Lists;

import java.util.*;

import static com.example.timrocket_backend.security.Feature.*;

public enum SecurityRole {
    ADMIN(GET_USER_INFORMATION, UPDATE_PROFILE),
    COACH(GET_CLASS_TEST, ADD_CLASS_TEST, GET_USER_INFORMATION, UPDATE_PROFILE),
    COACHEE(GET_USER_INFORMATION, UPDATE_PROFILE);

    private final List<Feature> features;

    SecurityRole(Feature... features) {
        this.features = Lists.newArrayList(features);
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public String getRoleName() {
        String lowercaseName = this.name().toLowerCase();
        return lowercaseName.substring(0, 1).toUpperCase() + lowercaseName.substring(1);
    }

    public static SecurityRole getByName(String name) {
        for(SecurityRole role : SecurityRole.values()) {
            if(name.equalsIgnoreCase(role.getRoleName())) {
                return role;
            }
        }
        return null;
    }
}


