package org.vm.test;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features", 
    glue = { "org.vm.test.stepdefs" }, 
    plugin = { 
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml",
        "timeline:target/cucumber-reports/timeline",
        "usage:target/cucumber-reports/usage.json",
        "rerun:target/cucumber-reports/rerun.txt",
        "org.vm.test.plugins.DetailedReporter"
    },
    monochrome = true,
    publish = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
