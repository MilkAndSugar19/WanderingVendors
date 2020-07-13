package eu.endermite.wanderingvendors.trades.types;

import eu.endermite.wanderingvendors.WanderingVendors;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class ItemTrade {

    private static final Configuration config = WanderingVendors.getPlugin().getConfig();

    public static ItemStack getItem(String configsection, String type) {

        int amount = config.getInt("trades." + configsection + "." + type + ".amount");

        String material = config.getString("trades." + configsection + "." + type + ".material");

        ItemStack result = new ItemStack(Material.getMaterial(material), amount);

        ItemMeta resultmeta = result.getItemMeta();

        try {
            String name = config.getString("trades." + configsection + "." + type + ".name");
            resultmeta.setDisplayName(name);
        } catch (NullPointerException ignored) {
        }

        try {
            List<String> lore = config.getStringList("trades." + configsection + "." + type + ".lore");
            if (!lore.isEmpty())
                resultmeta.setLore(lore);
        } catch (NullPointerException ignored) {
        }

        try {
            int cmd = config.getInt("trades." + configsection + "." + type + ".custommodeldata");
            if (cmd != 0)
                resultmeta.setCustomModelData(cmd);
        } catch (NullPointerException ignored) {
        }

        result.setItemMeta(resultmeta);

        try {
            for (String enchdata : config.getStringList("trades." + configsection + "." + type + ".enchants")) {
                String[] ench = enchdata.split(":");
                result.addUnsafeEnchantment(Enchantment.getByName(ench[0]), Integer.parseInt(ench[1]));
            }
        } catch (NullPointerException ignored) {
        }


        return result;

    }

}