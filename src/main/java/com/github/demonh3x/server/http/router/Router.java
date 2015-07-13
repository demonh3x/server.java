package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

import java.util.List;

public class Router implements RequestHandler {
    private final List<Route> routes;
    private final RequestHandler missingRouteHandler;

    public Router(List<Route> routes, RequestHandler missingRouteHandler) {
        this.routes = routes;
        this.missingRouteHandler = missingRouteHandler;
    }

    public Response handle(Request request) {
        return findHandlerFor(request).handle(request);
    }

    private RequestHandler findHandlerFor(Request request) {
        for (Route route : routes) {
            if (route.isRequestedBy(request))
                return route;
        }

        return missingRouteHandler;
    }
}
