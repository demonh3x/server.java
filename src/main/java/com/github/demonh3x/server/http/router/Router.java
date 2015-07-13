package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

public class Router implements RequestHandler {
    private static final RequestHandler UNDEFINED_MISSING_ROUTE_HANDLER = new RequestHandler() {
        @Override
        public Response handle(Request request) {
            throw new RuntimeException("Undefined handler for missing routes!");
        }
    };

    private final Route[] routes;
    private final RequestHandler missingRouteHandler;

    public Router(RequestHandler missingRouteHandler, Route... routes) {
        this.routes = routes;
        this.missingRouteHandler = missingRouteHandler;
    }

    public Router(Route... routes) {
        this(UNDEFINED_MISSING_ROUTE_HANDLER, routes);
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
