package br.com.example.utils;

import java.time.ZonedDateTime;

/**
 * Classe utilitária para tratar de cálculos com o tempo
 * 
 * @author Danilo Alexandre
 *
 */
public class TimeUtils {

	public static ZonedDateTime addMinutesFromNow(long minutes) {
		minutes = minutes > 0 ? minutes : 1;
		return ZonedDateTime.now().plusMinutes(minutes);
	}

	public static long minutesInMillis(long minutes) {
		return minutes * 60 * 1000;
	}

}
