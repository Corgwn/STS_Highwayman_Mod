package theHighwayman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static basemod.BaseMod.logger;
import static theHighwayman.DefaultMod.makeID;

public class BleedGrowthAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;

    public BleedGrowthAction(AbstractCreature target, AbstractCreature source) {
        this.setValues(target, source);
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.33F;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;
        }
        else {
            this.tickDuration();
            if (this.isDone) {
                target.getPower(makeID("Bleed")).amount = (int) Math.ceil(target.getPower(makeID("Bleed")).amount * 1.25);
                this.addToTop(new WaitAction(0.1F));
            }
        }
    }
}
