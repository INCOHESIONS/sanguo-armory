package io.github.incohesions.sanguo_armory.block.kiln

import com.mojang.serialization.MapCodec
import io.github.incohesions.sanguo_armory.registry.special.KilnRegistry
import net.minecraft.block.AbstractFurnaceBlock
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class KilnBlock(settings: Settings) : AbstractFurnaceBlock(settings) {
    private val codec = createCodec(::KilnBlock)

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hit: BlockHitResult
    ): ActionResult {
        if (!world.isClient)
            player.openHandledScreen(
                state.createScreenHandlerFactory(world, pos) ?: return ActionResult.SUCCESS
            )

        return ActionResult.SUCCESS
    }

    override fun openScreen(world: World, pos: BlockPos, player: PlayerEntity) {
        player.openHandledScreen(world.getBlockEntity(pos) as? KilnBlockEntity ?: return)
    }

    /* from: net.minecraft.block.FurnaceBlock */
    override fun randomDisplayTick(state: BlockState, world: World, blockPos: BlockPos, random: Random) {
        if (!state.get(LIT)) return

        val pos = Vec3d(
            blockPos.x + 0.5,
            blockPos.y.toDouble(),
            blockPos.z + 0.5
        )

        if (random.nextDouble() < 0.1)
            world.playSoundClient(
                pos.x,
                pos.y,
                pos.z,
                SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE,
                SoundCategory.BLOCKS,
                1.0F,
                1.0F,
                false
            )

        val direction = state.get(FACING)

        val d1 = 0.52
        val d2 = random.nextDouble() * 0.6 - 0.3

        val particlePos = Vec3d(
            if (direction.axis === Direction.Axis.X) direction.offsetX * d1 else d2,
            random.nextDouble() * 0.6,
            if (direction.axis === Direction.Axis.Z) direction.offsetZ * d1 else d2
        ).add(pos)

        arrayOf(ParticleTypes.SMOKE, ParticleTypes.FLAME).forEach {
            world.addParticleClient(it, particlePos.x, particlePos.y, particlePos.z, 0.0, 0.035, 0.0)
        }

        val vel = Vec3d(1.0, 0.0, 1.0).multiply(random.nextBetween(-1, 1) / 100.0)

        world.addParticleClient(ParticleTypes.LARGE_SMOKE, pos.x, pos.y, pos.z, vel.x, 0.05, vel.z)
    }

    override fun <T : BlockEntity> getTicker(
        world: World, state: BlockState, type: BlockEntityType<T>
    ): BlockEntityTicker<T>? =
        validateTicker(world, type, KilnRegistry.blockEntity)

    override fun getCodec(): MapCodec<out AbstractFurnaceBlock> = codec

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = KilnBlockEntity(pos, state)
}