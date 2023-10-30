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

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String QUERY = "delete from employee where id = ?";
       
    public DeleteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	   PrintWriter pw = res.getWriter();
	   
	   int id = Integer.parseInt(req.getParameter("id"));
	   
	   res.setContentType("text/html");
      pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
      
      try {
         Class.forName(DbName.dbDriver);
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      pw.println("<div class='card' style='margin:auto; width:60%; margin-top:100px'>");
      
      try (Connection conn = DriverManager.getConnection(DbName.dbName, DbName.userName, DbName.pass);
            PreparedStatement ps = conn.prepareStatement(QUERY)) {
         ps.setInt(1, id);
         
         int count = ps.executeUpdate();
         
         if (count == 1) {
            pw.println("<h2 class='bg-danger text-light text-center'>Record deleted</h2>");
         } else {
            pw.println("<h2 class='bg-danger text-light text-center'>Record is NOT deleted</h2>");
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
