/*
 * The FML Forge Mod Loader suite.
 * Copyright (C) 2012 cpw
 * 
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package net.minecraft.src;

import java.util.Collections;
import java.util.List;

public class CommonRegistry {
  public static void addRecipe(ItemStack output, Object... params) {
    CraftingManager.func_20151_a().func_20153_a(output, params);
  }

  public static void addShapelessRecipe(ItemStack output, Object... params) {
    CraftingManager.func_20151_a().func_21146_b(output, params);
  }

  public static void addSmelting(int input, ItemStack output) {
    FurnaceRecipes.func_21162_a().func_21160_a(input, output);
  }

  public static void registerBlock(Block block) {
    registerBlock(block,ItemBlock.class);
  }

  public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass) {
    try {
      assert block!=null : "registerBlock: block cannot be null";
      assert itemclass!=null : "registerBlock: itemclass cannot be null";
      int blockItemId=block.field_573_bc-256;
      itemclass.getConstructor(int.class).newInstance(blockItemId);
    } catch (Exception e) {
      //HMMM
    }
  }

  public static void registerEntityID(Class<? extends Entity> entityClass, String entityName, int id) {
    EntityList.addNewEntityListMapping(entityClass, entityName, id);
  }

  public static void registerEntityID(Class<? extends Entity> entityClass, String entityName, int id, int backgroundEggColour, int foregroundEggColour) {
    EntityList.addNewEntityListMapping(entityClass, entityName, id, backgroundEggColour, foregroundEggColour);
  }

  public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
    TileEntity.addNewTileEntityMapping(tileEntityClass, id);
  }

  public static void addBiome(BiomeGenBase biome) {
    //NOOP because the implementation idea is broken. Creating a BiomeGenBase adds the biome already.
  }

  public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes) {
    for (BiomeGenBase biome : biomes) {
      @SuppressWarnings("unchecked")
      List<SpawnListEntry> spawns=biome.func_25055_a(typeOfCreature);
      for (SpawnListEntry entry : spawns) {
        //Adjusting an existing spawn entry
        if (entry.field_25145_a==entityClass) {
          entry.field_35483_d=weightedProb;
          entry.field_35484_b=min;
          entry.field_35485_c=max;
          break;
        }
      }
      spawns.add(new SpawnListEntry(entityClass, weightedProb, min, max));
    }
  }

  @SuppressWarnings("unchecked")
  public static void addSpawn(String entityName, int weightedProb, int min, int max, EnumCreatureType spawnList, BiomeGenBase... biomes) {
    Class<? extends Entity> entityClazz=EntityList.getEntityToClassMapping().get(entityName);
    if (EntityLiving.class.isAssignableFrom(entityClazz)) {
      addSpawn((Class<? extends EntityLiving>) entityClazz,weightedProb,min,max,spawnList,biomes);
    }
  }

  public static void removeBiome(BiomeGenBase biome) {
    // NOOP because broken
  }

  public static void removeSpawn(Class<? extends EntityLiving> entityClass, EnumCreatureType typeOfCreature, BiomeGenBase... biomes) {
    for (BiomeGenBase biome : biomes) {
      @SuppressWarnings("unchecked")
      List<SpawnListEntry> spawns=biome.func_25055_a(typeOfCreature);
      for (SpawnListEntry entry : Collections.unmodifiableList(spawns)) {
        if (entry.field_25145_a==entityClass) {
          spawns.remove(entry);
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static void removeSpawn(String entityName, EnumCreatureType spawnList, BiomeGenBase... biomes) {
    Class<? extends Entity> entityClazz=EntityList.getEntityToClassMapping().get(entityName);
    if (EntityLiving.class.isAssignableFrom(entityClazz)) {
      removeSpawn((Class<? extends EntityLiving>) entityClazz,spawnList,biomes);
    }
  }

}
