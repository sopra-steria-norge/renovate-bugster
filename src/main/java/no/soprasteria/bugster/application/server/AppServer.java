package no.soprasteria.bugster.application.server;

import no.soprasteria.bugster.infrastructure.server.EmbeddedWebAppContext;
import no.soprasteria.bugster.infrastructure.server.ServerUtil;
import no.soprasteria.bugster.infrastructure.util.IOUtil;
import no.soprasteria.bugster.infrastructure.util.LogUtil;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.MovedContextHandler;
import org.eclipse.jetty.server.handler.ShutdownHandler;
import org.eclipse.jetty.util.security.Constraint;

import java.io.File;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AppServer {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppServer.class);
    private AppConfig config;
    private Server server;

    private AppServer(AppConfig config) {
        this.config = config;
    }

    public static void main(String[] args) throws Exception {
        new File("logs").mkdirs();
        LogUtil.setupLogging("logging-simpleserverseed.xml");
        IOUtil.extractResourceFile("seedapp.properties");

        AppServer server = new AppServer(new ReloadableAppConfigFile());
        server.start();

        if (System.getProperty("startBrowser") != null) {
            Runtime.getRuntime().exec("cmd /c \"start " + server.getURI() + "\"");
        }
    }

    private void start() throws Exception {
        start(config.getHttpPort());
    }

    public void start(int port) throws Exception {
        config.start();

        server = new Server(port);
        server.setHandler(createHandlers());
        server.start();

        log.info("Started server " + server.getURI());
    }

    private Handler createHandlers() {
        LoginService loginService = new HashLoginService("MyRealm","src/test/resources/realm.properties");
        server.addBean(loginService);

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        server.setHandler(security);

        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate( true );
        constraint.setRoles(new String[]{"user", "admin"});

        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec( "/*" );
        mapping.setConstraint( constraint );

        Set<String> knownRoles = new HashSet<>();
        knownRoles.add("user");
        knownRoles.add("admin");

        security.setConstraintMappings(Collections.singletonList(mapping), knownRoles);
        security.setAuthenticator(new BasicAuthenticator());
        security.setLoginService(loginService);

        HandlerList handlers = new HandlerList();
        EmbeddedWebAppContext ctx = new EmbeddedWebAppContext("/bugster");
//        OneLoginAuthenticator authenticator = new OneLoginAuthenticator("default", Collections.singletonList("bruker"));
//        ctx.getSecurityHandler().setAuthenticator(authenticator);
//        ctx.getSecurityHandler().setLoginService(authenticator.getLoginService());
        handlers.addHandler(new ShutdownHandler("sgds", false, true));
        handlers.addHandler(new EmbeddedWebAppContext("/bugster"));
        handlers.addHandler(new MovedContextHandler(null, "/", "/bugster"));
        return ServerUtil.createStatisticsHandler(
                ServerUtil.createRequestLogHandler(handlers));
    }

    private URI getURI() {
        return server.getURI();
    }

}

