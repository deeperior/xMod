package me.xasz.xMod.Executor;

import me.xasz.xMod.xMod;
import me.xasz.xMod.xPlayerProfile;
import me.xasz.xMod.Helper.xPlayer;
import me.xasz.xMod.Skills.xSkillType;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class xTopExecutor implements CommandExecutor  {
	
	private final xMod x;
	public xTopExecutor (final xMod instance){
		x = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,	String[] args) {
		boolean success = false;
		Player player = null;
		try{
			//Checking if sender is player object
			if(sender instanceof Player ){
				player = (Player)sender;
			} else {
				sender.sendMessage("Command is only supported for Players");
				return false;
			}
			switch(args.length){
				case 1: // one parameter is set by player
					//with parameter
					if(args.length == 1){
						xSkillType skillasked = xSkillType.NONE;
						if(args[0].equalsIgnoreCase("Axe")){
							skillasked = xSkillType.AXE;
						}else if(args[0].equalsIgnoreCase("Pickaxe")){
							skillasked = xSkillType.PICKAXE;
						}else if(args[0].equalsIgnoreCase("Shear")){
							skillasked = xSkillType.SHEAR;
						}else if(args[0].equalsIgnoreCase("Repair")){
							skillasked = xSkillType.REPAIR;
						}else if(args[0].equalsIgnoreCase("Shovel")){
							skillasked = xSkillType.SHOVEL;
						}else if(args[0].equalsIgnoreCase("Hoe")){
							skillasked = xSkillType.HOE;
						}else{
							xPlayer.sendErrorMessage(player, "Wrong Parameter: " + args[0]);
							return false;
						}
						xPlayerProfile[] profiles = x.getConnector().getAllFakeProfilesSortedBySkill(skillasked);
						//java.util.Arrays.sort( profiles );
						// 'cause sort will start smallest first, we have to go in reverse through
						xPlayer.showTopList(profiles, player, skillasked);
					}
					break;
			
				case 0: // no parameter is set by player
				default:
					player.sendMessage(ChatColor.WHITE + "[xMod]" + ChatColor.RED + "xTop - Default" );
					player.sendMessage(ChatColor.BLACK +"______________________________");
					//get fake profiles from database
					xPlayerProfile[] profiles = x.getConnector().getAllFakeProfiles();
					java.util.Arrays.sort( profiles );
					// 'cause sort will start smallest first, we have to go in reverse through
					// the array.
					int countshowed = 0;
					for (int i = profiles.length - 1; i >= 0; i--)
					{
					  player.sendMessage(ChatColor.WHITE + profiles[i].GetPlayerName()  + "    " + ChatColor.YELLOW + profiles[i].getXtraLevel());
					  if(countshowed >= 9){
						  break;
					  }else{
						  countshowed++;
					  }
					}
					break;		
			}
		}
		catch(Exception ex){
			x.getServer().broadcastMessage("Error occured on commmand:"+cmd.getName());
		}

		
		return success;
	}

}
