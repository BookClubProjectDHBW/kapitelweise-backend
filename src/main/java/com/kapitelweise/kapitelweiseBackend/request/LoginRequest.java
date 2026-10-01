package com.kapitelweise.kapitelweiseBackend.request;


//äquivalent zu AppUser, nur dass es ein record ist, d.h. es ist immutable
public record LoginRequest(String username, String password) {

}