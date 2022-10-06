package com.koolade446.tfmc.world;

import com.koolade446.tfmc.Tfmc;
import com.koolade446.tfmc.blocks.DeepDarkPortalBlock;
import com.koolade446.tfmc.registry.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.function.Predicate;

public class CustomPortalShape{

    private int MAX_HEIGHT;
    private int MIN_HEIGHT;
    private final int MAX_WIDTH;
    private final Direction.Axis AXIS;
    private final Direction traceDirection;
    private final BlockPos startingPosition;
    private Block frameBlock;

    private LevelAccessor level;
    private final BlockPos bottomLeft;
    private final BlockPos topRight;
    private final int width;
    private final int height;
    private final boolean isValid;

    private final Predicate<BlockPos> frameChecker = (p -> {
        BlockState bs = level.getBlockState(p);
        return bs.is(frameBlock);
    });

    public CustomPortalShape(int MIN_WIDTH, int MAX_WIDTH, int MIN_HEIGHT, int MAX_HEIGHT, BlockPos startingPosition, LevelAccessor level, Block frameBlock) {
        this.MAX_WIDTH = MAX_WIDTH;
        this.MIN_HEIGHT = MIN_HEIGHT;
        this.MAX_HEIGHT = MAX_HEIGHT;
        this.startingPosition = startingPosition;
        this.level = level;
        this.frameBlock = frameBlock;
        this.traceDirection = computeDirection();
        this.AXIS = traceDirection.getAxis();
        this.bottomLeft = calculateBottomLeft();

        int h;
        this.height = (h = calculateHeight()) >= this.MIN_HEIGHT ? h : 0;

        int w;
        this.width = (w = calculateWidth()) >= MIN_WIDTH ? w : 0;

        this.topRight = calculateTopRight();
        this.isValid = performFullValidityCheck();

        Tfmc.LOGGER.info(String.format("""
                Starting position: %s
                Level: %s
                Frame block: %s
                Trace Direction: %s
                Axis: %s
                Bottom left: %s
                Height: %s
                Width: %s
                Top Right: %s
                Is valid portal: %s
                """,
                startingPosition.toString(),
                level.toString(),
                frameBlock.toString(),
                traceDirection.toString(),
                AXIS.toString(),
                bottomLeft.toString(),
                height,
                width,
                topRight.toString(),
                isValid));
    }

    public CustomPortalShape(int WIDTH, int HEIGHT, BlockPos startingPosition, LevelAccessor level, Block frameBlock) {
        this(WIDTH, WIDTH, HEIGHT, HEIGHT, startingPosition, level, frameBlock);
    }

    private Direction computeDirection() {
        if (frameChecker.test(startingPosition.east()) || frameChecker.test(startingPosition.west())) {
            return Direction.WEST;
        }
        else if (frameChecker.test(startingPosition.north()) || frameChecker.test(startingPosition.south())) {
            return Direction.SOUTH;
        }
        else return Direction.WEST;
    }

    private BlockPos calculateBottomLeft() {
        if (Direction.WEST.equals(traceDirection)) {
            for (int i = 0; i < MAX_WIDTH; ++i) {
                BlockPos pos = startingPosition.west(i);
                if (frameChecker.test(pos.above(1))) {
                    return pos;
                }
            }
        }
        else if (Direction.SOUTH.equals(traceDirection)) {
            for (int i = 0; i < MAX_WIDTH; ++i) {
                BlockPos pos = startingPosition.south(i);
                if (frameChecker.test(pos.above(1))) {
                    return pos;
                }
            }
        }
        return null;
    }

    private int calculateHeight() {
        for (int i = 0; i < MAX_HEIGHT + 1; ++i) {
            BlockPos pos = bottomLeft.above(i);
            if (!frameChecker.test(pos)) {
                return i;
            }
        }
        return 0;
    }

    private int calculateWidth() {
        for (int i = 0; i < MAX_WIDTH + 1; ++i) {
            BlockPos pos = bottomLeft.mutable().move(traceDirection.getOpposite(), i);
            if (!frameChecker.test(pos)) {
                return i;
            }
        }
        return 0;
    }

    private BlockPos calculateTopRight() {
        return bottomLeft.above(height - 1).mutable().move(traceDirection.getOpposite(), width - 1);
    }

    private boolean performFullValidityCheck() {
        Tfmc.LOGGER.info("Testing width");
        for (int i = 0; i < width - 1; ++i) {
            BlockPos bottomPos = bottomLeft.mutable().move(traceDirection.getOpposite(), i);
            BlockPos topPos = topRight.mutable().move(traceDirection, i);
            Tfmc.LOGGER.info(String.valueOf(i));
            Tfmc.LOGGER.info("testing " + bottomPos + " is " + frameChecker.test(bottomPos));
            Tfmc.LOGGER.info("testing " + topPos+ " is " + frameChecker.test(topPos));
            if (!frameChecker.test(bottomPos) || !frameChecker.test(topPos)) {return false;}
        }
        Tfmc.LOGGER.info("Testing height");
        for (int i = 0; i < height - 1; ++i) {
            BlockPos leftPos = bottomLeft.above(i);
            BlockPos rightPos = topRight.below(i);
            Tfmc.LOGGER.info(String.valueOf(i));
            Tfmc.LOGGER.info("testing " + leftPos + " is " + frameChecker.test(leftPos));
            Tfmc.LOGGER.info("testing " + rightPos + " is " + frameChecker.test(rightPos));
            if (!frameChecker.test(leftPos) || !frameChecker.test(rightPos)) {return false;}
        }
        return width != 0 && height != 0;
    }

    public boolean tryFillPortal(BlockState blockState) {
        if (!isValid) return false;
        else {
            Tfmc.LOGGER.info(blockState.getProperties().stream().toList().toString());
            final BlockState state = blockState.getProperties().contains(BlockStateProperties.HORIZONTAL_AXIS) ? blockState.setValue(BlockStateProperties.HORIZONTAL_AXIS, AXIS) : blockState;
            BlockPos portalBottomLeft = bottomLeft.above().mutable().move(traceDirection.getOpposite(), 1);
            for (int i = 0; i < width - 2; ++i) {
                for (int j = 0; j < height - 2; ++j) {
                    level.setBlock(portalBottomLeft.above(j).mutable().move(traceDirection.getOpposite(), i), state, 18);
                }
            }
            return true;
        }
    }
}
