package me.firedragon5.islanddefender.generator;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

public class CustomIslandGenerator extends ChunkGenerator {

//	Must create a island for each player
//	The world is max 13 chunks
//	Each chunk is 16x16
//	Every chunk must be grass dirt and stone then bedrock at the bottom
//	Each chunk must be 256 blocks high
//	When the world is created only one chunk in the middle is created the rest is water

	private static final int CHUNK_SIZE = 16; // Chunk size (16x16)
	private static final int MAX_HEIGHT = 256; // Maximum height of the island

	@Override
	public ChunkData generateChunkData(org.bukkit.World world, java.util.Random random, int chunkX, int chunkZ, ChunkGenerator.BiomeGrid biome) {
		ChunkData chunkData = createChunkData(world);

		// Set the biome for the chunk
		for (int x = 0; x < CHUNK_SIZE; x++) {
			for (int z = 0; z < CHUNK_SIZE; z++) {
				biome.setBiome(x, z, Biome.PLAINS);
			}
		}

		// Set the blocks for the chunk
		for (int x = 0; x < CHUNK_SIZE; x++) {
			for (int z = 0; z < CHUNK_SIZE; z++) {
				for (int y = 0; y < MAX_HEIGHT; y++) {
					if (y == 0) {
						chunkData.setBlock(x, y, z, Material.BEDROCK);
					} else if (y < 4) {
						chunkData.setBlock(x, y, z, Material.STONE);
					} else if (y < 5) {
						chunkData.setBlock(x, y, z, Material.DIRT);
					} else if (y < 6) {
						chunkData.setBlock(x, y, z, Material.GRASS_BLOCK);
					} else {
						chunkData.setBlock(x, y, z, Material.AIR);
					}
				}
			}
		}
		
		return chunkData;
	}
}
