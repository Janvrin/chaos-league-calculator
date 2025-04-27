package data;

import java.util.HashMap;
import java.util.Map;

public class PlayerStats {
	
	String firstName;
	String lastName;
	String displayName;
	String team;
	String jersey;
	public double weight;
	public double height;
	public double age;
	public boolean active;
	public String position;
	Map<String, Double> generalStats;
	Map<String, Double> passingStats;
	Map<String, Double> rushingStats;
	Map<String, Double> receivingStats;
	Map<String, Double> defensiveStats;
	Map<String, Double> defensiveInterceptionStats;
	Map<String, Double> kickingStats;
	Map<String, Double> returningStats;
	Map<String, Double> puntingStats;
	Map<String, Double> scoringStats;
	
	public static Builder builder() {
		return new Builder();
	}
	
	public PlayerStats(Builder builder) {
		firstName = builder.firstName;
		lastName = builder.lastName;
		displayName = builder.displayName;
		team = builder.team;
		jersey = builder.jersey;
		generalStats = builder.generalStats;
		passingStats = builder.passingStats;
		rushingStats = builder.rushingStats;
		receivingStats = builder.receivingStats;
		defensiveStats = builder.defensiveStats;
		defensiveInterceptionStats = builder.defensiveInterceptionStats;
		kickingStats = builder.kickingStats;
		returningStats = builder.returningStats;
		puntingStats = builder.puntingStats;
		scoringStats = builder.scoringStats;
	}
	
	public double getGeneralStat(String name) {
		return generalStats.getOrDefault(name, 0.0);
	}
	
	public double getPassingStat(String name) {
		return passingStats.getOrDefault(name, 0.0);
	}
	
	public double getRushingStat(String name) {
		return rushingStats.getOrDefault(name, 0.0);
	}
	
	public double getReceivingStat(String name) {
		return receivingStats.getOrDefault(name, 0.0);
	}
	
	public double getDefensiveStat(String name) {
		return defensiveStats.getOrDefault(name, 0.0);
	}
	
	public double getDefensiveInterceptionStat(String name) {
		return defensiveInterceptionStats.getOrDefault(name, 0.0);
	}
	
	public double getKickingStat(String name) {
		return kickingStats.getOrDefault(name, 0.0);
	}
	
	public double getReturingStat(String name) {
		return returningStats.getOrDefault(name, 0.0);
	}
	
	public double getPuntingStat(String name) {
		return puntingStats.getOrDefault(name, 0.0);
	}
	
	public double getScoringStat(String name) {
		return scoringStats.getOrDefault(name, 0.0);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder(displayName).append("\n");
		if (!generalStats.isEmpty()) {
			builder.append("General Stats: ").append(generalStats).append("\n");
		}
		if (!passingStats.isEmpty()) {
			builder.append("Passing Stats: ").append(passingStats).append("\n");
		}
		if (!rushingStats.isEmpty()) {
			builder.append("Rushing Stats: ").append(rushingStats).append("\n");
		}
		if (!receivingStats.isEmpty()) {
			builder.append("Receiving Stats: ").append(receivingStats).append("\n");
		}
		if (!defensiveStats.isEmpty()) {
			builder.append("Defensive Stats: ").append(defensiveStats).append("\n");
		}
		if (!defensiveInterceptionStats.isEmpty()) {
			builder.append("Defensive Interception Stats: ").append(defensiveInterceptionStats).append("\n");
		}
		if (!kickingStats.isEmpty()) {
			builder.append("Kicking Stats: ").append(kickingStats).append("\n");
		}
		if (!returningStats.isEmpty()) {
			builder.append("Returning Stats: ").append(returningStats).append("\n");
		}
		if (!puntingStats.isEmpty()) {
			builder.append("Punting Stats: ").append(puntingStats).append("\n");
		}
		if (!scoringStats.isEmpty()) {
			builder.append("Scoring Stats: ").append(scoringStats).append("\n");
		}
		
		return builder.toString();
	}
	
	public static class Builder {
		
		String firstName;
		String lastName;
		String displayName;
		String team;
		String jersey;
		public double weight;
		public double height;
		public double age;
		public boolean active;
		public String position;
		Map<String, Double> generalStats = new HashMap<>();
		Map<String, Double> passingStats = new HashMap<>();
		Map<String, Double> rushingStats = new HashMap<>();
		Map<String, Double> receivingStats = new HashMap<>();
		Map<String, Double> defensiveStats = new HashMap<>();
		Map<String, Double> defensiveInterceptionStats = new HashMap<>();
		Map<String, Double> kickingStats = new HashMap<>();
		Map<String, Double> returningStats = new HashMap<>();
		Map<String, Double> puntingStats = new HashMap<>();
		Map<String, Double> scoringStats = new HashMap<>();
		
		private Builder() {};
		
		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public Builder displayName(String displayName) {
			this.displayName = displayName;
			return this;
		}
		
		public Builder team(String team) {
			this.team = team;
			return this;
		}
		
		public Builder jersey(String jersey) {
			this.jersey = jersey;
			return this;
		}
		
		public Builder weight(double weight) {
			this.weight = weight;
			return this;
		}
		
		public Builder height(double height) {
			this.height = height;
			return this;
		}
		
		public Builder age(double age) {
			this.age = age;
			return this;
		}
		
		public Builder active(boolean active) {
			this.active = active;
			return this;
		}
		
		public Builder position(String position) {
			this.position = position;
			return this;
		}
		
		public Builder generalStat(String name, double value) {
			generalStats.put(name, value);
			return this;
		}
		
		public Builder passingStat(String name, double value) {
			passingStats.put(name, value);
			return this;
		}
		
		public Builder rushingStat(String name, double value) {
			rushingStats.put(name, value);
			return this;
		}
		
		public Builder receivingStat(String name, double value) {
			receivingStats.put(name, value);
			return this;
		}
		
		public Builder defensiveStat(String name, double value) {
			defensiveStats.put(name, value);
			return this;
		}
		
		public Builder defensiveInterceptionStat(String name, double value) {
			defensiveInterceptionStats.put(name, value);
			return this;
		}
		
		public Builder kickingStat(String name, double value) {
			kickingStats.put(name, value);
			return this;
		}
		
		public Builder returningStat(String name, double value) {
			returningStats.put(name, value);
			return this;
		}
		
		public Builder puntingStat(String name, double value) {
			puntingStats.put(name, value);
			return this;
		}
		
		public Builder scoringStat(String name, double value) {
			scoringStats.put(name, value);
			return this;
		}
		
		public PlayerStats build() {
			return new PlayerStats(this);
		}
	}
}
