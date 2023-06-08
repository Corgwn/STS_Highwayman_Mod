package theHighwayman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHighwayman.powers.Ammo;
import theHighwayman.powers.Riposte;

import static theHighwayman.DefaultMod.makeID;

public class GainRiposteAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;
    public GainRiposteAction(AbstractCreature target, AbstractCreature source, int amount) {
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
                if (!target.hasPower(makeID("Riposte"))) {
                    target.addPower(new Riposte(target, source, amount));
                }
                else {
                    target.getPower(makeID("Riposte")).amount += amount;
                }
            }
        }
    }
}
