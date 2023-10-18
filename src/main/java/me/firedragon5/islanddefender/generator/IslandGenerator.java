package me.firedragon5.islanddefender.generator;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


public class IslandGenerator extends ChunkGenerator {


	//	Create a super flat world
	@Override
	public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, @NotNull BiomeGrid biome) {
		ChunkData chunkData = createChunkData(world);

		// Set the top layer to GRASS_BLOCK
		chunkData.setRegion(0, 0, 0, 16, 1, 16, Material.GRASS_BLOCK);

		// Set the second layer to DIRT
		chunkData.setRegion(0, 1, 0, 16, 1, 16, Material.DIRT);

		// Set the layers from 2 to 15 to STONE
		for (int y = 2; y <= 15; y++) {
			chunkData.setRegion(0, y, 0, 16, 1, 16, Material.STONE);
		}

		// Set the bottom layer (layer 16) to BEDROCK
		chunkData.setRegion(0, 16, 0, 16, 1, 16, Material.BEDROCK);

		return chunkData;
	}


	//	Each player can expand their island by eight chunks so make the world border 13 chunks
	@Override
	public boolean canSpawn(@NotNull World world, int x, int z) {
		WorldBorder border = world.getWorldBorder();
		int halfBorderSize = (int) (border.getSize() / 2);

		return x >= -halfBorderSize && x < halfBorderSize && z >= -halfBorderSize && z < halfBorderSize;
	}


}
