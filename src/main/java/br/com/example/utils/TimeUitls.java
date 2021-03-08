package br.com.example.utils;

import java.time.LocalDateTime;
/**
 * Classe utilitária para tratar de cálculos com o tempo
 * 
 * @author Danilo Alexandre
 *
 */
public class TimeUitls {
	
	
	public static LocalDateTime addMinutesFromNow(long minutes) {
		minutes = minutes > 0 ? minutes : 1;
		return LocalDateTime.now().plusMinutes(minutes);
	}
	
	public static long minutesInMillis(long minutes) {
		return minutes * 60 * 1000;
	}

}
