package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AdminDAOSQL;
import model.Mentor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import util.Util;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminMentors implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/adminMentors.twig");
        JtwigModel model = JtwigModel.newModel();
        List<Mentor> mentorList = new ArrayList<>();
        AdminDAOSQL adminDAOSQL = new AdminDAOSQL();
        String method = httpExchange.getRequestMethod();
        if (method.equals("POST")){
            String formData = Util.getFormData(httpExchange);
            Map<String, String> inputs = Util.parseFormData(formData);
            if (inputs.size() == 4){
                adminDAOSQL.createMentor(inputs.get("name"),inputs.get("surname"),inputs.get("email"),inputs.get("phonenumber"),"123");
            }else if(inputs.size() == 1){
                adminDAOSQL.removeMentorById(inputs.get("id"));
            }
        }

        mentorList = adminDAOSQL.getMentors();
        sendResponse(httpExchange, template, model, mentorList);

    }

    private void sendResponse(HttpExchange httpExchange, JtwigTemplate template, JtwigModel model, List<Mentor> mentorList) throws IOException {
        model.with("mentors", mentorList);
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
