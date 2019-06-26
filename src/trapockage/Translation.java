package trapockage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Translation {
	public String Result = "";
	private String text;
	private int flag;
	private int tra_flag;

	public Translation(String text, int flag, int tra_flag) {
		this.flag = flag;
		this.text = text;
		this.tra_flag = tra_flag;
		traslatate();
	}

	public void traslatate() {
		String[] pra = { "", "", "", "" };

		try {
			String pythonFile = System.getProperty("user.dir") + "\\src\\trapockage\\Spider.exe";
			System.out.println(pythonFile);
			pra[0] = pythonFile;

			String[] paramter = new String[] { pra[0], String.valueOf(text), String.valueOf(flag),
					String.valueOf(tra_flag) };
			Process pr = Runtime.getRuntime().exec(paramter);

			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			String call;
			while ((call = in.readLine()) != null) {
				this.Result += "\n" + call;
			}
			in.close();
			pr.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
