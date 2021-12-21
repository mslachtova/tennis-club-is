import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import io.mslachtova.PersistenceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import service.BeanMapperImpl;

/**
 * Service configuration
 *
 * @author Monika Slachtova
 */
@Configuration
@Import(PersistenceConfig.class)
@ComponentScan(basePackageClasses = {BeanMapperImpl.class})
public class ServiceConfig {
    @Bean
    public Mapper dozerBean() {
        Mapper dozerBean = DozerBeanMapperBuilder.buildDefault();
        return dozerBean;
    }
}
