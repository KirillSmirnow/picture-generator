package org.smirnowku.picturegenerator.fs;

import org.apache.http.client.methods.HttpUriRequest;

public interface AuthorizationService {

    String authString(String username, String password);

    default void addAuthHeader(HttpUriRequest request, String username, String password) {
        request.addHeader("Authorization", authString(username, password));
    }
}
