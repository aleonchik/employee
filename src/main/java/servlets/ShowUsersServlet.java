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

@WebServlet("/showdata")
public class ShowUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String QUERY = "select " +
         "id, name, email, mobile, gender, dob, city " +
         "from employee";

       
    public ShowUsersServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		res.getWriter().append("Served at: ").append(req.getContextPath());
	   PrintWriter pw = res.getWriter();
      pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
      
      pw.println("<marquee>\n"
            + "      <h2 class=\"text-primary\">User data</h2>\n"
            + "   </marquee>");
      
      res.setContentType("text/html");
      
      try {
         Class.forName("org.h2.Driver");
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      try (Connection conn = DriverManager.getConnection("jdbc:h2:file:~/h2db/demo", "sa", "");
            PreparedStatement ps = conn.prepareStatement(QUERY)) {
         ResultSet rs = ps.executeQuery();
         
         pw.println("<div style='margin: auto; width:85%; margin-top: 50px;'>");
         
         pw.println("<table class='table table-hover table-striped'>");
         
         pw.println("<tr>" +
               "<th>ID</th>" +
               "<th>Name</th>" +
               "<th>EMail</th>" +
               "<th>Mobile</th>" +
               "<th>Gender</th>" +
               "<th>DOB</th>" +
               "<th>City</th>" +
               "<th>Edit</th>" +
               "<th>Delete</th>" +
               "</tr>");
         
         while (rs.next()) {
            pw.println("<tr>\n" +
            "<td>" + rs.getInt(1) + "</td>\n" +
            "<td>" + rs.getString(2) + "</td>\n" +
            "<td>" + rs.getString(3) + "</td>\n" +
            "<td>" + rs.getString(4) + "</td>\n" +
            "<td>" + rs.getString(5) + "</td>\n" +
            "<td>" + rs.getString(6) + "</td>\n" +
            "<td>" + rs.getString(7) + "</td>\n" +
            "<td> <a href='editurl?id=" + rs.getInt(1) + "'><b>Edit</b></a></td>\n" +
            "<td> <a href='deleteurl?id=" + rs.getInt(1) + "'><b>Delete</b></a></td>\n" +
            "</tr>");
         }
         
         pw.println("<table>");
         
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

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
