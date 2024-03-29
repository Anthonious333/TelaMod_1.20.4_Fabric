package net.anthony.telamod.util.RailWithEntity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RailEntityPlacementHelper {
    private final World world;
    private final BlockPos pos;
    private final RailWithEntity block;
    private BlockState state;
    private final boolean forbidCurves;
    private final List<BlockPos> neighbors = Lists.newArrayList();

    public RailEntityPlacementHelper(World world, BlockPos pos, BlockState state) {
        this.world = world;
        this.pos = pos;
        this.state = state;
        this.block = (RailWithEntity)state.getBlock();
        RailShape railShape = state.get(this.block.getShapeProperty());
        this.forbidCurves = this.block.cannotMakeCurves();
        this.computeNeighbors(railShape);
    }

    public List<BlockPos> getNeighbors() {
        return this.neighbors;
    }

    private void computeNeighbors(RailShape shape) {
        this.neighbors.clear();
        switch (shape) {
            case NORTH_SOUTH: {
                this.neighbors.add(this.pos.north());
                this.neighbors.add(this.pos.south());
                break;
            }
            case EAST_WEST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.east());
                break;
            }
            case ASCENDING_EAST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.east().up());
                break;
            }
            case ASCENDING_WEST: {
                this.neighbors.add(this.pos.west().up());
                this.neighbors.add(this.pos.east());
                break;
            }
            case ASCENDING_NORTH: {
                this.neighbors.add(this.pos.north().up());
                this.neighbors.add(this.pos.south());
                break;
            }
            case ASCENDING_SOUTH: {
                this.neighbors.add(this.pos.north());
                this.neighbors.add(this.pos.south().up());
                break;
            }
            case SOUTH_EAST: {
                this.neighbors.add(this.pos.east());
                this.neighbors.add(this.pos.south());
                break;
            }
            case SOUTH_WEST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.south());
                break;
            }
            case NORTH_WEST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.north());
                break;
            }
            case NORTH_EAST: {
                this.neighbors.add(this.pos.east());
                this.neighbors.add(this.pos.north());
            }
        }
    }

    private void updateNeighborPositions() {
        for (int i = 0; i < this.neighbors.size(); ++i) {
            net.anthony.telamod.util.RailWithEntity.RailEntityPlacementHelper RailEntityPlacementHelper = this.getNeighboringRail(this.neighbors.get(i));
            if (RailEntityPlacementHelper == null || !RailEntityPlacementHelper.isNeighbor(this)) {
                this.neighbors.remove(i--);
                continue;
            }
            this.neighbors.set(i, RailEntityPlacementHelper.pos);
        }
    }

    private boolean isVerticallyNearRail(BlockPos pos) {
        return AbstractRailBlock.isRail(this.world, pos) || AbstractRailBlock.isRail(this.world, pos.up()) || AbstractRailBlock.isRail(this.world, pos.down());
    }

    @Nullable
    private net.anthony.telamod.util.RailWithEntity.RailEntityPlacementHelper getNeighboringRail(BlockPos pos) {
        BlockPos blockPos = pos;
        BlockState blockState = this.world.getBlockState(blockPos);
        if (AbstractRailBlock.isRail(blockState)) {
            return new net.anthony.telamod.util.RailWithEntity.RailEntityPlacementHelper(this.world, blockPos, blockState);
        }
        blockPos = pos.up();
        blockState = this.world.getBlockState(blockPos);
        if (AbstractRailBlock.isRail(blockState)) {
            return new net.anthony.telamod.util.RailWithEntity.RailEntityPlacementHelper(this.world, blockPos, blockState);
        }
        blockPos = pos.down();
        blockState = this.world.getBlockState(blockPos);
        if (AbstractRailBlock.isRail(blockState)) {
            return new net.anthony.telamod.util.RailWithEntity.RailEntityPlacementHelper(this.world, blockPos, blockState);
        }
        return null;
    }

    private boolean isNeighbor(net.anthony.telamod.util.RailWithEntity.RailEntityPlacementHelper other) {
        return this.isNeighbor(other.pos);
    }

    private boolean isNeighbor(BlockPos pos) {
        for (int i = 0; i < this.neighbors.size(); ++i) {
            BlockPos blockPos = this.neighbors.get(i);
            if (blockPos.getX() != pos.getX() || blockPos.getZ() != pos.getZ()) continue;
            return true;
        }
        return false;
    }

    protected int getNeighborCount() {
        int i = 0;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (!this.isVerticallyNearRail(this.pos.offset(direction))) continue;
            ++i;
        }
        return i;
    }

    private boolean canConnect(net.anthony.telamod.util.RailWithEntity.RailEntityPlacementHelper placementHelper) {
        return this.isNeighbor(placementHelper) || this.neighbors.size() != 2;
    }

    private void computeRailShape(net.anthony.telamod.util.RailWithEntity.RailEntityPlacementHelper placementHelper) {
        this.neighbors.add(placementHelper.pos);
        BlockPos blockPos = this.pos.north();
        BlockPos blockPos2 = this.pos.south();
        BlockPos blockPos3 = this.pos.west();
        BlockPos blockPos4 = this.pos.east();
        boolean bl = this.isNeighbor(blockPos);
        boolean bl2 = this.isNeighbor(blockPos2);
        boolean bl3 = this.isNeighbor(blockPos3);
        boolean bl4 = this.isNeighbor(blockPos4);
        RailShape railShape = null;
        if (bl || bl2) {
            railShape = RailShape.NORTH_SOUTH;
        }
        if (bl3 || bl4) {
            railShape = RailShape.EAST_WEST;
        }
        if (!this.forbidCurves) {
            if (bl2 && bl4 && !bl && !bl3) {
                railShape = RailShape.SOUTH_EAST;
            }
            if (bl2 && bl3 && !bl && !bl4) {
                railShape = RailShape.SOUTH_WEST;
            }
            if (bl && bl3 && !bl2 && !bl4) {
                railShape = RailShape.NORTH_WEST;
            }
            if (bl && bl4 && !bl2 && !bl3) {
                railShape = RailShape.NORTH_EAST;
            }
        }
        if (railShape == RailShape.NORTH_SOUTH) {
            if (AbstractRailBlock.isRail(this.world, blockPos.up())) {
                railShape = RailShape.ASCENDING_NORTH;
            }
            if (AbstractRailBlock.isRail(this.world, blockPos2.up())) {
                railShape = RailShape.ASCENDING_SOUTH;
            }
        }
        if (railShape == RailShape.EAST_WEST) {
            if (AbstractRailBlock.isRail(this.world, blockPos4.up())) {
                railShape = RailShape.ASCENDING_EAST;
            }
            if (AbstractRailBlock.isRail(this.world, blockPos3.up())) {
                railShape = RailShape.ASCENDING_WEST;
            }
        }
        if (railShape == null) {
            railShape = RailShape.NORTH_SOUTH;
        }
        this.state = (BlockState)this.state.with(this.block.getShapeProperty(), railShape);
        this.world.setBlockState(this.pos, this.state, Block.NOTIFY_ALL);
    }

    private boolean canConnect(BlockPos pos) {
        net.anthony.telamod.util.RailWithEntity.RailEntityPlacementHelper RailEntityPlacementHelper = this.getNeighboringRail(pos);
        if (RailEntityPlacementHelper == null) {
            return false;
        }
        RailEntityPlacementHelper.updateNeighborPositions();
        return RailEntityPlacementHelper.canConnect(this);
    }

    public net.anthony.telamod.util.RailWithEntity.RailEntityPlacementHelper updateBlockState(boolean powered, boolean forceUpdate, RailShape railShape) {
        boolean bl10;
        boolean bl6;
        BlockPos blockPos = this.pos.north();
        BlockPos blockPos2 = this.pos.south();
        BlockPos blockPos3 = this.pos.west();
        BlockPos blockPos4 = this.pos.east();
        boolean bl = this.canConnect(blockPos);
        boolean bl2 = this.canConnect(blockPos2);
        boolean bl3 = this.canConnect(blockPos3);
        boolean bl4 = this.canConnect(blockPos4);
        RailShape railShape2 = null;
        boolean bl5 = bl || bl2;
        boolean bl7 = bl6 = bl3 || bl4;
        if (bl5 && !bl6) {
            railShape2 = RailShape.NORTH_SOUTH;
        }
        if (bl6 && !bl5) {
            railShape2 = RailShape.EAST_WEST;
        }
        boolean bl72 = bl2 && bl4;
        boolean bl8 = bl2 && bl3;
        boolean bl9 = bl && bl4;
        boolean bl11 = bl10 = bl && bl3;
        if (!this.forbidCurves) {
            if (bl72 && !bl && !bl3) {
                railShape2 = RailShape.SOUTH_EAST;
            }
            if (bl8 && !bl && !bl4) {
                railShape2 = RailShape.SOUTH_WEST;
            }
            if (bl10 && !bl2 && !bl4) {
                railShape2 = RailShape.NORTH_WEST;
            }
            if (bl9 && !bl2 && !bl3) {
                railShape2 = RailShape.NORTH_EAST;
            }
        }
        if (railShape2 == null) {
            if (bl5 && bl6) {
                railShape2 = railShape;
            } else if (bl5) {
                railShape2 = RailShape.NORTH_SOUTH;
            } else if (bl6) {
                railShape2 = RailShape.EAST_WEST;
            }
            if (!this.forbidCurves) {
                if (powered) {
                    if (bl72) {
                        railShape2 = RailShape.SOUTH_EAST;
                    }
                    if (bl8) {
                        railShape2 = RailShape.SOUTH_WEST;
                    }
                    if (bl9) {
                        railShape2 = RailShape.NORTH_EAST;
                    }
                    if (bl10) {
                        railShape2 = RailShape.NORTH_WEST;
                    }
                } else {
                    if (bl10) {
                        railShape2 = RailShape.NORTH_WEST;
                    }
                    if (bl9) {
                        railShape2 = RailShape.NORTH_EAST;
                    }
                    if (bl8) {
                        railShape2 = RailShape.SOUTH_WEST;
                    }
                    if (bl72) {
                        railShape2 = RailShape.SOUTH_EAST;
                    }
                }
            }
        }
        if (railShape2 == RailShape.NORTH_SOUTH) {
            if (AbstractRailBlock.isRail(this.world, blockPos.up())) {
                railShape2 = RailShape.ASCENDING_NORTH;
            }
            if (AbstractRailBlock.isRail(this.world, blockPos2.up())) {
                railShape2 = RailShape.ASCENDING_SOUTH;
            }
        }
        if (railShape2 == RailShape.EAST_WEST) {
            if (AbstractRailBlock.isRail(this.world, blockPos4.up())) {
                railShape2 = RailShape.ASCENDING_EAST;
            }
            if (AbstractRailBlock.isRail(this.world, blockPos3.up())) {
                railShape2 = RailShape.ASCENDING_WEST;
            }
        }
        if (railShape2 == null) {
            railShape2 = railShape;
        }
        this.computeNeighbors(railShape2);
        this.state = (BlockState)this.state.with(this.block.getShapeProperty(), railShape2);
        if (forceUpdate || this.world.getBlockState(this.pos) != this.state) {
            this.world.setBlockState(this.pos, this.state, Block.NOTIFY_ALL);
            for (int i = 0; i < this.neighbors.size(); ++i) {
                net.anthony.telamod.util.RailWithEntity.RailEntityPlacementHelper RailEntityPlacementHelper = this.getNeighboringRail(this.neighbors.get(i));
                if (RailEntityPlacementHelper == null) continue;
                RailEntityPlacementHelper.updateNeighborPositions();
                if (!RailEntityPlacementHelper.canConnect(this)) continue;
                RailEntityPlacementHelper.computeRailShape(this);
            }
        }
        return this;
    }

    public BlockState getBlockState() {
        return this.state;
    }
}

