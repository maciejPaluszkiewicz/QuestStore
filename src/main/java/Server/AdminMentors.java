package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AdminDAOSQL;
import model.Mentormod;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import util.Util;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AdminMentors extends LogIn implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (isCookieTypeAsAcces("admin", httpExchange)) {
                JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/adminMentors.twig");
                JtwigModel model = JtwigModel.newModel();
                List<Mentormod> mentormodList = new ArrayList<>();
                AdminDAOSQL adminDAOSQL = new AdminDAOSQL();
                String method = httpExchange.getRequestMethod();
                if (method.equals("POST")) {
                    String formData = Util.getFormData(httpExchange);
                    Map<String, String> inputs = Util.parseFormData(formData);
                    if (inputs.size() == 4) {
                        adminDAOSQL.createMentor(inputs.get("name"), inputs.get("surname"), inputs.get("email"), inputs.get("phonenumber"), "123");
                    } else if (inputs.size() == 1) {
                        adminDAOSQL.removeMentorById(inputs.get("id"));
                    }
                }

                mentormodList = adminDAOSQL.getMentors();
                sendResponse(httpExchange, template, model, mentormodList);

            }
            else{
                loadLoginSite(httpExchange);
            }
        }catch (SQLException e){
        }
    }
    private void sendResponse(HttpExchange httpExchange, JtwigTemplate template, JtwigModel model, List<Mentormod> mentormodList) throws IOException {
        model.with("mentors", mentormodList);
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}