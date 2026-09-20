package net.antymology.power;

import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.InventoryPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.antymology.SentientSlime;
import net.antymology.screen.SlimeInventoryScreenHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import virtuoel.pehkui.api.ScaleTypes;

public class SlimeInventoryPower extends InventoryPower {

    public SlimeInventoryPower(
        PowerType<?> type,
        LivingEntity entity,
        String containerTitle,
        boolean shouldDropOnDeath,
        boolean recoverable
    ) {
        super(
            type,
            entity,
            containerTitle,
            ContainerType.CHEST,
            shouldDropOnDeath,
            stack -> true,
            recoverable
        );
    }

    private int getTotalItems() {
        int total = 0;

        for (int i = 0; i < size(); i++) {
            total += getStack(i).getCount();
        }

        return total;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        super.setStack(slot, stack);
        updateScale();
    }

    @Override
    public void fromTag(NbtElement tag) {
        super.fromTag(tag);
        updateScale();
    }

    private void updateScale() {
        int totalItems = getTotalItems();

        float minScale = 0.5F;
        float maxScale = 2.0F;
        float maxItems = 1728.0F;

        float scale = minScale
            + Math.min(totalItems / maxItems, 1.0F) * (maxScale - minScale);

        ScaleTypes.BASE.getScaleData(entity).setTargetScale(scale);
    }

    @Override
    public void onUse() {
        if (!isActive()) {
            return;
        }

        if (!entity.getWorld().isClient && entity instanceof PlayerEntity player) {
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                (syncId, playerInventory, playerEntity) ->
                    new SlimeInventoryScreenHandler(
                        syncId,
                        playerInventory,
                        this
                    ),
                getContainerTitle()
            ));
        }
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(
            SentientSlime.id("slime_inventory"),
            new SerializableData()
                .add("title", SerializableDataTypes.STRING, "container.inventory")
                .add("drop_on_death", SerializableDataTypes.BOOLEAN, true)
                .add("recoverable", SerializableDataTypes.BOOLEAN, true)
                .add("key", io.github.apace100.apoli.data.ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
            data -> (type, livingEntity) -> {
                SlimeInventoryPower power = new SlimeInventoryPower(
                    type,
                    livingEntity,
                    data.getString("title"),
                    data.getBoolean("drop_on_death"),
                    data.getBoolean("recoverable")
                );

                power.setKey(data.get("key"));
                return power;
            }
        ).allowCondition();
    }
}