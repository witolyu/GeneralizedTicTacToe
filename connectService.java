import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author L the format of move is move = "4,4"; type = ""move";
 */
public class connectService {
	final String url = "http://www.notexponential.com/aip2pgaming/api/index.php";

	public static void main(String[] args) throws Exception {
		connectService http = new connectService();
		http.sendGet();
		http.sendPost();
	}

	private void sendGet() throws Exception {
		String getUrl = url + "?type=team&teamId=1051"; // test get members of team
		URL obj = new URL(getUrl);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
		// add request header
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setUseCaches(false);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("x-api-key", "d80b173eaf8d09e75c58");
		conn.setRequestProperty("userid", "247");
		conn.setRequestProperty("charset", "utf-8");
		
		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}

	// HTTP POST request
	private void sendPost() throws Exception {
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		// String parameters = creatMovePara(type, gameId, teamId, move);
		String parameters = "teamId=1051&userId=247&type=member"; // use creatMovePara() to build new move 
		byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		
		// add request header
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("x-api-key", "d80b173eaf8d09e75c58");
		conn.setRequestProperty("userid", "247");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length",
				Integer.toString(postDataLength));

		// Send post request

		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(parameters);
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + parameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

		/*
		 * Reader in = new BufferedReader(new
		 * InputStreamReader(conn.getInputStream(), "UTF-8")); for (int c; (c =
		 * in.read()) >= 0;){ System.out.print((char)c); }
		 */
	}

	public static String creatMovePara(String type, String gameId,
			String teamId, String move) throws UnsupportedEncodingException {
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("type", type);
		params.put("gameId", gameId);
		params.put("teamId", teamId);
		params.put("move", move);
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
					"UTF-8"));
		}
		return postData.toString();
	}
}
