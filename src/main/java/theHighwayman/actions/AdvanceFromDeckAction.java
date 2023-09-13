package theHighwayman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Objects;

import static theHighwayman.DefaultMod.makeID;

public class AdvanceFromDeckAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean zeroCost;

    public AdvanceFromDeckAction(int amount, boolean zeroCost) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.zeroCost = zeroCost;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;
        }
        else {
            this.tickDuration();
            if (this.isDone) {
                for (int i = 0; i < this.amount; i++) {
                    //if (p.drawPile.isEmpty()) { p.drawPile.shuffle(); }
                    if (!p.drawPile.isEmpty()) {
                        AbstractCard card = p.drawPile.getBottomCard();
                        if (zeroCost) { card.setCostForTurn(0); }
                        if (Objects.equals(card.cardID, makeID("PointBlank"))) { this.addToBot(new ModifyDamageAction(card.uuid, card.damage)); }
                        if (Objects.equals(card.cardID, makeID("Ambush"))) { card.use(p, AbstractDungeon.getRandomMonster()); card.moveToDiscardPile(); }
                        p.drawPile.removeCard(card);
                        AbstractDungeon.player.hand.addToTop(card);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                    }
                }
            }
        }
    }
}
