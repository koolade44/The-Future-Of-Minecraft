package com.koolade446.tfmc.server;

import com.koolade446.tfmc.Tfmc;
import com.koolade446.tfmc.client.CustomPlayerValues;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ServerEvents {

    @Mod.EventBusSubscriber(modid = Tfmc.MODID, value = Dist.DEDICATED_SERVER, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {

    }

    @Mod.EventBusSubscriber(modid = Tfmc.MODID, value = Dist.DEDICATED_SERVER, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

    }
}
