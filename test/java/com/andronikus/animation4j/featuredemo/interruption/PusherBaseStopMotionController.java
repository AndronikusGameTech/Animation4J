package com.andronikus.animation4j.featuredemo.interruption;

import com.andronikus.animation4j.stopmotion.StopMotionController;
import com.andronikus.animation4j.stopmotion.StopMotionState;

public class PusherBaseStopMotionController extends StopMotionController<Object, RetractablePusher, PusherBaseSpriteSheet> {

    public PusherBaseStopMotionController() {
        super(new PusherBaseSpriteSheet());
    }

    @Override
    protected StopMotionState<Object, RetractablePusher, PusherBaseSpriteSheet> buildInitialStatesAndTransitions() {
        /*
         * The Stop Motion Controllers shall have states:
         *
         * Neutral -> Breaking
         *
         * Breaking -> Broken (interruptible = false)
         * Breaking -> Neutral (interruptible = true)
         *
         * Broken -> Neutral
         */
        final StopMotionState<Object, RetractablePusher, PusherBaseSpriteSheet> neutralState = new StopMotionState<>(this)
            .addFrame(1L, (spriteSheet, state) -> spriteSheet.getNeutralSprite())
            .addFrame(null, (spriteSheet, state) -> spriteSheet.getNeutralSprite());

        final StopMotionState<Object, RetractablePusher, PusherBaseSpriteSheet> breakingState = neutralState
            .createTransitionState((o, retractablePusher) -> retractablePusher.isBroken());

        breakingState.withInterruptibleFlag(false);

        final StopMotionState<Object, RetractablePusher, PusherBaseSpriteSheet> brokenState = breakingState
            .completeCycleState();
        breakingState.createTransition(
            (o, retractablePusher) -> !retractablePusher.isBroken(),
            true,
            neutralState
        );

        brokenState.createTransition((o, retractablePusher) -> !retractablePusher.isBroken(), neutralState);

        brokenState
            .addFrame(1L, (spriteSheet, state) -> spriteSheet.getBrokenSprite())
            .addFrame(null, (spriteSheet, state) -> spriteSheet.getBrokenSprite());

        breakingState
            .addFrame(7L, PusherBaseSpriteSheet::getBreakingSprite)
            .addFrame(6L, PusherBaseSpriteSheet::getBreakingSprite)
            .addFrame(6L, PusherBaseSpriteSheet::getBreakingSprite)
            .addFrame(4L, PusherBaseSpriteSheet::getBreakingSprite)
            .addFrame(4L, PusherBaseSpriteSheet::getBreakingSprite)
            .addFrame(3L, PusherBaseSpriteSheet::getBreakingSprite)
            .addFrame(3L, PusherBaseSpriteSheet::getBreakingSprite)
            .addFrame(2L, PusherBaseSpriteSheet::getBreakingSprite)
            .addFrame(1L, PusherBaseSpriteSheet::getBreakingSprite)
            .addFrame(null, PusherBaseSpriteSheet::getBreakingSprite);

        return neutralState;
    }

    @Override
    public boolean checkIfObjectIsRoot(RetractablePusher object) {
        return true;
    }
}
