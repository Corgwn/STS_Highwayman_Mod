package theHighwayman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHighwayman.powers.Ammo;
import theHighwayman.powers.Bleed;

import static theHighwayman.DefaultMod.makeID;

public class GainAmmoAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;

    public GainAmmoAction(AbstractCreature target, AbstractCreature source, int amount, AttackEffect effect) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.SPECIAL;
        this.attackEffect = effect;
        this.duration = 0.33F;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;
        }
        else {
            this.tickDuration();
            if (this.isDone) {
                String pid = makeID("Ammo");
                if (!target.hasPower(pid)) {
                    target.addPower(new Ammo(target, source, amount));
                }
                else {
                    target.getPower(pid).amount += amount;
                }
                if (target.getPower(pid).amount > 1 && !target.hasPower("Vigorous")) {
                    target.getPower(pid).amount = 1;
                }
            }
        }
    }
}
