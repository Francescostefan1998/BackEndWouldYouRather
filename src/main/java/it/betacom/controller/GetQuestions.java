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
			String sql = "select * from questions";

			PreparedStatement ps = con.prepareStatement(sql);

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

		} catch (SQLException e) {
			e.printStackTrace();
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
	                if(rowsAffected <= 0) {
	                    // Handle the case where no rows were affected
	                }
	            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
