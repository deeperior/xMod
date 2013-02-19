package me.xasz.xMod;


import me.xasz.xMod.Skills.xSkillAxe;
import me.xasz.xMod.Skills.xSkillPickaxe;
import me.xasz.xMod.Skills.xSkillShear;
import me.xasz.xMod.Skills.xSkillShovel;

import org.bukkit.Material;
import org.bukkit.block.Block;
/** Used for tagging the player placed blocks to prevent xp farming.
 *  Watched blocks are tagged with a special byte in the blockobject
 * @author xasz
 *
 */
public class xBlockWatcher {


	private final xMod x;
	public xBlockWatcher(final xMod instance){
		this.x = instance;
	}
	/** check if the block is a playerplaced block
	 * @param the block to check
	 * @return true if it is a playerplaced block, false if not
	 */
	public boolean isWatched(Block block){
		//if block should not watched anyway .. return false
		if(!shouldBlockBeWatched(block)){
			return false;
		}
		return x.getConnector().isBlockWatched(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
	}
	public boolean isWatchedForce(Block block){
		return x.getConnector().isBlockWatched(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
	}
	/** set a block to watch
	 * @param the block to watch
	 */
	public void watch(Block block){
		if(shouldBlockBeWatched(block)){
			x.getConnector().watchBlock(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
		}
	}	
	/** set a block to watch
	 * @param the block to watch
	 */
	public void watchForce(Block block){
		x.getConnector().watchBlock(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
	}
	/** set a block to diswatch
	 * @param the block to diswatch
	 */	
	public void diswatch(Block block){
		x.getConnector().disBlock(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
	}
	public static boolean shouldBlockBeWatched(Block placedBlock){
		//check if the block is a type, that should be watched.
		if(	xSkillAxe.isItemBlockHandled(placedBlock.getType()) ||
			xSkillPickaxe.isItemBlockHandled(placedBlock.getType()) ||
			xSkillShear.isItemBlockHandled(placedBlock.getType()) ||
			xSkillShovel.isItemBlockHandled(placedBlock.getType())
			){
				return true;
			}
		return false;
	}
	
	
}
