package theHighwayman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;
import java.util.Objects;

import static theHighwayman.DefaultMod.makeID;

public class RetreatFromDiscardAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;
    private final AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public RetreatFromDiscardAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FASTER;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (this.duration == Settings.ACTION_DUR_FASTER) {
                if (this.p.discardPile.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                if (this.p.discardPile.size() <= this.amount) {
                    while (!AbstractDungeon.player.discardPile.isEmpty()) {
                        if (this.p.hasPower(makeID("Repositioning"))) {
                            this.p.getPower(makeID("Repositioning")).onSpecificTrigger();
                        }
                        AbstractCard c = this.p.discardPile.getTopCard();
                        if (Objects.equals(c.cardID, makeID("GetReady"))) { c.use(p, AbstractDungeon.getRandomMonster()); c.moveToDiscardPile(); }
                        this.p.discardPile.removeCard(c);
                        this.p.hand.moveToBottomOfDeck(c);
                    }
                }

                if (this.p.discardPile.group.size() > this.amount) {
                    AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, TEXT[0], false, false, false, false);
                    this.tickDuration();
                    return;
                }
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    if (this.p.hasPower(makeID("Repositioning"))) {
                        this.p.getPower(makeID("Repositioning")).onSpecificTrigger();
                    }
                    if (Objects.equals(c.cardID, makeID("GetReady"))) { c.use(p, AbstractDungeon.getRandomMonster()); c.moveToDiscardPile(); }
                    this.p.discardPile.removeCard(c);
                    this.p.hand.moveToBottomOfDeck(c);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ForethoughtAction");
        TEXT = uiStrings.TEXT;
    }
}
