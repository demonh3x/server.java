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

    public static MatchedRoute on(Method method, String uriBeginning) {
        MethodMatcher methodMatcher = new MethodMatcher(method.toString());
        UriMatcher uriMatcher = new UriMatcher(uriBeginning);
        AndMatcher methodAndUriMatcher = new AndMatcher(methodMatcher, uriMatcher);

        return new MatchedRoute(methodAndUriMatcher);
    }

    public static MatchedRoute on(Method method) {
        return new MatchedRoute(new MethodMatcher(method.toString()));
    }

    public static MatchedRoute onAnyMethodTo(String uriBeginning) {
        return new MatchedRoute(new UriMatcher(uriBeginning));
    }
}
