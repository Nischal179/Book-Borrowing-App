package com.nischal.book_borrowing_app.dto;

import com.nischal.book_borrowing_app.entity.LibraryUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/*The UserPrincipal class acts as a bridge between custom user model (Users) and Spring Security.
When a user attempts to authenticate, Spring Security uses this class to retrieve user details
such as username, password, and authorities.
 */
public class UserPrincipal implements UserDetails {

    private LibraryUser users;

    public UserPrincipal(LibraryUser users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
