package com.mecanix.misael.mecanix;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MessageEnvelope {
	private URL direction;
	private HttpURLConnection connection;
	private boolean secure = true;
	private String godSignatureAuth; // This variable stores the authkey base64 coded
	
	public MessageEnvelope(String url) throws Exception{
		Init(url);
	}
	
	private void Init(String url) throws Exception{
		direction = new URL(url);
		connection = (HttpURLConnection) direction.openConnection();
	}
	
	public String sendVars(String[] vars)throws Exception{
		String inputLine;
		StringBuffer response = new StringBuffer();
		if(secure){
			connection.setRequestMethod("GET");
			//connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			//connection.setRequestProperty("Authorization", "Basic " + godSignatureAuth);
			connection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(compileVars(vars));
			wr.flush();
			wr.close();
		}else{
			connection.setRequestMethod("GET");
		}
		BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
	
	public String sendVars(String vars)throws Exception{
		String inputLine;
		StringBuffer response = new StringBuffer();
		if(secure){
			connection.setRequestMethod("POST");//cambios*
			//connection.setRequestMethod("GET");//cambios*
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			connection.setRequestProperty("Authorization", "Basic " + godSignatureAuth);
			connection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(vars);
			wr.flush();
			wr.close();
		}else{
			connection.setRequestMethod("GET");
		}
		BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
	
	private String compileVars(String[] vars){
		String result = "";
		for(int i = 0; i<vars.length; i++){
			result+=vars[i];
		}
		return result;
	}
	
	public void setGodSignature(String godSignature){
		this.godSignatureAuth = godSignature;
	}
}
