package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdminMentors implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/adminMentors.twig");
        JtwigModel model = JtwigModel.newModel();
        List<String> usernames = new ArrayList<>();
        usernames.add("Piotrek");
        usernames.add("Piotrek2");
        usernames.add("Piotrek3");
        model.with("name", usernames);
        model.with("i", usernames.size());
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}