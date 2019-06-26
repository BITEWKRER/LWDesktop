package SideBar;
/**
 * @author comi
 * @功能：持续检测网络是否连通
 */


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetworkState{
	// 判断网络状态
	public static boolean isConnect() {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("ping " + "www.baidu.com");
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			is.close();
			isr.close();
			br.close();

			if (null != sb && !sb.toString().equals("")) {
				if (sb.toString().indexOf("TTL") > 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
