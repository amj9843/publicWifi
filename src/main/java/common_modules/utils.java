package common_modules;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class utils {
	public static String changeColon(String str) {
		return str.replaceAll("'", "''");
	}
	
	public static String nowTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter YYYY_MM_DD_HH_mm_ss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
        return localDateTime.format(YYYY_MM_DD_HH_mm_ss);
	}
}
