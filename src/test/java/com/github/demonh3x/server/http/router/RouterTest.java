package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.Response;
import com.github.demonh3x.server.http.router.testdoubles.AlwaysMatching;
import com.github.demonh3x.server.http.router.testdoubles.NeverMatching;
import com.github.demonh3x.server.http.testdoubles.NullRequestHandler;
import com.github.demonh3x.server.http.testdoubles.RequestHandlerDouble;
import com.github.demonh3x.server.http.testdoubles.RequestHandlerSpy;
import org.junit.Test;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RouterTest {
    @Test
    public void usesTheMatchingRoute() {
        Response responseFromHandler = new Response(200, "", new byte[]{});
        RequestHandlerDouble handlerSpy = new RequestHandlerDouble(responseFromHandler);
        Router router = new Router(
                new Route(new NeverMatching(), new NullRequestHandler()),
                new Route(new AlwaysMatching(), handlerSpy)
        );
        Request request = get("/uri");

        Response routedResponse = router.handle(request);

        assertThat(handlerSpy.getReceivedRequest(), is(request));
        assertThat(routedResponse, is(responseFromHandler));
    }

    @Test
    public void sendsTheRequestOnlyToTheFirstMatchingRoute() {
        RequestHandlerSpy first = new RequestHandlerSpy();
        RequestHandlerSpy second = new RequestHandlerSpy();
        Router router = new Router(
                new Route(new AlwaysMatching(), first),
                new Route(new AlwaysMatching(), second)
        );

        router.handle(get("/uri"));

        assertThat(first.hasBeenCalled(), is(true));
        assertThat(second.hasBeenCalled(), is(false));
    }

    @Test
    public void callsDefaultHandlerWhenNoRouteIsMatching() {
        Response responseFromHandler = new Response(200, "", new byte[]{});
        RequestHandlerDouble handlerSpy = new RequestHandlerDouble(responseFromHandler);
        Router router = new Router(
                handlerSpy,
                new Route(new NeverMatching(), new NullRequestHandler()),
                new Route(new NeverMatching(), new NullRequestHandler())
        );
        Request request = get("/uri");

        Response routedResponse = router.handle(request);

        assertThat(handlerSpy.getReceivedRequest(), is(request));
        assertThat(routedResponse, is(responseFromHandler));
    }

    @Test (expected = RuntimeException.class)
    public void withoutDefaultHandlerThrowsExceptionIfNoRouteIsMatching() {
        Router router = new Router();

        Request request = get("/uri");

        router.handle(request);
    }
}
