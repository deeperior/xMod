package me.xasz.xMod;

import me.xasz.xMod.Helper.xPlayer;
import me.xasz.xMod.Skills.xSkillType;

import org.bukkit.entity.Player;

import java.lang.Comparable;

/**
 * holds informations for the player like skilllevel and other informations
 * @author xasz
 *
 */
public class xPlayerProfile implements Comparable<xPlayerProfile>
{ 
	// Values from the database
	private int repairXP = 1;
	private int repairLVL = 0;
	
	private int pickaxeXP = 1;
	private int pickaxeLVL = 0;
	
	private int hoeXP = 1;
	private int hoeLVL = 0;

	private int axeXP = 1;
	private int axeLVL = 0;
	
	private int shovelXP = 1;
	private int shovelLVL = 0;
	
	
	private int shearXP = 1;
	private int shearLVL = 0;
	
	private int enchantmentXP = 1;
	private int enchantmentLVL = 0;
	
	private Player player = null;
	private final String playername;
	
	// Class variables
	private final xMod x;
	private boolean isChanged = false;
	
	// Variables to reduce recomputation of "Current Level Max XP's"
	// TODO This values have to be recalculated if the configuration is reloaded, the resulting levels vice versa.
	private int repairCurrentLevelMaxXP = 0;
	private int pickaxeCurrentLevelMaxXP = 0;
	private int axeCurrentLevelMaxXP = 0;
	private int shovelCurrentLevelMaxXP = 0;
	private int shearCurrentLevelMaxXP = 0;
	private int hoeCurrentLevelMaxXP = 0;
	private int enchantmentCurrentLevelMaxXP = 0;
	
	
	
	/**  sets all standard
	 * @param instance
	 * @param tplayer
	 */
	public xPlayerProfile (final xMod instance, Player tplayer)
	{
		x = instance;
		player = tplayer;
		playername = tplayer.getDisplayName();
		//refreshLevelXP();
	}
	
	public xPlayerProfile (final xMod instance, String tplayername)
	{
		x = instance;
		player = null;
		playername =  tplayername;
		//refreshLevelXP();
	}
	
	public boolean isChanged()
	{
		return isChanged;
	}

	public int getXtraLevel()
	{
		return repairLVL + pickaxeLVL + axeLVL + shearLVL + shovelLVL + hoeLVL;
	}

	/**
	 * sends information to the player
	 * sends all informations about his level and skills
	 */
	public void getLevelOverview()
	{
		xPlayer.getLevelOverview(this,this );
	}

	public xMod getX()
	{
		return x;
	}

	public void setPlayer(Player player) {
		isChanged = true;
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public String GetPlayerName()
	{
		return playername;
	}
	
	public String toString()
	{
		return playername;
	}

	//REPAIR SETTER AND GETTER
	public int getRepairXP() {
		return repairXP;
	}
	public void setRepairXP(int repairXP)
	{
		isChanged = true;
		this.repairXP = repairXP;
		
		if (this.repairXP > 0)
		{
			refreshRepairLvl();
		}
	}
	
	public int getRepairLVL() {
		return repairLVL;
	}
	
	public void refreshRepairCurrentLevelMaxXP()
	{
		repairCurrentLevelMaxXP = (int)(xMod.repairFactorA* Math.pow(getRepairLVL()+1, 2) + xMod.repairFactorB * (getRepairLVL()+1) + xMod.repairFactorC);
	}

	public int getRepairCurrentLevelMaxXP()
	{
		return this.repairCurrentLevelMaxXP;
	}

	//PICKAXE SETTER AND GETTER
	public int getPickaxeXP()
	{
		return pickaxeXP;
	}

	public void setPickaxeXP(int pickaxeXP)
	{
		isChanged = true;
		this.pickaxeXP = pickaxeXP;
		
		if (this.pickaxeXP > 0)
		{
			refreshPickaxeLvl();
		}
	}

	public int getPickaxeLVL()
	{
		return pickaxeLVL;
	}

	public void refreshPickaxeCurrentLevelMaxXP()
	{
		pickaxeCurrentLevelMaxXP =  (int)( xMod.pickaxeFactorA* Math.pow(getPickaxeLVL()+1, 2) + xMod.pickaxeFactorB * (getPickaxeLVL()+1) + xMod.pickaxeFactorC);
	}
	
	public int getPickaxeCurrentLevelMaxXP()
	{
		return this.pickaxeCurrentLevelMaxXP;
	}
	
	//HOE SETTER AND GETTER
	public int getHoeXP()
	{
		return hoeXP;
	}

	public void setHoeXP(int hoeXP)
	{
		isChanged = true;
		this.hoeXP = hoeXP;
		
		if (this.hoeXP > 0)
		{
			refreshHoeLvl();
		}
	}

	public int getHoeLVL()
	{
		return hoeLVL;
	}

	public void refreshHoeCurrentLevelMaxXP()
	{
		hoeCurrentLevelMaxXP =  (int)( xMod.hoeFactorA* Math.pow(getHoeLVL()+1, 2) + xMod.hoeFactorB * (getHoeLVL()+1) + xMod.hoeFactorC);
	}
	
	public int getHoeCurrentLevelMaxXP()
	{
		return this.hoeCurrentLevelMaxXP;
	}
	
	
	
	//AXE SETTER AND GETTER
	public int getAxeXP()
	{
		return axeXP;
	}

	public void setAxeXP(int axeXP)
	{
		isChanged = true;
		this.axeXP = axeXP;		
		
		if (this.axeXP > 0)
		{
			refreshAxeLvl();
		}
	}

	public int getAxeLVL()
	{
		return axeLVL;
	}

	public int getAxeCurrentLevelMaxXP()
	{
		return this.axeCurrentLevelMaxXP;
	}
	
	public void refreshAxeCurrentLevelMaxXP()
	{
		this.axeCurrentLevelMaxXP = (int)( xMod.axeFactorA* Math.pow(getAxeLVL()+1, 2) + xMod.axeFactorB * (getAxeLVL()+1) + xMod.axeFactorC);
	}

	//SHOVEL SETTER AND GETTER
	public int getShovelLVL()
	{
		return shovelLVL;
	}

	public int getShovelCurrentLevelMaxXP()
	{
		return this.shovelCurrentLevelMaxXP;
	}
	
	public void refreshShovelCurrentLevelMaxXP()
	{
		this.shovelCurrentLevelMaxXP = (int)( xMod.shovelFactorA* Math.pow(getShovelLVL()+1, 2) + xMod.shovelFactorB * (getShovelLVL()+1) + xMod.shovelFactorC);
	}

	public int getShovelXP()
	{
		return shovelXP;
	}

	public void setShovelXP(int shovelXP)
	{
		isChanged = true;
		this.shovelXP = shovelXP;
		
		if(this.shovelXP > 0)
		{
			refreshShovelLvl();
		}
	}

	
	//SHEAR SETTER AND GETTER
	public int getShearXP()
	{
		return shearXP;
	}

	public void setShearXP(int shearXP)
	{
		isChanged = true;
		this.shearXP = shearXP;
		
		if (shearXP > 0)
		{
			refreshShearLvl();
		}
	}

	public int getShearLVL()
	{
		return shearLVL;
	}

	public int getShearCurrentLevelMaxXP()
	{
		return this.shearCurrentLevelMaxXP;
	}
	
	public void refreshShearCurrentLevelMaxXP()
	{
		this.shearCurrentLevelMaxXP = (int)( xMod.shearFactorA* Math.pow(getShearLVL()+1, 2) + xMod.shearFactorB * (getShearLVL()+1) + xMod.shearFactorC);
	}
	
	//ENCHANTMENT SETTER AND GETTER
		public int getEnchantmentXP() {
			return enchantmentXP;
		}
		public void setEnchantmentXP(int enchantmentXP)
		{
			isChanged = true;
			this.enchantmentXP = enchantmentXP;
			
			if (this.enchantmentXP > 0)
			{
				refreshEnchantmentLvl();
			}
		}
		
		public int getEnchantmentLVL() {
			return enchantmentLVL;
		}
		
		public void refreshEnchantmentCurrentLevelMaxXP()
		{
			enchantmentCurrentLevelMaxXP = (int)(xMod.repairFactorA* Math.pow(getRepairLVL()+1, 2) + xMod.repairFactorB * (getRepairLVL()+1) + xMod.repairFactorC);
		}

		public int getEnchantmentCurrentLevelMaxXP()
		{
			return this.enchantmentCurrentLevelMaxXP;
		}
	
	public void refreshRepairLvl()
	{
		repairLVL = (int)(-xMod.repairFactorB + Math.sqrt(Math.pow(xMod.repairFactorB, 2)-4*xMod.repairFactorA*(xMod.repairFactorC-repairXP)))/ (2*xMod.repairFactorA);
		refreshRepairCurrentLevelMaxXP();
	}
	
	public void refreshPickaxeLvl()
	{
		pickaxeLVL = (int)(-xMod.pickaxeFactorB + Math.sqrt(Math.pow(xMod.pickaxeFactorB, 2)-4*xMod.pickaxeFactorA*(xMod.pickaxeFactorC-pickaxeXP)))/ (2*xMod.pickaxeFactorA);
		refreshPickaxeCurrentLevelMaxXP();
	}
	
	public void refreshShovelLvl()
	{
		shovelLVL = (int)(-xMod.shovelFactorB + Math.sqrt(Math.pow(xMod.shovelFactorB, 2)-4*xMod.shovelFactorA*(xMod.shovelFactorC-shovelXP)))/ (2*xMod.shovelFactorA);
		refreshShovelCurrentLevelMaxXP();
	}
	
	public void refreshAxeLvl()
	{
		axeLVL = (int)(-xMod.axeFactorB + Math.sqrt(Math.pow(xMod.axeFactorB, 2)-4*xMod.axeFactorA*(xMod.axeFactorC-axeXP)))/ (2*xMod.axeFactorA);
		refreshAxeCurrentLevelMaxXP();
	}
	

	
	public void refreshShearLvl()
	{
		shearLVL = (int)(-xMod.shearFactorB + Math.sqrt(Math.pow(xMod.shearFactorB, 2)-4*xMod.shearFactorA*(xMod.shearFactorC-shearXP)))/ (2*xMod.shearFactorA);
		refreshShearCurrentLevelMaxXP();
	}
	public void refreshHoeLvl()
	{
		hoeLVL = (int)(-xMod.hoeFactorB + Math.sqrt(Math.pow(xMod.hoeFactorB, 2)-4*xMod.hoeFactorA*(xMod.hoeFactorC-hoeXP)))/ (2*xMod.hoeFactorA);
		refreshHoeCurrentLevelMaxXP();
	}
	public void refreshEnchantmentLvl()
	{
		repairLVL = (int)(-xMod.repairFactorB + Math.sqrt(Math.pow(xMod.repairFactorB, 2)-4*xMod.repairFactorA*(xMod.repairFactorC-repairXP)))/ (2*xMod.repairFactorA);
		refreshEnchantmentCurrentLevelMaxXP();
	}
	
	/** adds exp to the players profile
	 * @param skill
	 * @param xpGain
	 */
	public void gainXP(xSkillType skill, int xpGain)
	{
		switch (skill)
		{
			case REPAIR:
			{
				repairXP += xpGain;
				if (repairLVL < xMod.repairMaxLevel)
				{
					if (repairXP >= this.getRepairCurrentLevelMaxXP())
					{
						repairLVL++;
						refreshRepairCurrentLevelMaxXP();
						xPlayer.sendSuccessMessage(player, skill.toString() + "Level UP - Your new REPAIR-Level is: " + repairLVL );

					}
				}
			} break;
			
			case ENCHANTMENT:
			{
				enchantmentXP += xpGain;
				if (enchantmentLVL < xMod.enchantmentMaxLevel)
				{
					if (enchantmentXP >= this.getRepairCurrentLevelMaxXP())
					{
						enchantmentLVL++;
						refreshRepairCurrentLevelMaxXP();
						xPlayer.sendSuccessMessage(player, skill.toString() + "Level UP - Your new ENCHANTMENT-Level is: " + enchantmentLVL );
					}
				}
			} break;
			
			case PICKAXE:
			{
				pickaxeXP += xpGain;
				if (pickaxeLVL < xMod.pickaxeMaxLevel)
				{
					if (pickaxeXP >= this.getPickaxeCurrentLevelMaxXP())
					{
						pickaxeLVL++;
						refreshPickaxeCurrentLevelMaxXP();
						xPlayer.sendSuccessMessage(player, skill.toString() + "Level UP - Your new PICKAXE-Level is: " + pickaxeLVL );
					}
				}
			} break;
			

			case AXE:
			{
				axeXP += xpGain;
				if (axeLVL < xMod.axeMaxLevel)
				{
					if (axeXP >= this.getAxeCurrentLevelMaxXP())
					{
						axeLVL++;
						refreshAxeCurrentLevelMaxXP();
						xPlayer.sendSuccessMessage(player, skill.toString() + "Level UP - Your new AXE-Level is: " + axeLVL );
					}
				}
			} break;
			
			case SHOVEL:
			{
				shovelXP += xpGain;
				if (shovelLVL < xMod.shovelMaxLevel)
				{
					if (shovelXP >= this.getShovelCurrentLevelMaxXP())
					{
						shovelLVL++;
						refreshShovelCurrentLevelMaxXP();
						xPlayer.sendSuccessMessage(player, skill.toString() + "Level UP - Your new SHOVEL-Level is: " + shovelLVL );
					}
				}
			} break;
			

			case SHEAR:
			{
				shearXP += xpGain;
				if (shearLVL < xMod.shearMaxLevel)
				{
					if (shearXP >= this.getShearCurrentLevelMaxXP())
					{
						shearLVL++;
						refreshShearCurrentLevelMaxXP();
						xPlayer.sendSuccessMessage(player, skill.toString() + "Level UP - Your new SHEAR-Level is: " + shearLVL );
					}
				}
			} break;

			case HOE:
			{
				hoeXP += xpGain;
				if (hoeLVL < xMod.hoeMaxLevel)
				{
					if (hoeXP >= this.getHoeCurrentLevelMaxXP())
					{
						hoeLVL++;
						refreshHoeCurrentLevelMaxXP();
						xPlayer.sendSuccessMessage(player, skill.toString() + "Level UP - Your new HOE-Level is: " + hoeLVL );
					}
				}
			} break;
			
			default:
			{
				xPlayer.sendErrorMessage(player, "Ok you shouldn't be here!");
			} break;
		}

		isChanged = true;

		pseudoSave();
	}
	private int saveCounter = 0;
	private void pseudoSave(){
		if(saveCounter >= xMod.BlocksToCountUntilPlayerProfileSave){
			x.Connector.savePlayerProfile(this);
			isChanged = false;
			saveCounter = 0;
		}else{
			saveCounter++;
		}		
	}
	
	public int compareTo(xPlayerProfile other)
	{
      if (this.getXtraLevel() < other.getXtraLevel())
      {
          return -1;
      }

      if (this.getXtraLevel() > other.getXtraLevel())
      {
          return 1;
      }

      return 0;
  }
}
