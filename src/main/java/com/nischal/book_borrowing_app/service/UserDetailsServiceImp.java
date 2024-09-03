package com.nischal.book_borrowing_app.service;

import com.nischal.book_borrowing_app.dto.UserPrincipal;
import com.nischal.book_borrowing_app.entity.LibraryUser;
import com.nischal.book_borrowing_app.repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private LibraryUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LibraryUser users = userRepository.findByUsername(username);

        if (users == null) {
            throw new UsernameNotFoundException("User not found");
        }
//  DaoAuthenticationProvider expects a UserDetails object to represent
//  the authenticated user, and UserPrincipal meets this requirement.
        return new UserPrincipal(users);
    }
}
