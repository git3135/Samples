import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorld extends HttpServlet {
public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
response.getWriter ().print ("Hello world!!! This is a response from HelloWorld servlet...okay...");
}

}