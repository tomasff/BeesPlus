package com.tomff.beesplus.handlers;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class BeehiveHandler implements Listener {

    @EventHandler
    public void onBeeHiveBreak(BlockDropItemEvent event) {
        BlockState blockState = event.getBlockState();
        Material blockType = blockState.getType();

        if (blockType != Material.BEEHIVE) {
            return;
        }

        if (event.getItems().size() == 0) {
            return;
        }

        event.getItems()
                .stream()
                .filter(this::validBeehive)
                .findFirst()
                .ifPresent(item -> saveState(blockState, item));
    }

    private boolean validBeehive(Item item) {
        ItemStack itemStack = item.getItemStack();

        return (!itemStack.hasItemMeta() && itemStack.getType() == Material.BEEHIVE);
    }

    private void saveState(BlockState state, Item item) {
        ItemStack itemStack = item.getItemStack();
        BlockStateMeta itemMeta = (BlockStateMeta) itemStack.getItemMeta();

        itemMeta.setBlockState(state);
        itemStack.setItemMeta(itemMeta);
        item.setItemStack(itemStack);
    }
}
