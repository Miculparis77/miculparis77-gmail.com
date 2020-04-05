package org.policeproject.context;



import cucumber.runtime.java.guice.ScenarioScoped;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;


@ScenarioScoped
public class ScenarioContext {

    private Scenario scenario;

    @Before
    public void init(Scenario scenario) {
        this.scenario = scenario;
    }

    public void write(String data) {
        write(null, data);
    }

    public void write(String summary, String data) {
        this.scenario.write(summary != null ? summary + (data == null ? "" : System.lineSeparator() + data) : data);
    }
}
