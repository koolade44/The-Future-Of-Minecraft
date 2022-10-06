package com.koolade446.tfmc.registry;

import com.koolade446.tfmc.Tfmc;
import com.koolade446.tfmc.blocks.DeepDarkPortalBlock;
import com.koolade446.tfmc.items.WardenHeart;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, Tfmc.MODID);
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, Tfmc.MODID);


    public static void init() {
        ITEM.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static final RegistryObject<Item> WARDENS_HEART = ITEM.register("warden_heart", WardenHeart::new);

    public static final RegistryObject<Block> DEEP_DARK_PORTAL = BLOCK.register("deep_dark_portal", DeepDarkPortalBlock::new);
}
