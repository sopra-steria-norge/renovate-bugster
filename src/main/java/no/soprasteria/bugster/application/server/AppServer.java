package no.soprasteria.bugster.application.server;

import no.soprasteria.bugster.infrastructure.server.EmbeddedWebAppContext;
import no.soprasteria.bugster.infrastructure.server.ServerUtil;
import no.soprasteria.bugster.infrastructure.util.IOUtil;
import no.soprasteria.bugster.infrastructure.util.LogUtil;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.MovedContextHandler;
import org.eclipse.jetty.server.handler.ShutdownHandler;

import java.io.File;
import java.net.URI;

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
        HandlerList handlers = new HandlerList();
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

