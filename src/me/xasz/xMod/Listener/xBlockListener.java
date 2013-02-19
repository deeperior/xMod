package me.xasz.xMod.Listener;


import java.util.List;

import me.xasz.xMod.xMod;
import me.xasz.xMod.Helper.xPlayer;
import me.xasz.xMod.Skills.xSkillAxe;
import me.xasz.xMod.Skills.xSkillHoe;
import me.xasz.xMod.Skills.xSkillPickaxe;
import me.xasz.xMod.Skills.xSkillShear;
import me.xasz.xMod.Skills.xSkillShovel;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;



/**
 * @author xasz
 *
 */
public class xBlockListener implements Listener{

	private final xMod x;
	public xBlockListener (final xMod instance){
		x = instance;
	}
	
	/**
	 * block place events
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent event){

		
		Block placedBlock = event.getBlock();
		
		//the block which was set, is will be watched to prevent doup-farming
		x.blockWatcher.watch(placedBlock);
		
		//check if player build an anvil
		if(event.getBlock().getTypeId() == 42){
			xPlayer.sendSuccessMessage(event.getPlayer(), "You build an anvil. Be Happy - Rightclick with an item to repair.");
		}
		
		//Check DEBUG Durability-Checker
		if(xMod.DEBUG){
			if(event.getBlock().getTypeId() == 41){
				xPlayer.sendDebugMessage(event.getPlayer(), "You build an durability Checkstone. Only availible in Debugmode");
			}			
		}

	}
	/**
	 * block break events
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event){
		//check if the block was watched ( means player placed), if yes the block will be diswatched
		if(x.blockWatcher.isWatched(event.getBlock())){
			x.blockWatcher.diswatch(event.getBlock());
			xPlayer.sendDebugMessage(event.getPlayer(),"Block was watched");	
		}else{
			xPlayer.sendDebugMessage(event.getPlayer(),"Block was not watched");			
			if(xSkillPickaxe.isItemBlockHandled(event.getBlock().getType())){
				xPlayer.sendDebugMessage(event.getPlayer(),"You killed an Pickaxe-Skill Block");
				xSkillPickaxe.calc(event.getPlayer(), event.getBlock());				
			}
			if(xSkillShovel.isItemBlockHandled(event.getBlock().getType())){
				xPlayer.sendDebugMessage(event.getPlayer(),"You killed an Shovel-Skill Block");
				xSkillShovel.calc(event.getPlayer(), event.getBlock());		
			}
			if(xSkillAxe.isItemBlockHandled(event.getBlock().getType())){
				xPlayer.sendDebugMessage(event.getPlayer(),"You killed an AXE-Skill Block");
				xSkillAxe.calc(event.getPlayer(), event.getBlock());	
			}		
			if(xSkillShear.isItemBlockHandled(event.getBlock().getType()) || event.getBlock().getTypeId() == 111){
				xPlayer.sendDebugMessage(event.getPlayer(),"You killed an SHEAR-Skill Block");	
				xSkillShear.calc(event.getPlayer(), event.getBlock());	
			}			
			if(xSkillHoe.isItemBlockHandled(event.getBlock().getType())){
				xPlayer.sendDebugMessage(event.getPlayer(),"You killed an HOE-Skill Block");	
				xSkillHoe.calc(event.getPlayer(), event.getBlock());	
			}	
		}		
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPistonExtend(BlockPistonExtendEvent event) {
		List<Block> iBlocks = event.getBlocks();
		for(Block block : iBlocks){
			//newposition
			this.x.blockWatcher.watchForce(block.getRelative(event.getDirection(),1));
		}
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPistonRetract(BlockPistonRetractEvent event) {
		this.x.blockWatcher.watchForce(event.getBlock().getRelative(event.getDirection(),1));
	}
	
	
	
}
