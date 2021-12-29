package io.mslachtova.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.mslachtova.ServiceConfig;

import javax.annotation.PostConstruct;

/**
 * @author Monika Slachtova
 */
@Configuration
@Import(ServiceConfig.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class SampleDataConfig {


        @Autowired
        SampleDataLoadingFacade sampleDataLoadingFacade;

        @PostConstruct
        public void dataLoading() {
            sampleDataLoadingFacade.loadData();
        }
}
