package com.julianduru.data.messaging.dto;


public record UserDataUpdate(
    String username,
    String firstName,
    String lastName,
    String email,
    String profilePhotoRef
) {
}

