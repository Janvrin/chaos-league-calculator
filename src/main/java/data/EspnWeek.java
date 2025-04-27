package data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class EspnWeek {
	@SerializedName("items")
	public List<EspnGameRef> games;
}
