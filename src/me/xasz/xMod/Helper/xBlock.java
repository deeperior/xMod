package me.xasz.xMod.Helper;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;


public class xBlock {
	public static boolean dropNaturallyItem(final int itemId, Block dropSource){
		dropSource.getWorld().dropItemNaturally(dropSource.getLocation(), new ItemStack(itemId,1));
		return true;
	}
	public static boolean dropNaturallyItem(final Material mat, Block dropSource){
		dropSource.getWorld().dropItemNaturally(dropSource.getLocation(), new ItemStack(mat,1));
		return true;
	}
	public static boolean dropNaturallyItem(final ItemStack stack, Block dropSource){
		dropSource.getWorld().dropItemNaturally(dropSource.getLocation(), stack);
		return true;
	}
}
