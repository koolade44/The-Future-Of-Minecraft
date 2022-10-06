package com.koolade446.tfmc.items;

import com.koolade446.tfmc.Tfmc;
import com.koolade446.tfmc.blocks.DeepDarkPortalBlock;
import com.koolade446.tfmc.registry.RegistryHandler;
import com.koolade446.tfmc.world.CustomPortalShape;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WardenHeart extends Item {
    public WardenHeart() {
        super(new Properties()
                .tab(CreativeModeTab.TAB_MATERIALS)
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        BlockState clickedBlockState = useOnContext.getLevel().getBlockState(useOnContext.getClickedPos());
        Level level = useOnContext.getLevel();
        if (clickedBlockState.is(Blocks.REINFORCED_DEEPSLATE) && (useOnContext.getClickedFace().equals(Direction.UP) || useOnContext.getClickedFace().equals(Direction.DOWN))) {
            if (level.isClientSide) return InteractionResult.SUCCESS;
            else if (new CustomPortalShape(22, 8, useOnContext.getClickedPos(),  useOnContext.getLevel(), Blocks.REINFORCED_DEEPSLATE).tryFillPortal(RegistryHandler.DEEP_DARK_PORTAL.get().defaultBlockState())) {
                useOnContext.getItemInHand().shrink(1);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }
}
