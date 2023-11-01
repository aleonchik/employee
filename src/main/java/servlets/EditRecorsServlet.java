package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/edit")
public class EditRecorsServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   
   private static final String QUERY = "update employee "
         + "set name=?, email=?, mobile=?, gender=?, dob=?, city=? " 
         + "where id=?";

   public EditRecorsServlet() {
      super();
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      PrintWriter pw = res.getWriter();

      int id = Integer.parseInt(req.getParameter("id"));
      
      String name = req.getParameter("name");
      String email = req.getParameter("email");
      String mobile = req.getParameter("mobile");
      String gender = req.getParameter("gender");
      String dob = req.getParameter("dob");
      String city = req.getParameter("city");
      
      res.setContentType("text/html");
      pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");

      try {
         Class.forName(DbName.dbDriver);

      } catch (Exception e) {
         e.printStackTrace();
      }

      pw.println("<div class='card' style='margin:auto; width:60%; margin-top:100px'>");
      
      pw.println(id); pw.println("<br>");
      pw.println(name + "<br>" + email + "<br>" + mobile + "<br>" + gender + "<br>" + dob + "<br>" + city);
      
      try (Connection conn = DriverManager.getConnection(DbName.dbName, DbName.userName, DbName.pass);
            PreparedStatement ps = conn.prepareStatement(QUERY)) {
         
         ps.setString(1, name);
         ps.setString(2, email);
         ps.setString(3, mobile);
         ps.setString(4, gender);
         ps.setString(5, dob);
         ps.setString(6, city);
         ps.setInt(7, id);
         
         int count = ps.executeUpdate();
         
         if (count == 1) {
            pw.println("<h2 class='bg-danger text-light text-center'>Record updated</h2>");
         } else {
            pw.println("<h2 class='bg-danger text-light text-center'>Record is NOT updated</h2>");
         }
      } catch (SQLException e) {
         pw.println("<h2 class='bg-danger text-light text-center'>" + e.getMessage() + "</h2>");
         e.printStackTrace();
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      
      pw.println("<a href='index.html'><button class='btn btn-outline-success'>Home</button></a> &nbsp; &nbsp;");
      pw.println("<a href='showdata'><button class='btn btn-outline-success'>Show users</button></a>");
      pw.println("</div>");
      pw.close();

   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doGet(request, response);
   }

}
