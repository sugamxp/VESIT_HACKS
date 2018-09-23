package com.example.sumeetharyani.asset_manager;

public class Info {
    private String firstName;
    private String lastName;
    private String authority;

    public Info(String firstName, String lastName, String authority) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.authority = authority;
    }

    public Info() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
