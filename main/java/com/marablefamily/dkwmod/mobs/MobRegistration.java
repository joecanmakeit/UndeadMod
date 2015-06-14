package com.marablefamily.dkwmod.mobs;

import net.minecraft.entity.EntityList;

import com.marablefamily.dkwmod.DKWMod;

import cpw.mods.fml.common.registry.EntityRegistry;

public class MobRegistration {

	public static void mainRegistration(DKWMod mod) {
		registerNewEntity(mod, EntitySoul.class, "Soul", 0xDCEAE2, 0xAEB7B2);
	}
	
	private static void registerNewEntity(DKWMod mod, Class entityClass, String entityName, int solidColor, int spotColor){
		int randomID = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomID);
		EntityRegistry.registerModEntity(entityClass, entityName, randomID, mod, 80, 1, true);
		EntityList.entityEggs.put(Integer.valueOf(randomID), new EntityList.EntityEggInfo(randomID, solidColor, spotColor));
	}
}
