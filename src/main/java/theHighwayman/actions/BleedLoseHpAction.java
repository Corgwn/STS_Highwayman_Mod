package theHighwayman.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static theHighwayman.DefaultMod.makeID;

public class BleedLoseHpAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;
    private final boolean consume;

    public BleedLoseHpAction(AbstractCreature target, AbstractCreature source, int amount, AbstractGameAction.AttackEffect effect, boolean consume) {
        this.setValues(target, source);
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.33F;
        this.consume = consume;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;
        }
        else {
            if (this.duration == 0.33F && this.target.currentHealth > 0) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            }

            this.tickDuration();
            if (this.isDone) {
                int damage = this.amount;
                this.target.getPower(makeID("Bleed")).flash();

                //Bleed reduction logic
                if (this.consume) {
                    if (AbstractDungeon.player.hasPower(makeID("TwistedTip"))) {
                        int reduceAmount = this.target.getPower(makeID("Bleed")).amount / 2;
                        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.target, this.source, makeID("Bleed"), reduceAmount));
                    }
                    else {
                        int reduceAmount = this.target.getPower(makeID("Bleed")).amount;
                        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.target, this.source, makeID("Bleed"), reduceAmount));
                    }
                }

                //Bleed damage logic
                if (this.target.currentHealth > 0 && !this.consume) {
                    this.target.tint.color = Color.CHARTREUSE.cpy();
                    this.target.tint.changeColor(Color.WHITE.cpy());
                    this.target.damage(new DamageInfo(this.source, damage, DamageInfo.DamageType.HP_LOSS));
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }

                this.addToTop(new WaitAction(0.1F));
            }
        }
    }
}
