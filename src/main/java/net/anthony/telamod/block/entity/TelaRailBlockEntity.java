package net.anthony.telamod.block.entity;

import net.anthony.telamod.block.DestaRailBlock;
import net.anthony.telamod.screen.TelaRailScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class TelaRailBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory{
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private static final int SLOT = 0;
    private BlockPos destination;
    protected final Random random = Random.create();

    public TelaRailBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TELA_RAIL, pos, state);
    }

    public boolean canActivate () {
        return !inventory.isEmpty();
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        if (destination != null) {
            nbt.putInt("destinationX", destination.getX());
            nbt.putInt("destinationY", destination.getY());
            nbt.putInt("destinationZ", destination.getZ());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        destination = new BlockPos(new Vec3i(nbt.getInt("destinationX"), nbt.getInt("destinationY"), nbt.getInt("destinationZ")));
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("TelaRail");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TelaRailScreenHandler(syncId, playerInventory, this);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (destination != null && world.getBlockState(destination).getBlock().getClass() != DestaRailBlock.class) {
            destination = null;
            return;
        }

        List<AbstractMinecartEntity> list = this.getCarts(world, pos, AbstractMinecartEntity.class, entity -> true);
        if (world.isReceivingRedstonePower(pos) && !list.isEmpty() && !inventory.get(SLOT).isEmpty() && destination != null) {
            for (Entity e : list) {
                this.removeStack(SLOT, 1);
                world.playSound(e, pos, SoundEvents.ENTITY_PLAYER_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
                e.requestTeleport(destination.getX(), destination.getY(), destination.getZ());
                world.playSound(e, e.getBlockPos(), SoundEvents.ENTITY_PLAYER_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
            }
        }
    }

    private <T extends AbstractMinecartEntity> List<T> getCarts(World world, BlockPos pos, Class<T> entityClass, Predicate<Entity> entityPredicate) {
        return world.getEntitiesByClass(entityClass, this.getCartDetectionBox(pos), entityPredicate);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        if (stack.isOf(Items.ENDER_PEARL)) {
            return true;
        }
        return false;
    }

    public void setDestination (BlockPos pos) {
        this.destination = pos;
    }

    public BlockPos getDestination() {
        return destination;
    }

    private Box getCartDetectionBox(BlockPos pos) {
        double d = 0.2;
        return new Box((double)pos.getX() + 0.2, pos.getY(), (double)pos.getZ() + 0.2, (double)(pos.getX() + 1) - 0.2, (double)(pos.getY() + 1) - 0.2, (double)(pos.getZ() + 1) - 0.2);
    }
}
