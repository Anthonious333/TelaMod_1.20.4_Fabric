package net.anthony.telamod.item;

import net.anthony.telamod.block.entity.TelaRailBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TelaWrenchItem extends Item {
    private BlockPos destaPos = null;
    public TelaWrenchItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockEntity e = context.getWorld().getBlockEntity(context.getBlockPos());
        if (e != null && e.getClass() == TelaRailBlockEntity.class) {
            if (destaPos != null && context.getWorld().getBlockState(destaPos) != null) {
                if (((TelaRailBlockEntity) e).getDestination() != destaPos) {
                    ((TelaRailBlockEntity) e).setDestination(destaPos);
                    context.getWorld().playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1f, 1f);
                }
            } else {
                context.getWorld().playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(), SoundCategory.BLOCKS, 1f, 1f);
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("telamod.telawrench.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }



    public void setDestaPos(BlockPos destaPos) {
        this.destaPos = destaPos;
    }

    public BlockPos getDestaPos() {
        return destaPos;
    }
}
