package eu.endermite.wanderingvendors.trades;

import eu.endermite.wanderingvendors.WanderingVendors;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TradeManager {

    private static Configuration config = WanderingVendors.getPlugin().getConfig();


    private static boolean checkState(String configstring) {
        if (config.getString(configstring).equalsIgnoreCase("none")) {
            return false;
        } else if (config.getString(configstring).equalsIgnoreCase("item")) {
            return true;
        } else {
            return false;
        }
    }

    public static ItemStack parseResult(String configsection) {
        if (checkState("trades."+configsection+".result.type")) {

            try {
                String material = config.getString("trades."+configsection+".result.material");
                int amount = config.getInt("trades."+configsection+".result.amount");

                ItemStack result = new ItemStack(Material.getMaterial(material), amount);

                String name = config.getString("trades."+configsection+".result.name");
                List<String> lore = config.getStringList("trades."+configsection+".result.lore");
                ItemMeta resultmeta = result.getItemMeta();
                resultmeta.setLocalizedName(name);
                resultmeta.setDisplayName(name);
                resultmeta.setLore(lore);
                result.setItemMeta(resultmeta);

                return result;

            } catch(NullPointerException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static ItemStack parseIngridient(String configsection, int number) {
        if (checkState("trades."+configsection+".ingridient"+number+".type")) {

            try {
                String material = config.getString("trades."+configsection+".ingridient"+number+".material");
                int amount = config.getInt("trades."+configsection+".ingridient"+number+".amount");

                ItemStack result = new ItemStack(Material.getMaterial(material), amount);

                String name = config.getString("trades."+configsection+".ingridient"+number+".name");

                ItemMeta resultmeta = result.getItemMeta();

                resultmeta.setLocalizedName(name);
                resultmeta.setDisplayName(name);
                result.setItemMeta(resultmeta);

                return result;

            } catch(NullPointerException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    public static MerchantRecipe createMerchantRecipe(String configsection) {

        if (parseResult(configsection) == null) {
            return null;
        }

        ItemStack result = parseResult(configsection);
        int maxUses = config.getInt("trades."+configsection+".maxuses");

        MerchantRecipe recipe = null;
        try {
            recipe = new MerchantRecipe(result, maxUses);

        } catch (NullPointerException e) {
            WanderingVendors.getPlugin().getLogger().severe("Could not load result for recipe "+configsection);
        }
        assert recipe != null;

        if (parseIngridient(configsection, 1) != null) {

            recipe.addIngredient(parseIngridient(configsection, 1));
        }
        if (parseIngridient(configsection, 2) != null) {
            recipe.addIngredient(parseIngridient(configsection, 1));
        }
        return recipe;




    }

}