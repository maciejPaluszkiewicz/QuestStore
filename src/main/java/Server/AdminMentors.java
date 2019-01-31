package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AdminDAOSQL;
import model.Mentor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

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
            String formData = getFormData(httpExchange);
            Map<String, String> inputs = parseFormData(formData);
            String id = inputs.get("id");
            String name = inputs.get("name");
            String surname = inputs.get("surname");
            String email = inputs.get("email");
            String phonenumber = inputs.get("phonenumber");
            String password = "123";
            if (inputs.size() == 4){
                adminDAOSQL.createMentor(name,surname,email,phonenumber,password);
            }else if(inputs.size() == 1){
                adminDAOSQL.removeMentorById(id);
            }
        }

        mentorList = adminDAOSQL.getMentors();
        model.with("mentors", mentorList);
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
    private String getFormData(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();
    }
    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length > 1) {
                String value = new URLDecoder().decode(keyValue[1], "UTF-8");
                map.put(keyValue[0], value);
            }
        }
        return map;
    }
}
