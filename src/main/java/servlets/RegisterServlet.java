package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private final static String QUERY = "insert into " +
         "employee (name, email, mobile, gender, dob, city) " +
         "values   (?,    ?,     ?,      ?,      ?,   ?)";

   public RegisterServlet() {
      super();
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse res) 
         throws ServletException, IOException {
//      res.getWriter().append("Served at: ").append(request.getContextPath());
      
      
      PrintWriter pw = res.getWriter();
      pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
      
      res.setContentType("text/html");
      
      String name = req.getParameter("userName");
      String email = req.getParameter("email");
      String mobile = req.getParameter("mobile");
      String dob = req.getParameter("dob");
      String gender = req.getParameter("gender");
      String city = req.getParameter("city");
      
//      Connection conn;
      
      try {
         Class.forName("org.h2.Driver");
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      pw.println("<div class='card' style='margin:auto; width:300px; margin-top:100px'>");
      
      try (Connection conn = DriverManager.getConnection("jdbc:h2:file:~/h2db/demo", "sa", "");
            PreparedStatement ps = conn.prepareStatement(QUERY)) {
         ps.setString(1, name);
         ps.setString(2, email);
         ps.setString(3, mobile);
         ps.setString(4, gender);
         ps.setString(5, dob);
         ps.setString(6, city);
         
         int count = ps.executeUpdate();
         
         if (count == 1) {
            pw.println("<h2 class='bg-danger text-light text-center'>Registr successfully</h2>");
         } else {
            pw.println("<h2 class='bg-danger text-light text-center'>User is not a registered</h2>");
         }
      } catch (SQLException e) {
         pw.println("<h2 class='bg-danger text-light text-center'>" + e.getMessage() + "</h2>");
         e.printStackTrace();
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      
      pw.println("<a href='index.html'><button class='btn btn-outline-success'>Home</button></a>");
      pw.println("</div>");
      pw.close();
      
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse res)
         throws ServletException, IOException {
      doGet(req, res);
      
   }

}
