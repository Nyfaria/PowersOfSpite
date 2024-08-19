//package com.nyfaria.powersofspite.utils;
//
//import com.google.common.cache.Cache;
//import com.google.common.cache.CacheBuilder;
//import net.minecraft.core.BlockPos;
//import net.minecraft.util.Mth;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.targeting.TargetingConditions;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.ClipContext;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.gameevent.GameEvent;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.BlockHitResult;
//import net.minecraft.world.phys.Vec3;
//import net.pierre.legacies.api.abstractclass.AbstractArmorItem;
//import net.pierre.legacies.api.armorhandler.ArmorEnum;
//import net.pierre.legacies.core.registry.KeyBindingEnum;
//import net.pierre.legacies.server.capability.player.PlayerData;
//import net.pierre.legacies.server.capability.player.PlayerDataProvider;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//import static net.pierre.legacies.common.network.handler.CommonHandler.hashKeyMapping;
//import static net.pierre.legacies.core.registry.KeyBindingEnum.NULL;
//
//public class MCUtil {
//    public static Cache<UUID, ArmorEnum> armorEnumCache = CacheBuilder.newBuilder().expireAfterWrite(20, TimeUnit.SECONDS).build();
//
//    public static boolean isDay(Level level) {
//        return level.getDayTime() % 24000 < 13000;
//    }
//
//    public static int distanceTo(BlockPos blockPos0, BlockPos blockPos1) {
//        float xDistance = (float) (blockPos0.getX() - blockPos1.getX());
//        float yDistance = (float) (blockPos0.getY() - blockPos1.getY());
//        float zDistance = (float) (blockPos0.getZ() - blockPos1.getZ());
//
//        return (int) Mth.sqrt(xDistance * xDistance + yDistance * yDistance + zDistance * zDistance);
//    }
//
//    public static Vec3 vec3DistanceTo(Vec3 pos0, Vec3 pos1) {
//        float xDistance = (float) (pos0.x() - pos1.x());
//        float yDistance = (float) (pos0.y() - pos1.y());
//        float zDistance = (float) (pos0.z() - pos1.z());
//
//        return new Vec3(xDistance, yDistance, zDistance);
//    }
//
//    public static int distanceTo(double x, double y, double z, BlockPos blockPos) {
//        float xDistance = (float) (x - blockPos.getX());
//        float yDistance = (float) (y - blockPos.getY());
//        float zDistance = (float) (z - blockPos.getZ());
//
//        return (int) Mth.sqrt(xDistance * xDistance + yDistance * yDistance + zDistance * zDistance);
//    }
//
//    public static boolean isFastFlying(Player player) {
//        PlayerData playerData = PlayerDataProvider.getPlayer(player);
//
//        return player.getAbilities().flying && player.isSprinting() && (int) playerData.getSolarEnergy() > 20;
//    }
//
//    public static boolean isFastSwimming(Player player) {
//        return player.isSwimming() && player.isSprinting();
//    }
//
//    public static ArmorEnum getEquippedArmor(Player player, boolean forceUpdate) {
//        ArmorEnum cachedArmorEnum = armorEnumCache.getIfPresent(player.getUUID());
//
//        if (cachedArmorEnum == null || forceUpdate) {
//            ItemStack checkItem = player.getInventory().armor.stream().filter(stack -> stack.getItem() instanceof AbstractArmorItem).findFirst().orElse(ItemStack.EMPTY);
//            if (!checkItem.isEmpty()) {
//                if (((AbstractArmorItem) checkItem.getItem()).getArmorEnum().getHandler().isWearingFullSuit(player)) {
//                    cachedArmorEnum = ((AbstractArmorItem) checkItem.getItem()).getArmorEnum();
//                    armorEnumCache.put(player.getUUID(), cachedArmorEnum);
//                } else {
//                    cachedArmorEnum = null;
//                    armorEnumCache.invalidate(player.getUUID());
//                    armorEnumCache.cleanUp();
//                }
//            } else {
//                cachedArmorEnum = null;
//                armorEnumCache.invalidate(player.getUUID());
//                armorEnumCache.cleanUp();
//            }
//        }
//
//        return cachedArmorEnum;
//    }
//
//    public static BlockHitResult getHitResult(Level level, Player player, double distance, ClipContext.Block clipContextBlock) {
//        return getBlockHitResult(level, player, distance, clipContextBlock);
//    }
//
//    public static BlockHitResult getHitResult(Level level, Player player, double distance) {
//        return getBlockHitResult(level, player, distance, ClipContext.Block.COLLIDER);
//    }
//
//    private static BlockHitResult getBlockHitResult(Level level, Player player, double distance, ClipContext.Block clipContextBlock) {
//        float f = player.getXRot();
//        float f1 = player.getYRot();
//        Vec3 vec3 = player.getEyePosition();
//        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
//        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
//        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
//        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
//        float f6 = f3 * f4;
//        float f7 = f2 * f4;
//        Vec3 vec31 = vec3.add((double) f6 * distance, (double) f5 * distance, (double) f7 * distance);
//        return level.clip(new ClipContext(vec3, vec31, clipContextBlock, ClipContext.Fluid.NONE, player));
//    }
//
//    public static Entity getEntityResult(Entity rayTraceEntity, double distance) {
//        float playerPitch = rayTraceEntity.getXRot();
//        float playerYaw = rayTraceEntity.getYRot();
//        Vec3 startPos = rayTraceEntity.getEyePosition();
//        float f2 = Mth.cos(-playerYaw * ((float) Math.PI / 180F) - (float) Math.PI);
//        float f3 = Mth.sin(-playerYaw * ((float) Math.PI / 180F) - (float) Math.PI);
//        float f4 = -Mth.cos(-playerPitch * ((float) Math.PI / 180F));
//        float additionY = Mth.sin(-playerPitch * ((float) Math.PI / 180F));
//        float additionX = f3 * f4;
//        float additionZ = f2 * f4;
//        double d0 = distance;
//        Vec3 endVec = startPos.add(
//                ((double) additionX * d0),
//                ((double) additionY * d0),
//                ((double) additionZ * d0));
//
//
//        AABB startEndBox = new AABB(startPos, endVec);
//        Entity entity = null;
//        for (Entity entity1 : rayTraceEntity.level().getEntities(rayTraceEntity, startEndBox, (val) -> true)) {
//            AABB aabb = entity1.getBoundingBox().inflate(entity1.getPickRadius());
//            Optional<Vec3> optional = aabb.clip(startPos, endVec);
//            if (aabb.contains(startPos)) {
//                if (d0 >= 0.0D) {
//                    entity = entity1;
//                    startPos = optional.orElse(startPos);
//                    d0 = 0.0D;
//                }
//            } else if (optional.isPresent()) {
//                Vec3 vec31 = optional.get();
//                double d1 = startPos.distanceTo(vec31);
//                if (d1 < d0 || d0 == 0.0D) {
//                    if (entity1.getRootVehicle() == rayTraceEntity.getRootVehicle() && !entity1.canRiderInteract()) {
//                        if (d0 == 0.0D) {
//                            entity = entity1;
//                            startPos = vec31;
//                        }
//                    } else {
//                        entity = entity1;
//                        startPos = vec31;
//                        d0 = d1;
//                    }
//                }
//            }
//        }
//
//        return entity;
//    }
//
//    public static KeyBindingEnum checkKeyBindingOfKeyValue(UUID uuid, int keyValue) {
//        Map<String, Integer> hash = hashKeyMapping.row(uuid);
//
//        for (String key : hash.keySet()) {
//            if (hash.get(key) == keyValue) {
//                return KeyBindingEnum.valueOf(key.substring(13).toUpperCase());
//            }
//        }
//
//        return NULL;
//    }
//
//    public static Player getPlayerByUUID(UUID uuid, Level level) {
//        for (Player player : level.players()) {
//            if (player.getUUID().equals(uuid)) return player;
//        }
//
//        return null;
//    }
//
//    public static void changeBlock(Player player, Level level, BlockPos blockPos, BlockState block, GameEvent blockEvent) {
//        level.setBlock(blockPos, block, 11);
//        level.gameEvent(player, blockEvent, blockPos);
//    }
//
//    public static boolean isSurvival(Player player) {
//        return !player.isCreative() && !player.isSpectator();
//    }
//
//    public static List<LivingEntity> getEntitiesAround(Player player, int radius) {
//        return player.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat(), player, player.getBoundingBox().inflate(radius));
//    }
//}
