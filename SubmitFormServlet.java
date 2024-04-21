import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SubmitFormServlet")
public class SubmitFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    String depName = request.getParameter("dep_name");
    String faculty = request.getParameter("facaulty");
    String classroomNo = request.getParameter("classroom_no");
    
    // Initialize database connection
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String URL = "jdbc:mysql://localhost:3306/msk";
        String USERNAME = "root";
        String PASSWORD = "root";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Prepare SQL statement
            String sql = "INSERT INTO departments (dep_name, facaulty, classroom_no) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set parameters
                statement.setString(1, depName);
                statement.setString(2, faculty);
                statement.setString(3, classroomNo);
                
                // Execute query
//                int rowsAffected = statement.executeUpdate();
                statement.executeUpdate();
                
                // Redirect to doGet to display department details
                response.sendRedirect(request.getContextPath() + "/SubmitFormServlet");
            }
        }
    } catch (ClassNotFoundException | SQLException e) {
        // Handle database errors
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}






    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> departmentDetails = new ArrayList<>();
        try {
       	 Class.forName("com.mysql.cj.jdbc.Driver");
         String URL = "jdbc:mysql://localhost:3306/msk";
         String USERNAME = "root";
         String PASSWORD = "root";
         try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String sql = "SELECT dep_name, facaulty, classroom_no FROM departments";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        String depName = resultSet.getString("dep_name");
                        String faculty = resultSet.getString("facaulty");
                        String classroomNo = resultSet.getString("classroom_no");
                        departmentDetails.add("Department Name: " + depName + ", Faculty: " + faculty
                                + ", Classroom No: " + classroomNo);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");
        out.println("<h2>Department Details:</h2>");
        for (String detail : departmentDetails) {
            out.println("<p>" + detail + "</p>");
        }
        out.println("</body></html>");
    }
}

