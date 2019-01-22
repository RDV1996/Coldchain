package be.ordina.coldchain.Config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Configuration
public class PasswordEncodingConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {

                return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(6));
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {

                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        };
    }

}
