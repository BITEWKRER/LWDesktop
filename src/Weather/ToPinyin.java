package Weather;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

public class ToPinyin {
	public static String toPinyin(String location) {
		String py = PinyinHelper.convertToPinyinString(location, "", PinyinFormat.WITHOUT_TONE);
		return py;
	}
}