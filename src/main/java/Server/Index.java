package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import model.baseUserData;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Index implements HttpHandler {
    MentorDAO mDao = new MentorDAOSQL();
    AdminDAO aDao = new AdminDAOSQL();
    StudentDAO sDao = new StudentDAOSQL();
    SessionDAO sesDao = new SessionDAOSQL();
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if(method.equals("GET")) {
            String response = loadLoginSite();
            sendResponse(response, httpExchange);
        }
        if(method.equals("POST")){
            baseUserData user = new baseUserData();
            String url = readURL(httpExchange);
            String login = parseURL(url).get("login");
            String password = parseURL(url).get("password");
            login = login.replace("%40","@");
            System.out.println(login+" "+password);
            user=autorise(login,password);

            if(user.getId()!=null) {

                Random generator = new Random();
                int cookieId = generator.nextInt(100000) + 1;
                System.out.println("Stworzylem Id");
                sesDao.addSession(Integer.toString(cookieId),user.getType(), Integer.parseInt(user.getId()));
                String response = loadLoginSite();
                HttpCookie cookie = new HttpCookie("sessionId", Integer.toString(cookieId));
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                loadHomeSite(httpExchange);
            }
            else{
                System.out.println("WszlemTu");
                loadHomeSite(httpExchange);
                //sendResponse(response,httpExchange);
            }

        }

    }

    private void loadHomeSite(HttpExchange httpExchange) throws IOException {
        {
            httpExchange.getResponseHeaders().add("Location", "/loginn");
            httpExchange.sendResponseHeaders(302,0);
            OutputStream os = httpExchange.getResponseBody();
            os.write("".getBytes());
            os.close();
        }

    }

    private String loadLoginSite() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/index.twig");
        JtwigModel model = JtwigModel.newModel();
        return template.render(model);
    }

    private baseUserData autorise(String login, String password) {
        baseUserData user = new baseUserData();
        String admin=aDao.autoriseAdmin(login,password);
        String student=sDao.autoriseStudent(login,password);
        String mentor=mDao.autoriseMentor(login,password);
        if(admin!=null){
            user.setId(admin);
            user.setType("admin");

        }
        if(student!=null){
            user.setId(student);
            user.setType("student");
        }
        if(mentor!=null){
            user.setId(mentor);
            user.setType("mentor");
        }
        return user;

    }

    public String readURL(HttpExchange httpExchange) throws IOException{
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();

    }

    public void sendResponse(String response,HttpExchange httpExchange) throws IOException{
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public Map<String,String> parseURL(String URL) {
        Map<String,String> login = new HashMap();
        String[] pairsURL=URL.split("&");
        for(String pair : pairsURL){
            String[] keyValue = pair.split("=");
            if(keyValue.length>1){
                login.put(keyValue[0],keyValue[1]);
            }
        }
        return login;
    }
}
