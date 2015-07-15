package com.github.demonh3x.app;

import com.github.demonh3x.app.handlers.*;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.handlers.BasicAuthentication;
import com.github.demonh3x.server.http.handlers.Redirect;
import com.github.demonh3x.server.http.router.Router;

import java.io.File;

import static com.github.demonh3x.server.http.router.dsl.Routes.*;
import static com.github.demonh3x.server.http.router.dsl.Routes.Method.*;

public class EntryPoint {
    public static RequestHandler createWith(Configuration config) {
        Redirect redirectHome = new Redirect(getHomeUri(config));
        AccessEntity accessToFormEntity = new AccessEntity();
        AvailableOptions respondAvailableOptions = new AvailableOptions();
        EchoQuery echoQueryParameters = new EchoQuery();
        BasicAuthentication haveProtectedAccessToLogs = new BasicAuthentication(new Logs());

        File root = new File(config.getDirectory());
        ReadRequestedFile readRequestedFile = new ReadRequestedFile(root);
        UpdateRequestedFile updateRequestedFile = new UpdateRequestedFile(root);

        return new Router(
                new NotAllow(),
                onAnyMethodTo("/method_options").will(respondAvailableOptions),
                onAnyMethodTo("/form").will(accessToFormEntity),
                on(GET, "/redirect").will(redirectHome),
                on(GET, "/parameters").will(echoQueryParameters),
                on(GET, "/logs").will(haveProtectedAccessToLogs),
                on(GET).will(readRequestedFile),
                on(PATCH).will(updateRequestedFile)
        );
    }

    private static String getHomeUri(Configuration config) {
        return String.format("http://%s:%d/", config.getHost(), config.getPort());
    }
}
