package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import common_modules.db;
import common_modules.utils;

public class history_service extends repository_service{
	public static void insertHistoryInfo(double latitude, double longitude) throws SQLException {
		Connection conn = db.connectDB();
		conn.setAutoCommit(false);
		
		repository_service repositoryService = new repository_service();
		String execTime = utils.nowTime();
		
		repositoryService.insertHistoryInfo(latitude, longitude, execTime);

		db.close();
	}
	
	public ArrayList<Map<String, Object>> getHistoryInfos() throws SQLException {
		ArrayList<Map<String, Object>> historyInfos = new ArrayList<>();
		
		Connection conn = db.connectDB();
		conn.setAutoCommit(false);
		
		repository_service repositoryService = new repository_service();
		historyInfos = repositoryService.getHistoryInfos();
		
		db.close();
		
		return historyInfos;
	}
	
	public void deleteHistoryInfo(int id) throws SQLException {
		Connection conn = db.connectDB();
		conn.setAutoCommit(false);
		
		repository_service repositoryService = new repository_service();
		
		repositoryService.deleteHistoryInfo(id);
		db.close();
	}
}
