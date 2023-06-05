package util;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Util {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
	static {
		nf.setMaximumFractionDigits(2); // O default é 3.
		nf.setMinimumFractionDigits(2);
		nf.setGroupingUsed(false);
	}
	public static Date strToDate(String umaData) {
		String dia = umaData.substring(0, 2);
		String mes = umaData.substring(3, 5);
		String ano = umaData.substring(6, 10);

		return java.sql.Date.valueOf(ano + "-" + mes + "-" + dia);
	}

	public static String dateToStr(Date umaData) {
		return sdf.format(umaData);
	}

}