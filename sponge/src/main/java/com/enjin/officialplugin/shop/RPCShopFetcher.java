package com.enjin.officialplugin.shop;

import com.enjin.core.EnjinServices;
import com.enjin.officialplugin.EnjinMinecraftPlugin;
import com.enjin.rpc.mappings.mappings.general.RPCData;
import com.enjin.rpc.mappings.mappings.shop.Shop;
import com.enjin.rpc.mappings.services.ShopService;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RPCShopFetcher implements Runnable {
    private EnjinMinecraftPlugin plugin;
    private UUID uuid;

    public RPCShopFetcher(Player player) {
        this.plugin = EnjinMinecraftPlugin.getInstance();
        this.uuid = player.getUniqueId();
    }

    @Override
    public void run() {
        Optional<Player> p = plugin.getGame().getServer().getPlayer(uuid);

        if (!p.isPresent()) {
            plugin.debug("Player is not present. No longer fetching shop data.");
            return;
        }

        Player player = p.get();
        RPCData<List<Shop>> data = EnjinServices.getService(ShopService.class).get(plugin.getAuthKey(), player.getName());

        if (data == null) {
            player.sendMessage(Texts.builder("Failed to fetch shop data.").color(TextColors.RED).build());
            return;
        }

        if (data.getError() != null) {
            player.sendMessage(Texts.of(data.getError().getMessage()));
            return;
        }

        List<Shop> shops = data.getResult();

        if (shops == null || shops.isEmpty()) {
            player.sendMessage(Texts.builder("There are no shops available at this time.").color(TextColors.RED).build());
            return;
        }

        if (!PlayerShopInstance.getInstances().containsKey(player.getUniqueId())) {
            PlayerShopInstance.getInstances().put(player.getUniqueId(), new PlayerShopInstance(shops));
        } else {
            PlayerShopInstance.getInstances().get(player.getUniqueId()).update(shops);
        }

        PlayerShopInstance instance = PlayerShopInstance.getInstances().get(player.getUniqueId());
        ShopUtil.sendTextShop(player, instance, -1);
    }
}
