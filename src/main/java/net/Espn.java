package net;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import data.EspnGame;
import data.EspnGameRef;
import data.EspnGameSummary;
import data.EspnPlayer;
import data.EspnPlayerRef;
import data.EspnPlayerStats;
import data.EspnPlayers;
import data.EspnStatCategory;
import data.EspnTeam;
import data.EspnWeek;
import data.PlayerStats;

public class Espn {
	
	public static List<PlayerStats> getPlayerStats(String year, String week) {
		return getGamesForWeek(year, week).games.stream()
				.map(Espn::getGame)
				.flatMap(Espn::getPlayerStats)
				.toList();
	}
	
	private static EspnWeek getGamesForWeek(String year, String week) {
		String endpoint = new StringBuilder("https://sports.core.api.espn.com/v2/sports/football/leagues/nfl")
				.append("/seasons/").append(year)
				.append("/types/2")
				.append("/weeks/").append(week)
				.append("/events")
				.toString();
		return apiCall(endpoint, new TypeToken<EspnWeek>() {});
	}
	
	private static EspnGame getGame(EspnGameRef ref) {
		return apiCall(ref.url, new TypeToken<EspnGame>() {});
	}
	
	private static Stream<PlayerStats> getPlayerStats(EspnGame game) {
		return getGameSummary(game).boxscore.teams.stream()
				.flatMap(boxscoreTeam -> getPlayerStats(boxscoreTeam.team, game));
	}
	
	private static EspnGameSummary getGameSummary(EspnGame game) {
		String endpoint = new StringBuilder("https://site.api.espn.com/apis/site/v2/sports/football/nfl/summary")
			.append("?event=").append(game.id)
			.toString();
		return apiCall(endpoint, new TypeToken<EspnGameSummary>() {});
	}
	
	private static Stream<PlayerStats> getPlayerStats(EspnTeam team, EspnGame game) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String endpoint = new StringBuilder("https://sports.core.api.espn.com/v2/sports/football/leagues/nfl")
				.append("/events/").append(game.id)
				.append("/competitions/").append(game.id)
				.append("/competitors/").append(team.id)
				.append("/roster")
				.toString();
			return apiCall(endpoint, new TypeToken<EspnPlayers>() {}).players.stream()
					.map(player -> player.athlete)
					.map(Espn::getPlayer)
					.flatMap(player -> toPlayerStats(player, team, getPlayerStats(team, game, player)).stream());
	}
	
	private static EspnPlayer getPlayer(EspnPlayerRef ref) {
		return apiCall(ref.url, new TypeToken<EspnPlayer>() {});
	}
	
	private static EspnPlayerStats getPlayerStats(EspnTeam team, EspnGame game, EspnPlayer player) {
		String endpoint = new StringBuilder("https://sports.core.api.espn.com/v2/sports/football/leagues/nfl")
				.append("/events/").append(game.id)
				.append("/competitions/").append(game.id)
				.append("/competitors/").append(team.id)
				.append("/roster/").append(player.id)
				.append("/statistics/0")
				.toString();
			return apiCall(endpoint, new TypeToken<EspnPlayerStats>() {});
	}
	
	private static Optional<PlayerStats> toPlayerStats(EspnPlayer player, EspnTeam team, EspnPlayerStats stats) {
		if (stats == null) {
			return Optional.empty();
		}
		
		PlayerStats.Builder builder = PlayerStats.builder()
				.firstName(player.firstName)
				.lastName(player.lastName)
				.displayName(player.displayName)
				.jersey(player.jersey)
				.team(team.abbreviation)
				.weight(player.weight)
				.height(player.height)
				.age(player.age)
				.active(player.active)
				.position(player.position.abbreviation);
		
		for (EspnStatCategory category : stats.splits.categories) {
			switch(category.name) {
				case "general" -> category.stats.stream().filter(stat -> stat.value != 0.0).forEach(stat -> builder.generalStat(stat.name, stat.value));
				case "passing" -> category.stats.stream().filter(stat -> stat.value != 0.0).forEach(stat -> builder.passingStat(stat.name, stat.value));
				case "rushing" -> category.stats.stream().filter(stat -> stat.value != 0.0).forEach(stat -> builder.rushingStat(stat.name, stat.value));
				case "receiving" -> category.stats.stream().filter(stat -> stat.value != 0.0).forEach(stat -> builder.receivingStat(stat.name, stat.value));
				case "defensive" -> category.stats.stream().filter(stat -> stat.value != 0.0).forEach(stat -> builder.defensiveStat(stat.name, stat.value));
				case "defensiveInterceptions" -> category.stats.stream().filter(stat -> stat.value != 0.0).forEach(stat -> builder.defensiveInterceptionStat(stat.name, stat.value));
				case "kicking" -> category.stats.stream().filter(stat -> stat.value != 0.0).forEach(stat -> builder.kickingStat(stat.name, stat.value));
				case "returning" -> category.stats.stream().filter(stat -> stat.value != 0.0).forEach(stat -> builder.returningStat(stat.name, stat.value));
				case "punting" -> category.stats.stream().filter(stat -> stat.value != 0.0).forEach(stat -> builder.puntingStat(stat.name, stat.value));
				case "scoring" -> category.stats.stream().filter(stat -> stat.value != 0.0).forEach(stat -> builder.scoringStat(stat.name, stat.value));
				default -> throw new IllegalStateException("Unrecognized statistic category: " + category.name);
			}
		}
		
		return Optional.of(builder.build());
	}
	
	public static <T> T apiCall(String endpoint, TypeToken<T> dataType) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());

	        if (response.statusCode() == 200) {
	            String responseBody = response.body();
	            Gson gson = new Gson();
	            return gson.fromJson(responseBody, dataType.getType());
	        } else {
	            System.err.println("API request failed with status code: " + response.statusCode() + " at URL: " + endpoint);
	        }
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
