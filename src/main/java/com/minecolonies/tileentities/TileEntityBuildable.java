package com.minecolonies.tileentities;

import com.minecolonies.MineColonies;
import com.minecolonies.network.packets.TileEntityPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;

import java.util.UUID;

public abstract class TileEntityBuildable extends TileEntityChest
{
    private int                buildingLevel;
    private boolean            hasWorker;
    private TileEntityTownHall townHall;
    private String             hutName;

    public TileEntityBuildable()
    {
        this.buildingLevel = 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagCompound nbtTagCompound = (NBTTagCompound) compound.getTag("nbtTagCompound");
        this.buildingLevel = nbtTagCompound.getInteger("buildingLvl");
        this.hasWorker = nbtTagCompound.getBoolean("hasWorker");
        this.hutName = nbtTagCompound.getString("hutName");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("buildingLvl", buildingLevel);
        nbtTagCompound.setBoolean("hasWorker", hasWorker);
        nbtTagCompound.setString("hutName", hutName);
        compound.setTag("nbtTagCompound", nbtTagCompound);
    }

    public void sendPacket()
    {
        NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        TileEntityPacket packet = new TileEntityPacket(xCoord, yCoord, zCoord, data);

        MineColonies.packetPipeline.sendToServer(packet);
    }

    public int getBuildingLevel()
    {
        return buildingLevel;
    }

    public void setBuildingLevel(int buildingLevel)
    {
        this.buildingLevel = buildingLevel;
    }

    public boolean isHasWorker()
    {
        return hasWorker;
    }

    public void setHasWorker(boolean hasWorker)
    {
        this.hasWorker = hasWorker;
    }

    public String getHutName()
    {
        return hutName;
    }

    public void setHutName(String hutName)
    {
        this.hutName = hutName;
    }

    public TileEntityTownHall getTownHall()
    {
        return townHall;
    }

    public void setTownHall(TileEntityTownHall townHall)
    {
        this.townHall = townHall;
    }

    public void requestBuilding(EntityPlayer player)
    {
        if(!(buildingLevel >= 3)) //TODO
            for(UUID owners : getTownHall().getOwners())
                if(owners == player.getUniqueID())
                    getTownHall().addBuildingForUpgrade(xCoord, yCoord, zCoord);
    }

    /**
     * Returns the required sex of a citizen before it can be added to a hut.
     * 0 = man, 1 = female
     * @return sex
     */
    public int getRequiredSex()
    {
        return 0;
    }
}
