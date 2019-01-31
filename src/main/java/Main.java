import Server.*;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;


public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/twig", new Jtwig());
        server.createContext("/adminmentors", new AdminMentors());
        server.createContext("/admincoins", new AdminCoins());
        server.createContext("/mentor", new Mentor());
        server.createContext("/mentorartifacts", new MentorArtifacts());
        server.createContext("/mentorquest", new MentorQuest());
        server.createContext("/mentorstudents", new MentorStudents());
        server.createContext("/shop", new Shop());
        server.createContext("/user", new User());
        server.createContext("/admin", new Admin());
        server.createContext("/static", new Static());
        server.createContext("/loginn", new LogIn());
        server.createContext("/index", new Index());
        server.setExecutor(null);
        server.start();
    }
}