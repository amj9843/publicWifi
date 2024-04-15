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
import common_modules.utils;

public class bookmark_service extends repository_service {
	public ArrayList<Map<String, Object>> getBookmarkGroups() throws SQLException {
		ArrayList<Map<String, Object>> bookmarkGroups = new ArrayList<>();
		
		Connection conn = db.connectDB();
		conn.setAutoCommit(false);
		
		repository_service repositoryService = new repository_service();
		bookmarkGroups = repositoryService.getBookmarkGroups();
		
		db.close();
		
		return bookmarkGroups;
	}
	
	public Map<String, Object> getBookmarkGroup(int id) throws SQLException {
		Map<String, Object> bookmarkGroup = new HashMap<>();
		
		Connection conn = db.connectDB();
		conn.setAutoCommit(false);
		
		repository_service repositoryService = new repository_service();
		bookmarkGroup = repositoryService.getBookmarkGroup(id);
		
		db.close();
		
		return bookmarkGroup;
	}
	
	public int deleteBookmarkGroup(int id) throws SQLException {
		Connection conn = db.connectDB();
		conn.setAutoCommit(false);
		
		repository_service repositoryService = new repository_service();
		
		int result = repositoryService.deleteBookmarkGroup(id);
		
		db.close();
		return result;
	}
	
	public String addBookmarkGroup(String name, int seqNum) throws SQLException {
		Connection conn = db.connectDB();
		conn.setAutoCommit(false);
		
		repository_service repositoryService = new repository_service();
		String registTime = utils.nowTime();
		
		int result = repositoryService.addBookmarkGroup(name, seqNum, registTime);
		
		db.close();
		if (result == 1) {
			return "북마크 그룹이 추가되었습니다.";
		} else if (result == 0) {
			return "이미 해당 그룹에 같은 이름이 있습니다.";
		} else {
			return "북마크 그룹 생성에 실패하였습니다.";
		}
	}
	
	public String addBookmark(int bookmarkId, int wifiId) throws SQLException {
		Connection conn = db.connectDB();
		conn.setAutoCommit(false);
		
		repository_service repositoryService = new repository_service();
		String registTime = utils.nowTime();
		
		int result = repositoryService.addBookmark(bookmarkId, wifiId, registTime);
		
		db.close();
		if (result == 1) {
			return "북마크가 추가되었습니다.";
		} else if (result == 0) {
			return "이미 해당 그룹에 추가되어있는 와이파이 정보입니다.";
		} else {
			return "북마크 추가에 실패하였습니다.";
		}
	}
}
