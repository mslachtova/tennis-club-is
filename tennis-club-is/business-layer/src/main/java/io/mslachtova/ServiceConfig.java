package io.mslachtova;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Service configuration
 *
 * @author Monika Slachtova
 */
@Configuration
@Import(PersistenceConfig.class)
@ComponentScan()
public class ServiceConfig {
    @Bean
    public Mapper dozer(){
        Mapper mapper = DozerBeanMapperBuilder.create()
                .withMappingBuilder(new DozerCustomConfig())
                .build();
        return mapper;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
        }
    }
}
