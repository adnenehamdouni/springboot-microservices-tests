package com.isquare.microservices.automatisation;

import com.isquare.microservices.config.SharedWebTestConfig;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.isquare.microservices.steps", "com.isquare.microservices.config"},
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
@Import(SharedWebTestConfig.class)
public class CucumberTest {
}

