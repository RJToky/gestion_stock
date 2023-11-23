package util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import remote.RemoteStock;

public class Remote {
    public static RemoteStock stock() {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        try {
            Context context = new InitialContext(env);
            RemoteStock app = (RemoteStock) context
                    .lookup("ejb:back_stock/back_stock.jar/SessionBean!remote.RemoteStock");
            return app;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
