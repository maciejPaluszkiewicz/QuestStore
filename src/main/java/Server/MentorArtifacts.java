package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class MentorArtifacts extends LogIn implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (isCookieTypeAsAcces("mentor", httpExchange)) {
                sendResponse(createResponse(),httpExchange);
            }
            else {
                loadLoginSite(httpExchange);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String createResponse() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorartifacts.twig");
        JtwigModel model = JtwigModel.newModel();
        return template.render(model);
    }
}