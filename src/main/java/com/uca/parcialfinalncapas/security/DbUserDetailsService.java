package com.uca.parcialfinalncapas.security;

import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
@AllArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        User user = repository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontro el usuario con el correo: "+correo));

        var authorities = new HashSet<GrantedAuthority>();

        authorities.add( new SimpleGrantedAuthority(user.getNombre()));

        return new org.springframework.security.core.userdetails.User(correo, user.getPassword(), authorities);
    }
}
