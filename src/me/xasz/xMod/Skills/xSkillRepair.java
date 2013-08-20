package me.xasz.xMod.Skills;


import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import me.xasz.xMod.xMod;
import me.xasz.xMod.xPlayerProfile;
import me.xasz.xMod.Helper.ItemType;
import me.xasz.xMod.Helper.xPlayer;

import org.bukkit.inventory.ItemStack;


public class xSkillRepair {

	/** checks if item is repairable
	 * @param item
	 * @return yes/no
	 */
	public static boolean isItemRepairable(ItemStack item) {
		if(!xMod.repairIsEnabled){
			return false;
		}
		boolean success = false;
		if (getItemType(item) != ItemType.NONE && item.getDurability() > 0) {
			success = true;
		}
		return success;
	}

	/** repair the item
	 *  if item could not be repaired, the function will fail and return false
	 *  player should get his minerals back, if the function fail
	 * @param player
	 * @return boolean
	 */
	
	public static boolean repair(Player player) {		
		boolean success = false;
		//check for enabled and the permission
		if(!xMod.repairIsEnabled || !player.hasPermission(xMod.permSkillRepair)){
			return false;
		}
		
		//only do the repair if item is repiarable
		if (isItemRepairable(player.getItemInHand())) {
			//get the itemID
			int neededItemID = getNeededItemId(player.getItemInHand());
			
			//get the itemtype
			ItemType neededItemType = getItemType(player.getItemInHand());
			
			//generate a itemstack
			ItemStack stackinHand = player.getItemInHand();
			
			//get the needed Enchantmenlevel
			int neededEnchantmentLevel = calculateRepairEnchantmentCosts( player.getInventory().getItemInHand());
			
			
			//getting itemamount for enchantmentrepair 
			int neededItemAmount = 1;
			 xPlayer.sendDebugMessage(player, "You Weapon is LVL:" + neededEnchantmentLevel);
			 xPlayer.sendDebugMessage(player, "You need Minerals Amount:" + neededItemAmount);
				
			//check, if player has enough minerals
			boolean hasPlayerEnoughMinerals = xPlayer.hasItem(player,new ItemStack(neededItemID,neededItemAmount));

			 xPlayer.sendDebugMessage(player, "Enough Mineral:" + hasPlayerEnoughMinerals);
			 
			if (hasPlayerEnoughMinerals) {
				//check if Enchantment needed and cancel if player has not enough lvl
				 if(player.getLevel() < neededEnchantmentLevel){
					 xPlayer.sendErrorMessage(player, "Your Level is not high enough. You need Level: " + neededEnchantmentLevel);	 
					 return false;
				 }
				//remove the needed Item from the inventory of the player 
				for(int i = 0; i< neededItemAmount; i++){
					xPlayer.removeItem(player, neededItemID);
				}
				//remove the level for enchantment from  player 
				player.setLevel(player.getLevel()-neededEnchantmentLevel);

				// getting the health of the item - based on the wiki
				byte itemCraftCount =getCraftCount(neededItemType);
				short maxItemHealth =  getMaxDurability(neededItemType,neededItemID);

				//saving the actuell durability for the repaircalculation
				short oldDurability = player.getItemInHand().getDurability();
				
				//calculates the new durability
				short newDurability = calculateRepairDurability(itemCraftCount, maxItemHealth, player);
				//setting the new itemdurability
				stackinHand.setDurability(newDurability);
				//verfiy that the player get the item in hand
				player.setItemInHand(stackinHand);
				
				int xp =  (oldDurability - newDurability);
				if(xp < 1){
					xp = 1;
				}
				//call the gain xp function.. adding the amount of durabilty which was repaired
				xMod.getPlayerProfile(player).gainXP(xSkillType.REPAIR,xp);
				
				xPlayer.sendSuccessMessage(player, "Your Item is repaired");
				//function is deprecated.. but without it doesn't work at the moment - Version Bukkit 1.1
				player.updateInventory();

			} else {				
				//if not enough minerals, the player gets a message what item is needed
				xPlayer.sendErrorMessage(player, "Not enough Material. You need " + (neededItemAmount) +" "+ ItemIDToText(neededItemID) +".");	
			}

		} else {
			//if item cannon be repaired, the player gets a message
			xPlayer.sendSuccessMessage(player, "Item cannot be repaired");
		}
		return success;
	}
	/**
	 *  searches for the the max durability - based on the wiki
	 * @param neededItemType - Which item type you want to calculate
	 * @param neededItemMaterialID - Which MaterialID the Item got, because other iron, gold.. got different durability
	 * @return
	 */
	private static short getMaxDurability(ItemType neededItemType,int neededItemMaterialID){
		short maxItemHealth = 0;
		//sets the durability of the armor. else there is ne normal durability based on the material
		if(neededItemType == ItemType.BOW){
			maxItemHealth = 385; 
		}else if(neededItemType == ItemType.HELMET || neededItemType == ItemType.CHESTPLATE || neededItemType == ItemType.LEGGINGS || neededItemType == ItemType.BOOTS){
			if(neededItemType == ItemType.HELMET){
				switch (neededItemMaterialID) {
				// Iron
				case 265:
					maxItemHealth = 166;
					break;
				// Gold
				case 266:
					maxItemHealth = 78;
					break;
				// Dia
				case 264:
					maxItemHealth = 364;
					break;
				}						
			}
			if(neededItemType == ItemType.CHESTPLATE){
				switch (neededItemMaterialID) {
				// Iron
				case 265:
					maxItemHealth = 242;
					break;
				// Gold
				case 266:
					maxItemHealth = 114;
					break;
				// Dia
				case 264:
					maxItemHealth = 530;
					break;
				}						
			}
			if(neededItemType == ItemType.LEGGINGS){
				switch (neededItemMaterialID) {
				// Iron
				case 265:
					maxItemHealth = 226;
					break;
				// Gold
				case 266:
					maxItemHealth = 106;
					break;
				// Dia
				case 264:
					maxItemHealth = 496;
					break;
				}						
			}
			if(neededItemType == ItemType.SHEAR){
				maxItemHealth = 238;						
			}
			if(neededItemType == ItemType.BOOTS){
				switch (neededItemMaterialID) {
				// Iron
				case 265:
					maxItemHealth = 196;
					break;
				// Gold
				case 266:
					maxItemHealth = 92;
					break;
				// Dia
				case 264:
					maxItemHealth = 430;
					break;
				}						
			}
		}else{
			switch (neededItemMaterialID) {
			// Wood
			case 5:
				maxItemHealth = 66;
				break;
			// Stone
			case 4:
				maxItemHealth = 132;
				break;
			// Iron
			case 265:
				maxItemHealth = 251;
				break;
			// Gold
			case 266:
				maxItemHealth = 33;
				break;
			// Dia
			case 264:
				maxItemHealth = 1562;
				break;
			}
		}

			return maxItemHealth;

	}
	
	/**
	 * get the craftcount of an item
	 * the craftcount is the amount what u need to craft the item
	 * e.g. iron pickaxe = return = 3
	 * @param the id of the item
	 * @return the craftcount
	 */	
	private static byte getCraftCount(ItemType neededItemType){
		if (neededItemType == ItemType.AXE
				|| neededItemType == ItemType.PICKAXE) {
			return 3;
		}
		if (neededItemType == ItemType.HOW
				|| neededItemType == ItemType.SWORD) {
			return 2;
		}
		if (neededItemType == ItemType.SHOVEL) {
			return 1;
		}
		if(neededItemType == ItemType.HELMET){
			return 5;	
		}
		if(neededItemType == ItemType.CHESTPLATE){
			return 8;	
		}
		if(neededItemType == ItemType.LEGGINGS){
			return 7;	
		}
		if(neededItemType == ItemType.BOOTS){
			return 4;	
		}
		if(neededItemType == ItemType.SHEAR){
			return 2;	
		}
		if(neededItemType == ItemType.BOW){
			return 3;	
		}
		return 0;
	}
	/**
	 * calculates the lvl which is needed for one repairaction
	 * @param item which should be repaired
	 * @return how much level you need for the repair
	 */
	private static int calculateRepairEnchantmentCosts(ItemStack item ){
		int overAllEnchantmentLevel = 0;
		if(item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
		}
		if(item.getEnchantmentLevel(Enchantment.ARROW_FIRE) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.ARROW_FIRE);
		}
		if(item.getEnchantmentLevel(Enchantment.ARROW_INFINITE) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.ARROW_INFINITE);
		}
		if(item.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK);
		}
		if(item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
		}
		if(item.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
		}
		if(item.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
		}
		if(item.getEnchantmentLevel(Enchantment.DIG_SPEED) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.DIG_SPEED);
		}
		if(item.getEnchantmentLevel(Enchantment.DURABILITY) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.DURABILITY);
		}
		if(item.getEnchantmentLevel(Enchantment.FIRE_ASPECT) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.FIRE_ASPECT);
		}
		if(item.getEnchantmentLevel(Enchantment.KNOCKBACK) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.KNOCKBACK);
		}
		if(item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
		}
		if(item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
		}
		if(item.getEnchantmentLevel(Enchantment.OXYGEN) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.OXYGEN);
		}
		if(item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
		}
		if(item.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS);
		}
		if(item.getEnchantmentLevel(Enchantment.PROTECTION_FALL) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.PROTECTION_FALL);
		}
		if(item.getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE);
		}
		if(item.getEnchantmentLevel(Enchantment.SILK_TOUCH) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.SILK_TOUCH);
		}
		if(item.getEnchantmentLevel(Enchantment.WATER_WORKER) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.WATER_WORKER);
		}

		if(item.getEnchantmentLevel(Enchantment.THORNS) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.WATER_WORKER);
		}
		if(item.getEnchantmentLevel(Enchantment.PROTECTION_FIRE) > 0){
			overAllEnchantmentLevel += item.getEnchantmentLevel(Enchantment.WATER_WORKER);
		}
		
		
		//multiply by factor 1.5
		overAllEnchantmentLevel = (int)(((double)overAllEnchantmentLevel*1.5f)+0.5f);
		
		
		return overAllEnchantmentLevel;
	}

	public static double getChance(Player player){
		return getChance(xMod.getPlayerProfile(player));
	}
	public static double getChance(xPlayerProfile player){
		//REPAIRPROCCHANCE
		double chance = player.getRepairLVL() *xMod.repairChancePerLevelFactor;
		if(chance > xMod.repairMaxProcChance){
			chance = xMod.repairMaxProcChance;
		}
		//rounding 
		chance = chance * 100;
		chance = Math.round(chance);
		chance = chance / 100;
		return chance;
	}	
	/** calculate the remaining durability for an item, that should be repaired
	 * @param craftCount how much blocks are needed for a craft. Example: Axe = 3, How = 2, Leggings = 7
	 * @param maxHealth the items max durability
	 * @param player yes... of course ... :)
	 * @return short
	 */
	private static short calculateRepairDurability(byte craftCount,short maxHealth, Player player) {
		
		short newDurability = player.getItemInHand().getDurability();
		short repairAmount = (short) ((float) maxHealth / (float) craftCount);
		float multiplicator = 1.0f;
		
		//REPAIRPROCCHANCE
		double chance = getChance(player);
		if((Math.random() * 100.0f) < chance ){	
			multiplicator += 1.0f;
			xPlayer.sendSuccessMessage(player, "DOUBLE REPAIR");
		}	
		
		//BONUS
		multiplicator += xMod.getPlayerProfile(player).getRepairLVL()/100.0f;
		
		newDurability -= repairAmount * multiplicator;
		if (xMod.DEBUG) {
			xPlayer.sendDebugMessage(player, "repairAmount is:" + repairAmount);
		}
		// prevents floating errors for the short and stops repairingamount if item is fully repaired
		if (newDurability <= 0) {
			newDurability = 0;
			
			if (xMod.DEBUG) {
				xPlayer.sendDebugMessage(player,"Item will be fully repaired - Durability will be set to: 0");
			}
		}	
		if (xMod.DEBUG) {
			xPlayer.sendDebugMessage(player, "newDurability is:" + newDurability);
		}	
		
		
		return newDurability;
	}

	/** get ItemType from an item
	 * @param item
	 * @return ItemType
	 */
	public static ItemType getItemType(ItemStack item) {
		ItemType result = ItemType.NONE;
		int itemid = item.getTypeId();
		switch (itemid) {
		case 261:// BOW
			result = ItemType.BOW;
			break;
		// HOW
		case 290:
		case 291:
		case 292:
		case 293:
		case 294:
			result = ItemType.HOW;
			break;

		// SWORD
		case 267:
		case 268:
		case 272:
		case 276:
		case 283:
			result = ItemType.SWORD;
			break;

		// PICKAXE
		case 257:
		case 270:
		case 274:
		case 278:
		case 285:
			result = ItemType.PICKAXE;
			break;

		// AXE
		case 258:
		case 271:
		case 275:
		case 279:
		case 286:
			result = ItemType.AXE;
			break;

		// SHOVEL
		case 256:
		case 269:
		case 273:
		case 277:
		case 284:
			result = ItemType.SHOVEL;
			break;
		// HELMET
		case 306:
		case 310:
		case 314:
			result = ItemType.HELMET;
			break;
		// CHESTPLATE
		case 307:
		case 311:
		case 315:
			result = ItemType.CHESTPLATE;
			break;
		// LEGGINGS
		case 308:
		case 312:
		case 316:
			result = ItemType.LEGGINGS;
			break;
		// BOOTS
		case 309:
		case 313:
		case 317:
			result = ItemType.BOOTS;
			break;
			// SHEAR
		case 359:
			result = ItemType.SHEAR;
			break;
		}
		return result;
	}
	
	/**get the item material -- dia sword => dia
	 * @param item
	 * @return int id of the item
	 */
	public static int getNeededItemId(ItemStack item) {
		int result = -1;
		;
		int itemid = item.getTypeId();
		switch (itemid) {
		//Stirng
		case 261:// BOW
			result = 287;
			break;
		
		// Wood
		case 268:
		case 269:
		case 270:
		case 271:
		case 290:
			//Wood
			result = 5;
			break;

		// Stone
		case 272:
		case 273:
		case 274:
		case 275:
		case 291:
			//Stone
			result = 4;
			break;

		// Iron
		case 256:
		case 257:
		case 258:
		case 267:
		case 292:
		case 359:
			
		//EQ
		case 306:	
		case 307:
		case 308:
		case 309:
			//Iron
			result = 265;
			break;

		// Gold
		case 283:
		case 284:
		case 285:
		case 286:
		case 294:
			
		//EQ
		case 314:	
		case 315:
		case 316:
		case 317:
		
			//Gold
			result = 266;
			break;

		// DIA
		case 276:
		case 277:
		case 278:
		case 279:
		case 293:
			
		//EQ
		case 310:	
		case 311:
		case 312:
		case 313:
			//Dia
			result = 264;
			break;
		}
		return result;
	}
	/**
	 * Changes an Item ID of a Block to the clean Word
	 * @param id
	 * @return the string with the item name
	 */
	
	private static String ItemIDToText(int id){
		String result = null;
		switch(id){
		case 5: // Wood
			result = "Wood";
			break;
		case 4: //Stone
			result = "Stone";
			break;
		case 265: //Iron
			result = "Iron";
			break;
		case 266: //Gold
			result = "Gold";
			break;
		case 264: // Dia
			result = "Diamond";
			break;
		case 287: // Dia
			result = "String";
			break;
		default:
			result = new ItemStack(id).toString();
			break;
		}
		return result;
	}
	
}
