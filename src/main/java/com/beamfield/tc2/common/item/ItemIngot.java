package com.beamfield.tc2.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemIngot extends ItemBase {
	public static final String[] TYPES = { "ingotCopper", "ingotTin", "ingotSteel", "ingotBronze", "ingotSilver", "ingotAluminum", "ingotNickel", "ingotLead",
										   "dustCopper", "dustTin", "dustSteel", "dustBronze", "dustSilver", "dustAluminum", "dustNickel", "dustLead"};

public static final String[] MATERIALS = { "Copper", "Tin", "Steel", "Bronze", "Silver", "Aluminum", "Nickel", "Lead" };

public ItemIngot()
{
super("ingot");
this.setHasSubtypes(true);
this.setMaxDamage(0);
}

@Override
protected int getNumberOfSubItems() {
return TYPES.length;
}

@Override
protected String[] getSubItemNames() {
return TYPES;
}

@Override
public int getMetadata(int damage)
{
return damage;
}

@Override
public String getUnlocalizedName(ItemStack itemStack)
{
int idx = Math.min(TYPES.length-1, itemStack.getItemDamage());
return "item." + TYPES[idx];
}

@Override
public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
{
for (int i = 0; i < TYPES.length; i++)
{
list.add(new ItemStack(this, 1, i));
}
}

public ItemStack getItemStackForType(String typeName) {
for(int i = 0; i < TYPES.length; i++) {
if(TYPES[i].equals(typeName)) {
return new ItemStack(this, 1, i);
}
}

return null;
}

public ItemStack getIngotItem(String name) {
return getItemStackForType("ingot" + name);
}

public ItemStack getDustItem(String name) {
return getItemStackForType("dust" + name);
}
}
