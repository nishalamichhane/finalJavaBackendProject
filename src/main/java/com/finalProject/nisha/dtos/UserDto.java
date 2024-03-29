package com.finalProject.nisha.dtos;

import com.finalProject.nisha.models.Authority;
import com.finalProject.nisha.models.Order;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Set;

public class UserDto {

    @NotBlank (message = "Username should not be blank")
    public String username;
    @NotBlank(message = "Password should not be blank")
    public String password;
    @NotNull
    public Boolean enabled;
    public String apikey;

    @NotBlank
    @Email
    public String email;
    public Set<Authority> authorities;
    public List<Order> order;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public String getEmail() {
        return email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}