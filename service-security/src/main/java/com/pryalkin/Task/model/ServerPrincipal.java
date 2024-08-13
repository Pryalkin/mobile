package com.pryalkin.Task.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ServerPrincipal implements UserDetails {

    private Server server;

    public ServerPrincipal(Server server) {
        this.server = server;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return server.getServerPassword();
    }

    @Override
    public String getUsername() {
        return server.getServerName();
    }
}
