package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.MentorDAOSQL;
import model.Student;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import util.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MentorStudents implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorstudents.twig");
        JtwigModel model = JtwigModel.newModel();
        List<Student> studentList = new ArrayList<>();
        MentorDAOSQL mentorDAOSQL = new MentorDAOSQL();
        String method = httpExchange.getRequestMethod();
        if (method.equals("POST")){
            String formData = Util.getFormData(httpExchange);
            Map<String,String> inputs = Util.parseFormData(formData);
            if(inputs.size() == 7){
                mentorDAOSQL.createStudent(inputs.get("name"),inputs.get("surname"),inputs.get("class"),inputs.get("email"),inputs.get("phonenumber"),Integer.parseInt(inputs.get("coolcoins")),Integer.parseInt(inputs.get("level")),"123");
            }else if (inputs.size() == 1){
                mentorDAOSQL.removeStudentById(inputs.get("id"));
            }
        }
        studentList = mentorDAOSQL.showStudents();
        model.with("students",studentList);
        String response = template.render(model);
        sendResponse(httpExchange, response);

    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}