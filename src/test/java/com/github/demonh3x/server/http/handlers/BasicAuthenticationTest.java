package com.github.demonh3x.server.http.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.Response;
import com.github.demonh3x.server.http.testdoubles.RequestHandlerDouble;
import com.github.demonh3x.server.http.testdoubles.RequestHandlerSpy;
import org.junit.Test;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicAuthenticationTest {
    @Test
    public void givenValidAuthorizationDelegatesToTheRequestHandler() {
        Response delegatedResponse = new Response(200, "OK", new byte[0]);
        RequestHandlerDouble delegate = new RequestHandlerDouble(delegatedResponse);
        BasicAuthentication auth = new BasicAuthentication(delegate);

        String base64ForUserAndPassword = "YWRtaW46aHVudGVyMg=="; //= "admin:hunter2"
        Request authenticatedRequest = get(headers("Authorization", "Basic " + base64ForUserAndPassword));
        Response finalResponse = auth.handle(authenticatedRequest);

        assertThat(delegate.getReceivedRequest(), is(authenticatedRequest));
        assertThat(finalResponse, is(delegatedResponse));
    }

    @Test
    public void givenInvalidAuthorizationResponds401WithoutCallingTheDelegate() {
        RequestHandlerSpy delegate = new RequestHandlerSpy();
        BasicAuthentication auth = new BasicAuthentication(delegate);

        Request notAuthenticatedRequest = get(headers("Authorization", "Basic YTpi"));
        Response response = auth.handle(notAuthenticatedRequest);

        assertThat(delegate.hasBeenCalled(), is(false));
        assertNotAuthorized(response);
    }

    @Test
    public void givenNoAuthorizationHeaderResponds401WithoutCallingTheDelegate() {
        RequestHandlerSpy delegate = new RequestHandlerSpy();
        BasicAuthentication auth = new BasicAuthentication(delegate);

        Request notAuthenticatedRequest = get();
        Response response = auth.handle(notAuthenticatedRequest);

        assertThat(delegate.hasBeenCalled(), is(false));
        assertNotAuthorized(response);
    }

    private void assertNotAuthorized(Response response) {
        assertThat(response.getStatusCode(), is(401));
        assertThat(response.getReasonPhrase(), is("Unauthorized"));
        String body = new String(response.getMessageBody());
        assertThat(body, is("Authentication required"));
    }
}
