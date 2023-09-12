package it.betacom.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Servlet implementation class GetAiResponse
 */
@WebServlet("/GetAiResponse")
public class GetAiResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
	private static final String URL = "https://api.openai.com/v1/chat/completions";
	private static final OkHttpClient client = new OkHttpClient();
	private String OPENAI_API_KEY;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		super.init();

		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("dbConf.properties")) {
			if (input == null) {
				throw new ServletException("Unable to find apikey.properties");
			}
			properties.load(input);
			OPENAI_API_KEY = properties.getProperty("OPENAI_API_KEY");

			if (OPENAI_API_KEY == null) {
				throw new ServletException("OPENAI_API_KEY not set in dbConf.properties");
			}
		} catch (IOException ex) {
			throw new ServletException("Error reading OPENAI_API_KEY", ex);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// CORS headers
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");

		StringBuilder sb = new StringBuilder();
		String line;

		try (BufferedReader reader = req.getReader()) {
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		}

		JSONObject jsonObject = new JSONObject(sb.toString());
		String question = jsonObject.getString("question");

		Request request = new Request.Builder().url(URL).post(RequestBody.create(createRequestBody(
				"give me a 'would you rather' type of question without starting with 'would you rather', and separate the 2 choices with a '/' symbol (the two choices must always be present, don't advance singular question, also the '/' symbol must always be present in between, ). Make them quite endgy, not question mark, not longher than 30 words, oddly specific, and challenging to respond to."),
				JSON)).addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + OPENAI_API_KEY).build();

		Response response = client.newCall(request).execute();

		if (response.isSuccessful() && response.body() != null) {
			String responseBody = response.body().string();
			JSONObject responseObject = new JSONObject(responseBody);
			String openAIResponse = responseObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
					.getString("content");
			System.out.println(openAIResponse);
			// Split the response string
			
			try {
			    String[] splitResponses = openAIResponse.split("/", 2);
			    // rest of your logic
			 // Create the response JSON
				JSONObject jsonResponse = new JSONObject();
				jsonResponse.put("response1", cleanResponse(splitResponses[0]));

				if (splitResponses.length > 1) {
					jsonResponse.put("response2", cleanResponse(splitResponses[1]));
				}

				resp.setContentType("application/json");
				resp.getWriter().write(jsonResponse.toString());
			} catch (Exception e) {
			    e.printStackTrace();  // or log the exception
			    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Exception: " + e.getMessage());
			}
			
			
		} else {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error querying OpenAI");
		}
	}

	private String cleanResponse(String response) {
		// Remove unwanted characters and numbers
		String cleaned = response.replaceAll("[^a-zA-Z\\s,?]", "").trim();
		// If response starts with a number followed by a space, remove it
		return cleaned.replaceFirst("^[0-9]+ ", "");
	}

	private String createRequestBody(String question) {
		// Convert the given question into JSON format for the OpenAI API request.
		return "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"" + question + "\"}]}";
	}
}
