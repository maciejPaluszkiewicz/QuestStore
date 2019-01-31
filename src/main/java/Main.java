import Server.Admin;
import Server.AdminMentors;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;


public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/twig", new Jtwig());
        server.createContext("/adminmentors", new AdminMentors());
        server.createContext("/admin", new Admin());
        server.createContext("/static", new Static());
        server.setExecutor(null);
        server.start();
    }
}