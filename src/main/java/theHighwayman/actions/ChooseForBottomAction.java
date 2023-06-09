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

public class ChooseForBottomAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;
    private final AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public ChooseForBottomAction(AbstractCreature target, AbstractCreature source,  int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() <= amount) {
                int num = this.p.hand.size();
                for (int i = 0; i < num; i++) {
                    AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToBottomOfDeck(c);
                    AbstractDungeon.player.hand.refreshHandLayout();
                }
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                for(Iterator<AbstractCard> var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); this.p.hand.moveToBottomOfDeck(c)) {
                    c = (AbstractCard)var1.next();
                }
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ForethoughtAction");
        TEXT = uiStrings.TEXT;
    }
}
