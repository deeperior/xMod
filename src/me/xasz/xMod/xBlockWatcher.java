package me.xasz.xMod;


import java.sql.SQLException;
import java.util.List;

import org.bukkit.block.Block;

import de.diddiz.LogBlock.BlockChange;
import de.diddiz.LogBlock.LogBlock;
import de.diddiz.LogBlock.QueryParams;
import de.diddiz.LogBlock.QueryParams.BlockChangeType;
/** Used for tagging the player placed blocks to prevent xp farming.
 *  Watched blocks are tagged with a special byte in the blockobject
 * @author xasz
 *
 */
public class xBlockWatcher {


	private final xMod x;
	private LogBlock logblock = null;
	
	
	public xBlockWatcher(final xMod instance){
		this.x = instance;
		logblock = (LogBlock) x.getServer().getPluginManager().getPlugin("LogBlock");
	}
	/** check if the block is a playerplaced block
	 * @param the block to check
	 * @return true if it is a playerplaced block, false if not
	 */
	public boolean isWatched(Block block){
		//if block should not watched anyway .. return false
		/*if(!shouldBlockBeWatched(block)){
			return false;
		}
		return x.getConnector().isBlockWatched(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());*/
		
		return isWatchedForce(block);
	}
	public boolean isWatchedForce(Block block){
		//return x.getConnector().isBlockWatched(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
		boolean haveLogFile = false;
		QueryParams params = new QueryParams(logblock);
		System.out.println(block.getLocation().toString());
		params.loc = block.getLocation();
		params.bct = BlockChangeType.CREATED;
		params.world = block.getWorld();
		params.radius = 0;
		params.needPlayer = true;
		params.needCoords = true;
		params.needData = true;
		params.needType = true;
		params.limit = 1;
		System.out.println(params.getQuery());
		try {
			List<BlockChange> changes = logblock.getBlockChanges(params);
			System.out.println(changes.size());
			   for (BlockChange bc : logblock.getBlockChanges(params)) {
			        System.out.println(bc.toString());
			    }
			if(changes.size() > 0){	
				haveLogFile = true;
			}
		} catch (SQLException e) {
			System.out.println(e);		
			haveLogFile = true;
		}
		return haveLogFile;
	}
	/** set a block to watch
	 * @param the block to watch
	 */
	public void watch(Block block){
		/*if(shouldBlockBeWatched(block)){
			x.getConnector().watchBlock(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
		}*/
	}	
	/** set a block to watch
	 * @param the block to watch
	 */
	public void watchForce(Block block){
		//x.getConnector().watchBlock(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
	}
	/** set a block to diswatch
	 * @param the block to diswatch
	 */	
	public void diswatch(Block block){
		//x.getConnector().disBlock(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
	}
	public static boolean shouldBlockBeWatched(Block placedBlock){
		//check if the block is a type, that should be watched.
		/*if(	xSkillAxe.isItemBlockHandled(placedBlock.getType()) ||
			xSkillPickaxe.isItemBlockHandled(placedBlock.getType()) ||
			xSkillShear.isItemBlockHandled(placedBlock.getType()) ||
			xSkillShovel.isItemBlockHandled(placedBlock.getType())
			){
				return true;
			}*/
		return false;
	}
	
	
}
