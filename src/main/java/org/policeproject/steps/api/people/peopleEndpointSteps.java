package org.policeproject.steps.api.people;

import com.cucumber.utils.clients.http.Method;
import com.cucumber.utils.context.props.ScenarioProps;
import com.cucumber.utils.context.utils.Cucumbers;
import com.google.inject.Inject;
import io.cucumber.java.en.Then;
import org.policeproject.steps.HttpApi;

import static org.policeproject.env.Env.*;

public class peopleEndpointSteps extends HttpApi {

    public static final String URI = "forces/%s/people";
    public String policeForce = null;

//    @Inject
//    public peopleEndpointSteps(Cucumbers cucumbers) {
//
//    }

    @Inject
    private ScenarioProps scenarioProps;

    protected void init(String policeForce) {
        super.init();
        this.builder
                .address(apiHost)
                .method(Method.GET)
                .path(String.format(URI, policeForce));
    }

    @Then("we make a get request to police force={} and check officers list response")
    public void getRequest(String policeForce) {
        cucumbers.loadScenarioPropsFromFile("templates/forces/"+policeForce+"/properties.yaml");
        init(policeForce);
        invokeHttpRequestAndCompareResultWith(scenarioProps.get("getOfficersResponse").toString(), 5);
    }
}