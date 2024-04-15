package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import common_modules.db;
import common_modules.wifi_api;

public class wifi_service extends repository_service {
	public static int insertWifiInfos() throws SQLException {
		ArrayList<JsonObject> datas = null;
		
		Connection conn = db.connectDB();
		conn.setAutoCommit(false);
		
		repository_service repositoryService = new repository_service();
		
		try {
			datas = wifi_api.getAllDatas();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (datas == null) return 0;
		int count = datas.size();
		//기존 데이터 삭제
		repositoryService.clearTable("BOOKMARK_LIST");
		repositoryService.clearTable("WIFI_INFO");
		
		//불러온 데이터 저장
//		for(JsonObject data: datas) {
//			repositoryService.insertWifiInfo(data);
//			count++;
//			conn.commit();
//		}
		repositoryService.insertWifiInfos(datas);
		db.close();
		
		return count;
	}
	
	public ArrayList<Map<String, Object>> getNearWifiInfos(double latitude, double longitude) throws SQLException {
		ArrayList<Map<String, Object>> nearWifiInfos = new ArrayList<>();
		
		Connection conn = db.connectDB();
		conn.setAutoCommit(false);
		
		repository_service repositoryService = new repository_service();
		nearWifiInfos = repositoryService.getNearWifiInfos(latitude, longitude);
		
		db.close();
		
		return nearWifiInfos;
	}
	
	public Map<String, Object> getWifiInfo(String managementId) throws SQLException {
		Map<String, Object> wifiInfo = new HashMap<>();
		
		Connection conn = db.connectDB();
		conn.setAutoCommit(false);
		
		repository_service repositoryService = new repository_service();
		wifiInfo = repositoryService.getWifiInfo(managementId);
		
		db.close();
		
		return wifiInfo;
	}
}
