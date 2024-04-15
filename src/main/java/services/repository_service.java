package services;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import common_modules.db;
import common_modules.utils;

public class repository_service {
	private static int OPT_BATCH_SIZE = 700;
	private static Connection conn;
	private PreparedStatement pstmt;
	
	public repository_service() {
		conn = db.conn;
	}

    public boolean checkTable(String tableName) throws SQLException {
    	DatabaseMetaData data = conn.getMetaData();
		
		//테이블 목록 조회
		ResultSet tables = data.getTables(null, null, tableName, null);
		
		return tables.next() ? tables.getRow() != 0 : false;
    }
    
    public ArrayList<Map<String, Object>> getHistoryInfos() throws SQLException {
    	if (!checkTable("HISTORY")) {
    		createTable("HISTORY");
    	}
    	
    	ArrayList<Map<String, Object>> historyInfos = new ArrayList<>();
    	String SQL = "SELECT * FROM HISTORY ORDER BY ID DESC;";
    	
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while (rs.next()) {
    			Map<String, Object> history = new HashMap<>();
    			history.put("id", rs.getInt("ID"));
    			history.put("latitude", rs.getDouble("LATITUDE"));
    			history.put("longitude", rs.getDouble("LONGITUDE"));
    			history.put("execTime", rs.getString("EXEC_TIME"));
    			
    			historyInfos.add(history);
    		}
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	return historyInfos;
    }
    
    public void insertHistoryInfo(double latitude, double longitude, String execTime) throws SQLException {
    	if (!checkTable("HISTORY")) {
    		createTable("HISTORY");
    	}
    	
    	String SQL = "INSERT INTO HISTORY (LATITUDE, LONGITUDE, EXEC_TIME) VALUES (?, ?, ?);";
    	
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		
    		pstmt.setDouble(1, latitude);
    		pstmt.setDouble(2, longitude);
    		pstmt.setString(3, execTime);
    		
    		pstmt.executeUpdate();
    		
    		conn.commit();
    		System.out.println("위치 히스토리 저장 완료");
    	} catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteHistoryInfo(int id) throws SQLException {
    	String SQL = "DELETE FROM HISTORY WHERE 1=1 AND ID = ?";
    	
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		
    		pstmt.setInt(1, id);
    		
    		pstmt.executeUpdate();
    		conn.commit();
    		
    		System.out.printf("%d번 위치 조회 이력 삭제 완료\n", id);
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	} finally  {
            // PreparedStatement 종료
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch ( SQLException e ) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void insertWifiInfos(ArrayList<JsonObject> arr) throws SQLException {
    	String basic = "INSERT INTO WIFI_INFO ("
    			+ "MANAGEMENT_ID, WIFI_NAME, LATITUDE, LONGITUDE, STATE, ADDRESS, ADDRESS_DETAIL, INSTALL_FLOOR, INSTALL_TYPE,"
    			+ "INSTALL_AGENCY, SERVICE_TYPE, NETWORK_TYPE, INSTALL_YEAR, INDOOR_OR_OUTDOOR, CONNECTION_ENVIRONMENT, WORKED_TIME"
    			+ ") VALUES ";
    	
    	ArrayList<String> values = new ArrayList<>();
    	try {
    		for(int i = 0; i < arr.size(); i++ ) {
                JsonObject data = arr.get(i);
                
                StringBuilder rowValue = new StringBuilder("('").append(utils.changeColon(data.get("X_SWIFI_MGR_NO").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("X_SWIFI_MAIN_NM").getAsString())).append("', ")
                		.append(utils.changeColon(data.get("LAT").getAsString())).append(", ")
                		.append(utils.changeColon(data.get("LNT").getAsString())).append(", \'")
                		.append(utils.changeColon(data.get("X_SWIFI_WRDOFC").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("X_SWIFI_ADRES1").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("X_SWIFI_ADRES2").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("X_SWIFI_INSTL_FLOOR").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("X_SWIFI_INSTL_TY").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("X_SWIFI_INSTL_MBY").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("X_SWIFI_SVC_SE").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("X_SWIFI_CMCWR").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("X_SWIFI_CNSTC_YEAR").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("X_SWIFI_INOUT_DOOR").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("X_SWIFI_REMARS3").getAsString())).append("', \'")
                		.append(utils.changeColon(data.get("WORK_DTTM").getAsString())).append("')");
                values.add(rowValue.toString());
  
                if (values.size() == OPT_BATCH_SIZE) {
                	Statement stmt = conn.createStatement();
                	String SQL = new StringBuilder(basic).append(String.join(", ", values)).append(";").toString();
                	stmt.executeUpdate(SQL);
                	stmt.close();
                	conn.commit();
                	
                	values.clear();
                }
            }
    		
    		Statement stmt = conn.createStatement();
        	String SQL = new StringBuilder(basic).append(String.join(", ", values)).append(";").toString();
        	stmt.executeUpdate(SQL);
        	stmt.close();
        	conn.commit();
        	
        	values.clear();
    	} catch (SQLException e) {
            // 오류출력
            System.out.println(e.getMessage());
            
            try {
                conn.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            // PreparedStatement 종료
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public ArrayList<Map<String, Object>> getNearWifiInfos(double latitude, double longitude) throws SQLException {
    	if (!checkTable("WIFI_INFO")) {
    		createTable("WIFI_INFO");
    	}
    	
    	ArrayList<Map<String, Object>> nearWifiInfos = new ArrayList<>();
    	String SQL = " SELECT *, round(6371*acos(cos(radians(?))*cos(radians(LATITUDE))*cos(radians(LONGITUDE) " +
                " -radians(?))+sin(radians(?))*sin(radians(LATITUDE))), 4) AS DISTANCE " +
                " FROM WIFI_INFO ORDER BY DISTANCE LIMIT 20;";
    	
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setDouble(1, latitude);
            pstmt.setDouble(2, longitude);
            pstmt.setDouble(3, latitude);
            
    		ResultSet rs = pstmt.executeQuery();
    		
    		while (rs.next()) {
    			Map<String, Object> wifiInfo = new HashMap<>();
    			wifiInfo.put("distance", rs.getDouble("DISTANCE"));
    			wifiInfo.put("managementId", rs.getString("MANAGEMENT_ID"));
    			wifiInfo.put("state", rs.getString("STATE"));
    			wifiInfo.put("wifiName", rs.getString("WIFI_NAME"));
    			wifiInfo.put("address", rs.getString("ADDRESS"));
    			wifiInfo.put("addressDetail", rs.getString("ADDRESS_DETAIL"));
    			wifiInfo.put("installFloor", rs.getString("INSTALL_FLOOR"));
    			wifiInfo.put("installType", rs.getString("INSTALL_TYPE"));
    			wifiInfo.put("installAgency", rs.getString("INSTALL_AGENCY"));
    			wifiInfo.put("serviceType", rs.getString("SERVICE_TYPE"));
    			wifiInfo.put("networkType", rs.getString("NETWORK_TYPE"));
    			wifiInfo.put("installYear", rs.getString("INSTALL_YEAR"));
    			wifiInfo.put("indoorOrOutdoor", rs.getString("INDOOR_OR_OUTDOOR"));
    			wifiInfo.put("connectionEnvironment", rs.getString("CONNECTION_ENVIRONMENT"));
    			wifiInfo.put("longitude", rs.getDouble("LONGITUDE"));
    			wifiInfo.put("latitude", rs.getDouble("LATITUDE"));
    			wifiInfo.put("workedTime", rs.getString("WORKED_TIME"));
    			
    			nearWifiInfos.add(wifiInfo);
    		}
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	return nearWifiInfos;
    }
    
    public Map<String, Object> getWifiInfo(String managementId) throws SQLException {
    	if (!checkTable("WIFI_INFO")) {
    		createTable("WIFI_INFO");
    	}
    	
    	Map<String, Object> wifiInfo = new HashMap<>();
    	String SQL = " SELECT * from WIFI_INFO WHERE MANAGEMENT_ID = ?";
    	
    	try {
    		pstmt = conn.prepareStatement(SQL);
    		pstmt.setString(1, managementId);
            
    		ResultSet rs = pstmt.executeQuery();
    		
    		while (rs.next()) {
    			wifiInfo.put("id", rs.getInt("ID"));
    			wifiInfo.put("managementId", rs.getString("MANAGEMENT_ID"));
    			wifiInfo.put("state", rs.getString("STATE"));
    			wifiInfo.put("wifiName", rs.getString("WIFI_NAME"));
    			wifiInfo.put("address", rs.getString("ADDRESS"));
    			wifiInfo.put("addressDetail", rs.getString("ADDRESS_DETAIL"));
    			wifiInfo.put("installFloor", rs.getString("INSTALL_FLOOR"));
    			wifiInfo.put("installType", rs.getString("INSTALL_TYPE"));
    			wifiInfo.put("installAgency", rs.getString("INSTALL_AGENCY"));
    			wifiInfo.put("serviceType", rs.getString("SERVICE_TYPE"));
    			wifiInfo.put("networkType", rs.getString("NETWORK_TYPE"));
    			wifiInfo.put("installYear", rs.getString("INSTALL_YEAR"));
    			wifiInfo.put("indoorOrOutdoor", rs.getString("INDOOR_OR_OUTDOOR"));
    			wifiInfo.put("connectionEnvironment", rs.getString("CONNECTION_ENVIRONMENT"));
    			wifiInfo.put("longitude", rs.getDouble("LONGITUDE"));
    			wifiInfo.put("latitude", rs.getDouble("LATITUDE"));
    			wifiInfo.put("workedTime", rs.getString("WORKED_TIME"));
    		}
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	return wifiInfo;
    }
    
    public void insertWifiInfo(JsonObject data) {
    	String SQL = "INSERT INTO WIFI_INFO ("
    			+ "MANAGEMENT_ID, WIFI_NAME, LATITUDE, LONGITUDE, STATE, ADDRESS, ADDRESS_DETAIL, INSTALL_FLOOR, INSTALL_TYPE,"
    			+ "INSTALL_AGENCY, SERVICE_TYPE, NETWORK_TYPE, INSTALL_YEAR, INDOOR_OR_OUTDOOR, CONNECTION_ENVIRONMENT, WORKED_TIME"
    			+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    	
    	try {
    		pstmt = conn.prepareStatement(SQL);

            // 입력 데이터 매핑
            pstmt.setString(1, data.get("X_SWIFI_MGR_NO").getAsString()); //관리번호
            pstmt.setString(2, data.get("X_SWIFI_MAIN_NM").getAsString()); //와이파이명
            pstmt.setDouble(3, data.get("LAT").getAsDouble()); // Y좌표
            pstmt.setDouble(4, data.get("LNT").getAsDouble()); //X좌표
            pstmt.setString(5, data.get("X_SWIFI_WRDOFC").getAsString()); //자치구
            pstmt.setString(6, data.get("X_SWIFI_ADRES1").getAsString()); //도로명주소
            pstmt.setString(7, data.get("X_SWIFI_ADRES2").getAsString()); //상세주소
            pstmt.setString(8, data.get("X_SWIFI_INSTL_FLOOR").getAsString()); //설치위치(층)
            pstmt.setString(9, data.get("X_SWIFI_INSTL_TY").getAsString()); //설치유형
            pstmt.setString(10, data.get("X_SWIFI_INSTL_MBY").getAsString()); //설치기관
            pstmt.setString(11, data.get("X_SWIFI_SVC_SE").getAsString()); //서비스구분
            pstmt.setString(12, data.get("X_SWIFI_CMCWR").getAsString()); //망종류
            pstmt.setString(13, data.get("X_SWIFI_CNSTC_YEAR").getAsString()); //설치년도
            pstmt.setString(14, data.get("X_SWIFI_INOUT_DOOR").getAsString()); //실내외구분
            pstmt.setString(15, data.get("X_SWIFI_REMARS3").getAsString()); //WIFI접속환경
            pstmt.setString(16, data.get("WORK_DTTM").getAsString()); //작업일자
            
            pstmt.executeUpdate();
            conn.commit();
    	} catch(Exception e) {
            e.printStackTrace();
        }
    };
    
    public void createTable(String tableName) throws SQLException {
    	String SQL=null;
    	
    	switch(tableName) {
	    	case "WIFI_INFO": 
	    		SQL = "CREATE TABLE IF NOT EXISTS WIFI_INFO ( "+"\n"
	                    + "ID INTEGER	PRIMARY KEY AUTOINCREMENT, "+"\n"
	                    + "MANAGEMENT_ID TEXT NOT NULL, "+"\n"
	                    + "WIFI_NAME TEXT NOT NULL, "+"\n"
	                    + "LATITUDE REAL NOT NULL, "+"\n"
	                    + "LONGITUDE REAL NOT NULL, "+"\n"
	                    + "STATE TEXT, "+"\n"
	                    + "ADDRESS TEXT, "+"\n"
	                    + "ADDRESS_DETAIL TEXT, "+"\n"
	                    + "INSTALL_FLOOR TEXT, "+"\n"
	                    + "INSTALL_TYPE TEXT, "+"\n"
	                    + "INSTALL_AGENCY TEXT, "+"\n"
	                    + "SERVICE_TYPE TEXT, "+"\n"
	                    + "NETWORK_TYPE TEXT, "+"\n"
	                    + "INSTALL_YEAR TEXT, "+"\n"
	                    + "INDOOR_OR_OUTDOOR TEXT, "+"\n"
	                    + "CONNECTION_ENVIRONMENT TEXT, "+"\n"
	                    + "WORKED_TIME TEXT "+"\n"
	                    + ");";
	    		break;
	    	case "BOOKMARK_LIST":
	    		SQL = "CREATE TABLE IF NOT EXISTS BOOKMARK_LIST ("+"\n"
	    				+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, "+"\n"
	                    + "WIFI_ID INTEGER NOT NULL, "+"\n"
	                    + "GROUP_ID INTEGER NOT NULL, "+"\n"
	                    + "REGIST_TIME TEXT NOT NULL "+"\n"
	                    + ");";
	    		break;
	    	case "BOOKMARK_GROUP":
	    		SQL = "CREATE TABLE IF NOT EXISTS BOOKMARK_GROUP ("+"\n"
	    				+ "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+"\n"
	                    + "GROUP_NAME TEXT NOT NULL,           "+"\n"
	                    + "CREATE_TIME TEXT NOT NULL, "+"\n"
	                    + "MODIFY_TIME TEXT, "+"\n"
	                    + "SEQ_NUM INTEGER NOT NULL"+"\n"
	                    + ");";
	    		break;
	    	case "HISTORY":
	    		SQL = "CREATE TABLE IF NOT EXISTS HISTORY ( "+"\n"
	    				+ "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+"\n"
	                    + "LATITUDE REAL NOT NULL, "+"\n"
	                    + "LONGITUDE REAL NOT NULL, "+"\n"
	                    + "EXEC_TIME TEXT NOT NULL "+"\n"
	                    + ");";
	    		break;
			default:
				break;
		}
    	
    	executeSQL(tableName, SQL);
    	System.out.println(tableName + "테이블 생성 완료");
    }
    
    public void clearTable(String tableName) throws SQLException {
    	String SQL=null;
    	if (checkTable(tableName)) {
    		SQL = "DELETE FROM " + tableName + ";";
    		executeSQL(tableName, SQL);
    		
    		SQL = "UPDATE SQLITE_SEQUENCE SET seq=0 WHERE name='" + tableName + "';";
    		executeSQL(tableName, SQL);
        	System.out.println(tableName + " 테이블 청소 완료");
    	} else {
    		createTable(tableName);
    	}
    }
    
    public void executeSQL(String tableName, String SQL) throws SQLException {
    	Statement stmt = null;
    	
    	try {
    		conn.setAutoCommit(false);
    		stmt = conn.createStatement();
        	stmt.execute(SQL);
        	
        	conn.commit();
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	} finally {
    		if( stmt != null ) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    	}
    }
}
