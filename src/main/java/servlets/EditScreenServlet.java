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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/editurl")
public class EditScreenServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final String QUERY = "select " + "id, name, email, mobile, gender, dob, city " + "from employee "
         + "where id=?";
//   private static final String QUERY_UPD = "update employee "
//         + "set name=?, email=?, mobile=?, gender=?, dob=?, city=? " + "where id=?";

   public EditScreenServlet() {
      super();
   }

   protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
      PrintWriter pw = res.getWriter();

      int id = Integer.parseInt(req.getParameter("id"));

      res.setContentType("text/html");
      pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");

      try {
         Class.forName("org.h2.Driver");

      } catch (Exception e) {
         e.printStackTrace();
      }

      pw.println("<div class='card' style='margin:auto; width:60%; margin-top:100px'>");

      try (Connection conn = DriverManager.getConnection("jdbc:h2:file:~/h2db/demo", "sa", "");
            PreparedStatement ps = conn.prepareStatement(QUERY)) {

         ps.setInt(1, id);

         ResultSet rs = ps.executeQuery();

         if (rs.next()) {
            String name = rs.getString("name");
            String email = rs.getString("email");
            String mobile = rs.getString("mobile");
            String gender = rs.getString("gender");
            String dob = rs.getString("dob");
            String city = rs.getString("city");

            pw.println("<form method='post' action='edit?id=+" + id + "  '>");
            pw.println("<table class='table table-hover table-striped'>");
            pw.println("<tr>" + 
                  "<td>Name</td> " + 
                  "<td><input type='text' name='name' value='" + name + "'></td> " +
                  "</tr>");
            pw.println("<tr>" + 
                  "<td>EMail</td> " + 
                  "<td><input type='text' name='name' value='" + email + "'></td> " +
                  "</tr>");
            pw.println("<tr>" +
                  "<td>Mbobile</td> " + 
                  "<td><input type='text' name='name' value='" + mobile + "'></td> " + 
                  "</tr>");
            pw.println("<tr>" + 
                  "<td>Gender</td> " + 
                  "<td><input type='text' name='name' value='" + gender + "'></td> " + 
                  "</tr>");
            pw.println("<tr>" + 
                  "<td>DOB</td> " + 
                  "<td><input type='date' name='name' value='" + dob + "'></td> " + 
                  "</tr>");
            pw.println("<tr>" + 
                  "<td>City</td> " + 
                  "<td><input type='text' name='name' value='" + city + "'></td> " + 
                  "</tr>");
            pw.println("<tr>" + 
                  "<td><button type='submit' class='btn btn-outline-success'>Update</button></td>" +
                  "<td><button type='reset' class='btn btn-outline-danger'>Cancel</button></td>" + 
                  "</tr>");
            pw.println("</table>");
            pw.println("</form>");
         } else {
            pw.println("<h2 class='bg-danger text-light text-center'>NO record</h2>");
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

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      doGet(request, response);
   }

}
