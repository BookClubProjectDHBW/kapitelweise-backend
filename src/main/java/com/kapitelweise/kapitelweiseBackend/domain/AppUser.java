package com.kapitelweise.kapitelweiseBackend.domain;
import java.util.Objects;

// das ist unser Datenbank User Objekt, das aktuell username, password enthält
public class AppUser{
    private String username;
    private String password;

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AppUser)) return false;
    AppUser other = (AppUser) o;
    return Objects.equals(username, other.username);
}

@Override
public int hashCode() {
    return Objects.hashCode(username);
}

    public void setUsername(String username) {
        this.username = username;
    }
}