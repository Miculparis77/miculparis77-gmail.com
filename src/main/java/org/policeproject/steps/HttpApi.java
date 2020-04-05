package org.policeproject.steps;

import com.cucumber.utils.clients.http.HttpClient;
import com.cucumber.utils.context.props.ScenarioProps;
import com.cucumber.utils.context.utils.Cucumbers;
import com.cucumber.utils.engineering.utils.JsonUtils;
import com.google.inject.Inject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.policeproject.context.ScenarioContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;


public class HttpApi {
    protected Logger log = LogManager.getLogger();
    protected HttpClient.Builder builder;
    @Inject
    protected ScenarioContext context;

    @Inject
    protected Cucumbers cucumbers;
    @Inject
    protected ScenarioProps scenarioProps;

    protected void init() {
        this.builder = new HttpClient.Builder();
    }

    public void setRequestEntity(String requestEntity) {
        builder.entity(requestEntity);
    }

    public void invokeHttpRequestAndCompareResultWith(String expected) {
        invokeHttpRequestAndCompareResultWith(expected, null);
    }

    public void invokeHttpRequestAndCompareResultWith(String expected, Integer pollDurationInSeconds) {
        HttpClient client = builder.build();
        context.write("--------- API call details ---------");
        context.write("REQUEST: " + client.getMethod() + client.getUri());
        context.write("REQUEST HEADERS: " + client.getHeaders());
        if (client.getRequestEntity() != null) {
            context.write("REQUEST BODY: ", client.getRequestEntity());
        }
        final AtomicReference<CloseableHttpResponse> responseWrapper = new AtomicReference<>();
        HttpEntity entity = null;

        if (pollDurationInSeconds == null) {
            responseWrapper.set(client.execute());
            cucumbers.compare(expected, responseWrapper.get());
        } else {
            cucumbers.pollAndCompare(expected, pollDurationInSeconds,
                    () -> {
                        responseWrapper.set(client.execute());
                        return responseWrapper.get();
                    });
        }
        try {
            context.write("----------- Comparison -----------");
            context.write("EXPECTED Response:", expected);
            context.write("--------------- vs ---------------");
            context.write("ACTUAL Response status: " + responseWrapper.get().getStatusLine().getStatusCode());
            entity = responseWrapper.get().getEntity();
            context.write("ACTUAL Response body:", (entity != null) ? JsonUtils.prettyPrint(EntityUtils.toString(entity)) : "Empty data <∅>");
            context.write("ACTUAL Response headers:", Arrays.asList(responseWrapper.get().getAllHeaders()).toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (entity != null) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                    log.error(e);
                }
            }
            try {
                responseWrapper.get().close();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
}