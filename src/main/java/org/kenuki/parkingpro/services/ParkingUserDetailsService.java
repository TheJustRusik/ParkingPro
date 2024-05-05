package org.kenuki.parkingpro.services;

import org.kenuki.parkingpro.configs.ParkingUserDetails;
import org.kenuki.parkingpro.entities.User;
import org.kenuki.parkingpro.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ParkingUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    @Autowired
    public void getUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByNicknameOrEmail(usernameOrEmail, usernameOrEmail);

        return user.map(ParkingUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("[" + usernameOrEmail + "] not found!"));
    }
}
