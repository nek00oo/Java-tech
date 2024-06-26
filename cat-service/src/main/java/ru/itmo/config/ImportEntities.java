package ru.itmo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.itmo.EnableEntities;

@Import({
        EnableEntities.class
})
@Configuration
public class ImportEntities {
}
