package me.firedragon5.islanddefender.instance.hats;

import java.util.Arrays;
import java.util.List;

public enum HatType {


	TOP_HAT("&cTop Hat",
			Arrays.asList("&cNice Hat"),
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDA3MjYyODRiOGMxMmExYTM3NjhiZGMzMjE4NWMxNTVlMTkxOTZkYzA4MGYwNGQ1YTY3ZDUwZTQ5MmQ1NjNiYiJ9fX0="),
	TIGER_HAT("&6Tiger Hat",
			Arrays.asList("&7Roar"),
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjVhZmMzN2RjMTkwOWVlMGEzZWI4ZDA0MDQyNzFmYzQ3NjYwY2ZmMTE1MzQ5NTQxMmQ2ZTk4OTY2MzJlYWE4ZSJ9fX0="),
	GOKU_HAT("&fGoku Hat",
			Arrays.asList("&7Kamehameha"),
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjY4ZDQ5ZTA3NjRlZjlhYTQxNDdlYzZkMWFhZDM4ZjY3YjViYjQ4MTdkYTdlOGMyMWQ1M2QwM2MyZTZiYzVlYyJ9fX0=");

	private String display, headTexture;
	private List<String> description;



	HatType(String display, List<String> description, String headTexture){

		this.display = display;
		this.description = description;
		this.headTexture = headTexture;

	}

	public String getDisplay() {
		return display;
	}

	public List<String> getDescription() {
		return description;
	}

	public String getHeadTexture() {
		return headTexture;
	}




}
