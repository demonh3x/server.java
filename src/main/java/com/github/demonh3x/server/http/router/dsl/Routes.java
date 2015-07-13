package com.github.demonh3x.server.http.router.dsl;

import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.router.MethodAndUriMatcher;
import com.github.demonh3x.server.http.router.RequestMatcher;
import com.github.demonh3x.server.http.router.Route;

public class Routes {
    public enum Method {
        GET, POST, PUT, DELETE, PATCH, OPTION
    }

    public static class MatchedRoute {
        private final RequestMatcher matcher;

        private MatchedRoute(RequestMatcher matcher) {
            this.matcher = matcher;
        }

        public Route will(RequestHandler handler) {
            return new Route(matcher, handler);
        }
    }

    public static MatchedRoute on(Method method, String uri) {
        return new MatchedRoute(new MethodAndUriMatcher(method.toString(), uri));
    }
}
