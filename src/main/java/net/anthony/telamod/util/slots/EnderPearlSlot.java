package net.anthony.telamod.util.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

    public class EnderPearlSlot
            extends Slot {
        public EnderPearlSlot(Inventory inventory, int i, int j, int k) {
            super(inventory, i, j, k);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return matches(stack);
        }

        public boolean matches(ItemStack stack) {
            return stack.isOf(Items.ENDER_PEARL);
        }

        @Override
        public int getMaxItemCount() {
            return 16;
        }
    }
