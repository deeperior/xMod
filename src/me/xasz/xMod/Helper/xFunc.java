package me.xasz.xMod.Helper;


import me.xasz.xMod.xMod;
import org.bukkit.inventory.ItemStack;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class xFunc {
	public static void addRecipeeMossyCobblestone(xMod x){
		ItemStack stack = new ItemStack(Material.MOSSY_COBBLESTONE, 1);	
		ShapedRecipe recipe  = new ShapedRecipe(stack);
		recipe.shape("SSS", "SBS", "SSS");
		recipe.setIngredient('S', Material.SEEDS);
		recipe.setIngredient('B', Material.COBBLESTONE);
		x.getServer().addRecipe(recipe);
	}
	public static void addRecipeMossyBrickStone(xMod x){
		ItemStack stack = new ItemStack(Material.SMOOTH_BRICK, 1,(short) 0, (byte)1);	
		ShapedRecipe recipe  = new ShapedRecipe(stack);
		recipe.shape("SSS", "SBS", "SSS");        
		recipe.setIngredient('S', Material.SEEDS);
		recipe.setIngredient('B', Material.SMOOTH_BRICK);
		x.getServer().addRecipe(recipe);
	}
	public static void addRecipeCrackedBrickStone(xMod x){
		ItemStack stack = new ItemStack(Material.SMOOTH_BRICK, 1,(short) 0, (byte)2);	
		ShapedRecipe recipe  = new ShapedRecipe(stack);
		recipe.shape("#S#", "SBS", "#S#");
		recipe.setIngredient('S', Material.COBBLESTONE);
		recipe.setIngredient('B', Material.SMOOTH_BRICK);
		x.getServer().addRecipe(recipe);
	}
	public static void addChisledStone(xMod x){
		ItemStack stack = new ItemStack(Material.SMOOTH_BRICK, 1,(short) 0, (byte)3);	
		ShapedRecipe recipe  = new ShapedRecipe(stack);
		recipe.shape("#S#", "SBS", "#S#");
		recipe.setIngredient('B', Material.FLINT);
		recipe.setIngredient('S', Material.SMOOTH_BRICK);
		x.getServer().addRecipe(recipe);
	}
	public static void addLeatherRecipe(xMod x){
		ItemStack stack = new ItemStack(Material.LEATHER, 1,(short) 0);	
		ShapedRecipe recipe  = new ShapedRecipe(stack);
		recipe.shape("#S#", "SBS", "#S#");
		recipe.setIngredient('S', Material.ROTTEN_FLESH);
		recipe.setIngredient('B', Material.COAL,1);
		x.getServer().addRecipe(recipe);
	}
	public static void addRecipeIceSnowBlock(xMod x){
		ItemStack stack = new ItemStack(Material.ICE, 1);	
		ShapedRecipe recipe  = new ShapedRecipe(stack);
		recipe.shape("SS#", "SS#", "###");        
		recipe.setIngredient('S', Material.SNOW_BLOCK);
		x.getServer().addRecipe(recipe);
		
		ShapedRecipe recipe1  = new ShapedRecipe(stack);
		recipe1.shape("#SS", "#SS", "###");        
		recipe1.setIngredient('S', Material.SNOW_BLOCK);
		x.getServer().addRecipe(recipe1);
		
		ShapedRecipe recipe2  = new ShapedRecipe(stack);
		recipe2.shape("###", "#SS", "#SS");        
		recipe2.setIngredient('S', Material.SNOW_BLOCK);
		x.getServer().addRecipe(recipe2);
		
		ShapedRecipe recipe3  = new ShapedRecipe(stack);
		recipe3.shape("###", "SS#", "SS#");        
		recipe3.setIngredient('S', Material.SNOW_BLOCK);
		x.getServer().addRecipe(recipe3);
	}
	public static boolean isSword(int id){
		boolean success = false;
		switch (id) {		
		// SWORD
		case 267:
		case 268:
		case 272:
		case 276:
		case 283:
			success = true;
			break;
		}
		return success;
	}
	public static boolean isAxe(int id){
		boolean success = false;
		switch (id) {		
		// AXE
		case 258:
		case 271:
		case 275:
		case 279:
		case 286:
			success = true;
			break;
		}
		return success;
	}
	public static boolean isPickaxe(int id){
		boolean success = false;
		switch (id) {		
		// PICKAXE
		case 257:
		case 270:
		case 274:
		case 278:
		case 285:
			success = true;
			break;
		}
		return success;
	}
	public static boolean isShovel(int id){
		boolean success = false;
		switch (id) {		
		// SHOVEL
		case 256:
		case 269:
		case 273:
		case 277:
		case 284:
			success = true;
			break;
		}
		return success;
	}
	public static boolean isHoe(int id){
		boolean success = false;
		switch (id) {		
		// Hoe
		case 290:
		case 291:
		case 292:
		case 293:
		case 294:
			success = true;
			break;
		}
		return success;
	}
	public static boolean isBow(int typeId) {
		if(typeId == 261)
			return true;
		return false;
	}
}
