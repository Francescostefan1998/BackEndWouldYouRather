package it.betacom.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import it.betacom.connection.Database;

/**
 * Servlet implementation class GetQuestions
 */
@WebServlet("/GetQuestions")
public class GetQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetQuestions() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
		JSONArray jsonArray = new JSONArray();
		try {
			Connection con = Database.getConnection();
			String questionParam = request.getParameter("questionId");

			if (questionParam != null) {
				int questionId = Integer.parseInt(questionParam);
				String sql = "select * from questions where questionId = ?";

				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, questionId);
				ResultSet rs = ps.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				while (rs.next()) {
					JSONObject jsonObject = new JSONObject();
					for (int i = 1; i <= columnCount; i++) {
						String columnName = rsmd.getColumnName(i);
						Object value = rs.getObject(columnName);
						jsonObject.put(columnName, value);
					}
					jsonArray.put(jsonObject);
				}
				System.out.println(jsonArray.toString());

				response.setContentType("application/json");
				response.getWriter().write(jsonArray.toString());
			} else {
				System.out.println("You haven't provided the questionId param");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
			response.getWriter().write("Invalid 'questionId' parameter");

			// Handle the case where the "questionId" parameter cannot be parsed as an
			// integer
			// You may want to send an error response or handle it in another way.
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
		try {
			Connection con = Database.getConnection();
			String sql = "insert into questions (question_text, time_selected) values (?, 0)";

			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, request.getParameter("question"));

				int rowsAffected = ps.executeUpdate();
				if (rowsAffected <= 0) {
					// Handle the case where no rows were affected
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
	    
	    try {
	        Connection con = Database.getConnection();
	        String questionIdParam = request.getParameter("questionId");
	        String questionTextParam = request.getParameter("questionText");

	        if (questionIdParam != null && questionTextParam != null) {
	            int questionId = Integer.parseInt(questionIdParam);
	            int questionText = Integer.parseInt(questionTextParam);
	            String sql = "";
	            if (questionText == 1) {
	                sql = "UPDATE questions SET time_selected_first_question = time_selected_first_question + 1 WHERE questionId = ?";
	            } else if (questionText == 2) {
	                sql = "UPDATE questions SET time_selected_second_question = time_selected_second_question + 1 WHERE questionId = ?";
	            } else {
	                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	                response.getWriter().write("The 'questionText' param must be 1 or 2");
	                return;
	            }

	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setInt(1, questionId);
	            int updatedRows = ps.executeUpdate();

	            response.setContentType("application/json");
	            response.getWriter().write("{\"updatedRows\": " + updatedRows + "}");
	        } else {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	            response.getWriter().write("You haven't provided the required parameters");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
	        response.getWriter().write("Database error");
	    } catch (NumberFormatException e) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	        response.getWriter().write("Invalid parameter values");
	    }
	}

}
