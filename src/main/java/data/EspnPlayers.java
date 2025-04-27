package data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class EspnPlayers {
	@SerializedName("entries")
	public List<EspnRosterPlayer> players;
}
