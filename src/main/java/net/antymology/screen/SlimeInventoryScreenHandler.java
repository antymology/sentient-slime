package net.antymology.screen;

import net.antymology.register.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SlimeInventoryScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public SlimeInventoryScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(27));
    }

    public SlimeInventoryScreenHandler(
        int syncId,
        PlayerInventory playerInventory,
        Inventory inventory
    ) {
        super(ModScreenHandlers.SLIME_INVENTORY, syncId);

        this.inventory = inventory;

        checkSize(inventory, 27);
        inventory.onOpen(playerInventory.player);

        int[][] positions = {
            {7, 3},
            {38, 4},
            {69, 8},
            {101, 2},
            {133, 6},
            {157, 13},

            {21, 16},
            {52, 19},
            {83, 13},
            {114, 17},
            {145, 21},

            {5, 30},
            {36, 27},
            {67, 34},
            {98, 28},
            {129, 32},
            {158, 37},

            {18, 44},
            {49, 41},
            {80, 47},
            {111, 43},
            {142, 46},

            {6, 49},
            {37, 48},
            {68, 51},
            {97, 51},
            {130, 49},
            {157, 52}
        };

        for (int i = 0; i < 27; i++) {
            this.addSlot(new Slot(
                inventory,
                i,
                positions[i][0],
                positions[i][1]
            ));
        }

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(
                    playerInventory,
                    column + row * 9 + 9,
                    8 + column * 18,
                    84 + row * 18
                ));
            }
        }

        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(
                playerInventory,
                column,
                8 + column * 18,
                142
            ));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    @Override
    public net.minecraft.item.ItemStack quickMove(
        PlayerEntity player,
        int slot
    ) {
        net.minecraft.item.ItemStack newStack = net.minecraft.item.ItemStack.EMPTY;

        Slot sourceSlot = this.slots.get(slot);

        if (sourceSlot != null && sourceSlot.hasStack()) {
            net.minecraft.item.ItemStack sourceStack = sourceSlot.getStack();
            newStack = sourceStack.copy();

            if (slot < 27) {
                if (!insertItem(sourceStack, 27, this.slots.size(), true)) {
                    return net.minecraft.item.ItemStack.EMPTY;
                }
            } else if (!insertItem(sourceStack, 0, 27, false)) {
                return net.minecraft.item.ItemStack.EMPTY;
            }

            if (sourceStack.isEmpty()) {
                sourceSlot.setStack(net.minecraft.item.ItemStack.EMPTY);
            } else {
                sourceSlot.markDirty();
            }
        }

        return newStack;
    }
}