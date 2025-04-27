package core;

import java.util.Optional;

import data.PlayerStats;
import net.Espn;

public class Main {
	
	public static void main(String[] args) {
		//String year = getArg(args, "year").orElseThrow();
		//String week = getArg(args, "week").orElseThrow();
		
		for (PlayerStats stats : Espn.getPlayerStats("2024", "1")) {
			System.out.println("--------------------");
			System.out.println(stats);
		}
	}
	
	private static Optional<String> getArg(String[] args, String argName) {
		String value = null;
		boolean nameFound = false;
		String fullName = "-" + argName;
		
		for (int i = 0; i < args.length; i++) {
			if (nameFound) {
				value = args[i];
				break;
			}
			
			if (args[i].equals(fullName)) {
				nameFound = true;
			}
		}
		
		return Optional.ofNullable(value);
	}
}
