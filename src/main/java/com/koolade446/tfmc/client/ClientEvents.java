package com.koolade446.tfmc.client;

import com.koolade446.tfmc.Tfmc;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.renderable.ITextureRenderTypeLookup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = Tfmc.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void clientOnlySetup(FMLClientSetupEvent event) {
            Tfmc.playerValues = new CustomPlayerValues();
        }
    }

    @Mod.EventBusSubscriber(modid = Tfmc.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

    }
}
