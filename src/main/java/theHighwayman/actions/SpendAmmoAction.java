package theHighwayman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static theHighwayman.DefaultMod.makeID;

public class SpendAmmoAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;

    public SpendAmmoAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
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
                if (target.hasPower(makeID("Ammo"))) {
                    target.getPower(makeID("Ammo")).amount -= amount;
                }
            }
        }
    }
}
