package com.github.demonh3x.server.http.router.dsl;

import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.router.*;

public class Routes {
    public enum Method {
        GET, POST, PUT, DELETE, PATCH, OPTIONS
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
        MethodMatcher methodMatcher = new MethodMatcher(method.toString());
        UriMatcher uriMatcher = new UriMatcher(uri);
        AndMatcher methodAndUriMatcher = new AndMatcher(methodMatcher, uriMatcher);

        return new MatchedRoute(methodAndUriMatcher);
    }

    public static MatchedRoute onAnyMethodTo(String uri) {
        return new MatchedRoute(new UriMatcher(uri));
    }
}
