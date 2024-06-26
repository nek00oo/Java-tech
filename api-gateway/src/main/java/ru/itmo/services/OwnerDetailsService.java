package ru.itmo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.config.OwnerDetails;
import ru.itmo.dto.OwnerDto;
import ru.itmo.kafka.KafkaConsumer;
import ru.itmo.kafka.KafkaProducer;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OwnerDetailsService implements UserDetailsService {

    private final KafkaConsumer kafkaConsumer;
    private final KafkaProducer kafkaProducer;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals("admin")) {
            return new User("admin", passwordEncoder.encode("123"),
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        kafkaProducer.checkOwnerExists(username);

        try {
            Optional<OwnerDto> user = kafkaConsumer.getOwnerFuture().get(10, TimeUnit.SECONDS);
            return user.map(OwnerDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to retrieve user information", e);
        }
    }
}
