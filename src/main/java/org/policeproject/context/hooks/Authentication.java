package org.policeproject.context.hooks;

import com.google.inject.Inject;
import org.policeproject.context.ScenarioContext;
import org.policeproject.steps.api.auth.AuthenticationSteps;
import cucumber.runtime.java.guice.ScenarioScoped;

@ScenarioScoped
public class Authentication {

    @Inject
    protected ScenarioContext context;

    @Inject
    protected AuthenticationSteps authSteps;

//    here be auth code (if api needs auth)
}