package common_modules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db {
	private static String dbPath="dbPath"; //db 파일 저장 위치 설정(ex publicWifi.db)
	private static String dbType="jdbc:sqlite";
	public static Connection conn=null;
	
	public static Connection connectDB() {
        StringBuilder urlBuilder = new StringBuilder(dbType).append(":").append(dbPath);
        String url = urlBuilder.toString();

        try {
            Class.forName("org.sqlite.JDBC");  //드라이버 설정
            conn = DriverManager.getConnection(url);
            
            System.out.println("SQLite DB Connected!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

       return conn;
    }

    public static void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        } finally {
            conn = null;
 
            System.out.println("SQLite DB Connect Closed!");
        }
    }
}
