package common_modules;

import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;

public class wifi_api {
	private static String apiUrl="http://openapi.seoul.go.kr:8088"; //input apiUrl(ex : http://openapi.seoul.go.kr:8088)
	private static String userKey="sample"; //input your api-key
	private static String resultType="json";
	private static String apiType="TbPublicWifiInfo";
	private static OkHttpClient okHttpClient = new OkHttpClient();
	
	public static String makeUrl(int start, int end) {
		StringBuilder url = new StringBuilder();
		url.append(apiUrl).append("/")
		.append(userKey).append("/")
		.append(resultType).append("/")
		.append(apiType).append("/")
		.append(String.valueOf(start)).append("/").append(String.valueOf(end));
		
		return url.toString();
	}
	
	public static int getTotalCnt() throws IOException {
		int totalCnt = 0;
		try {
			URL url = new URL(makeUrl(1, 1));
			
			Request.Builder builder = new Request.Builder().url(url).get();
			Response response = okHttpClient.newCall(builder.build()).execute();
			
			if (response.isSuccessful()) {
				ResponseBody responseBody = response.body();
				
				if (responseBody != null) {
					JsonElement elements = JsonParser.parseString(responseBody.string());

                    totalCnt = elements.getAsJsonObject().get("TbPublicWifiInfo")
                                     .getAsJsonObject().get("list_total_count")
                                     .getAsInt();
				}
			} else System.out.println("ERROR: " + response.code());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return totalCnt;
	}
	
	public static ArrayList<JsonObject> getAllDatas() throws IOException {
		int totalCnt = getTotalCnt();
		int pageNum = totalCnt/1000;
		int lastNum = totalCnt%1000;
		
		ArrayList<JsonObject> wifiLists = new ArrayList<>();
		
		for (int i = 0; i <= pageNum; i++) {
			int start = i*1000 + 1;
			int end = (i == pageNum) ? start + lastNum - 1 : (i+1)*1000;
			
			try {
				URL url = new URL(makeUrl(start, end));
				
				Request.Builder builder = new Request.Builder().url(url).get();
				Response response = okHttpClient.newCall(builder.build()).execute();
				
				if (response.isSuccessful()) {
					ResponseBody responseBody = response.body();
					
					if (responseBody != null) {
						JsonElement elements = JsonParser.parseString(responseBody.string());
						
						JsonArray arr = elements.getAsJsonObject().get("TbPublicWifiInfo")
                                .getAsJsonObject().get("row")
                                .getAsJsonArray();

	                    for(JsonElement obj: arr) {
	                    	wifiLists.add(obj.getAsJsonObject());
	                    }
					}
				} else System.out.println("ERROR: " + response.code());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		return wifiLists;
	}
}
