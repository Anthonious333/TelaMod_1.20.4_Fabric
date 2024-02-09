package net.anthony.telamod.block.entity;

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
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class TelaRailBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory{
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private static final int SLOT = 0;
    private BlockPos destination;

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
        List<AbstractMinecartEntity> list = this.getCarts(world, pos, AbstractMinecartEntity.class, entity -> true);
        if (world.isReceivingRedstonePower(pos) && !list.isEmpty() && !inventory.isEmpty() && destination != null && world.getBlockState(destination) != null) {
            this.removeStack(SLOT, 1);
            for (Entity e : list) {
                e.requestTeleport(destination.getX(), destination.getY(), destination.getZ());
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
    private Box getCartDetectionBox(BlockPos pos) {
        double d = 0.2;
        return new Box((double)pos.getX() + 0.2, pos.getY(), (double)pos.getZ() + 0.2, (double)(pos.getX() + 1) - 0.2, (double)(pos.getY() + 1) - 0.2, (double)(pos.getZ() + 1) - 0.2);
    }
}
