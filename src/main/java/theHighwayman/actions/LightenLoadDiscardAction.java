package theHighwayman.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static theHighwayman.DefaultMod.makeID;

public class LightenLoadDiscardAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;

    public LightenLoadDiscardAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.2F;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;
        }
        else {
            this.tickDuration();
            if (this.isDone) {
                AbstractPlayer p = AbstractDungeon.player;
                if (!p.drawPile.isEmpty()) {
                    p.drawPile.moveToDiscardPile(p.drawPile.getBottomCard());
                }
                p.drawPile.addToBottom(new Dazed());
            }
        }
    }
}
