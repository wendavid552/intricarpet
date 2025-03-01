package intricarpet.mixins.interaction;

import carpet.CarpetSettings;
import intricarpet.helpers.Interactions;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin
{
  @Shadow public ServerPlayerEntity player;

  @Inject(method = "onPlayerInteractBlock", at = @At(
    value = "INVOKE",
    target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;interactBlock(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;",
    shift = At.Shift.BEFORE
  ))
  private void beforeBlockInteracted(PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket_1, CallbackInfo ci)
  {
    String playerName = this.player.getName().getString();
    if(Interactions.onlinePlayerMap.containsKey(playerName) &&
      Interactions.onlinePlayerMap.get(playerName).contains("updates"))
      CarpetSettings.impendingFillSkipUpdates.set(true);
  }

  @Inject(method = "onPlayerInteractBlock", at = @At(
    value = "INVOKE",
    target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;interactBlock(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;",
    shift = At.Shift.AFTER
  ))
  private void afterBlockInteracted(PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket_1, CallbackInfo ci)
  {
    String playerName = this.player.getName().getString();
    if(Interactions.onlinePlayerMap.containsKey(playerName) &&
      Interactions.onlinePlayerMap.get(playerName).contains("updates"))
      CarpetSettings.impendingFillSkipUpdates.set(false);
  }

  @Inject(method = "onPlayerInteractItem", at = @At(
    value = "INVOKE",
    target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;interactItem(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
    shift = At.Shift.BEFORE
  ))
  private void beforeItemInteracted(PlayerInteractItemC2SPacket packet, CallbackInfo ci)
  {
    String playerName = this.player.getName().getString();
    if(Interactions.onlinePlayerMap.containsKey(playerName) &&
      Interactions.onlinePlayerMap.get(playerName).contains("updates"))
      CarpetSettings.impendingFillSkipUpdates.set(true);
  }

  @Inject(method = "onPlayerInteractItem", at = @At(
    value = "INVOKE",
    target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;interactItem(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
    shift = At.Shift.AFTER
  ))
  private void afterItemInteracted(PlayerInteractItemC2SPacket packet, CallbackInfo ci)
  {
    String playerName = this.player.getName().getString();
    if(Interactions.onlinePlayerMap.containsKey(playerName) &&
      Interactions.onlinePlayerMap.get(playerName).contains("updates"))
      CarpetSettings.impendingFillSkipUpdates.set(false);
  }
  @Inject(method = "onPlayerAction", at = @At(
    value = "INVOKE",
    //#if MC >= 11900
    //$$target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;processBlockBreakingAction(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/network/packet/c2s/play/PlayerActionC2SPacket$Action;Lnet/minecraft/util/math/Direction;II)V",
    //#else
    target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;processBlockBreakingAction(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/network/packet/c2s/play/PlayerActionC2SPacket$Action;Lnet/minecraft/util/math/Direction;I)V",
    //#endif
    shift = At.Shift.BEFORE
  ))
  private void beforeBlockBroken(PlayerActionC2SPacket packet, CallbackInfo ci)
  {
    String playerName = this.player.getName().getString();
    if(Interactions.onlinePlayerMap.containsKey(playerName) &&
      Interactions.onlinePlayerMap.get(playerName).contains("updates"))
      CarpetSettings.impendingFillSkipUpdates.set(true);
  }

  @Inject(method = "onPlayerAction", at = @At(
    value = "INVOKE",
    //#if MC >= 11900
    //$$target ="Lnet/minecraft/server/network/ServerPlayerInteractionManager;processBlockBreakingAction(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/network/packet/c2s/play/PlayerActionC2SPacket$Action;Lnet/minecraft/util/math/Direction;II)V",
    //#else
    target ="Lnet/minecraft/server/network/ServerPlayerInteractionManager;processBlockBreakingAction(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/network/packet/c2s/play/PlayerActionC2SPacket$Action;Lnet/minecraft/util/math/Direction;I)V",
    //#endif
    shift = At.Shift.AFTER
  ))
  private void afterBlockBroken(PlayerActionC2SPacket packet, CallbackInfo ci)
  {
    String playerName = this.player.getName().getString();
    if(Interactions.onlinePlayerMap.containsKey(playerName) &&
      Interactions.onlinePlayerMap.get(playerName).contains("updates"))
      CarpetSettings.impendingFillSkipUpdates.set(false);
  }
}
