package io.github.incohesions.sanguo_armory.items

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.item.Item
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World

class BlazeStaffItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): ActionResult {
        if (world.isClient) return ActionResult.PASS

        val frontOfPlayer = user.pos
            .add(user.rotationVector.multiply(1.5))
            .add(0.0, user.getEyeHeight(user.pose).toDouble(), 0.0)

        val fireball = FireballEntity(world, user, user.rotationVector.multiply(2.0), 3)
        fireball.setPosition(frontOfPlayer)
        world.spawnEntity(fireball)

        return ActionResult.SUCCESS
    }
}