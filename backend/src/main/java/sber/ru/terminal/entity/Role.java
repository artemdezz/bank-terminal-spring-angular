package sber.ru.terminal.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT, OPERATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}