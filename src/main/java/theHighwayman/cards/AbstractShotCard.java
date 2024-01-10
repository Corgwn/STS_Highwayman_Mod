package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theHighwayman.DefaultMod.makeID;

public abstract class AbstractShotCard extends AbstractDefaultCard {
    public int numberShots;

    // "How come DefaultCommonAttack extends CustomCard and not DynamicCard like all the rest?"

    // Well every card, at the end of the day, extends CustomCard.
    // Abstract Default Card extends CustomCard and builds up on it, adding a second magic number. Your card can extend it and
    // bam - you can have a second magic number in that card (Learn Java inheritance if you want to know how that works).
    // Abstract Dynamic Card builds up on Abstract Default Card even more and makes it so that you don't need to add
    // the NAME and the DESCRIPTION into your card - it'll get it automatically. Of course, this functionality could have easily
    // Been added to the default card rather than creating a new Dynamic one, but was done so deliberately.

    public AbstractShotCard(final String id,
                            final String img,
                            final int cost,
                            final CardType type,
                            final CardColor color,
                            final CardRarity rarity,
                            final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        this.numberShots = 0;
    }

    public void consumeAmmo(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(makeID("Ammo"))) {
            this.numberShots = p.getPower(makeID("Ammo")).amount;
        }
        for (int i = 0; i < numberShots - 1; i++) {
            AbstractCard tmp = this.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = this.current_x;
            tmp.current_y = this.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, this.energyOnUse, true, true), true);
        }
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, makeID("Ammo"), this.numberShots));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!purgeOnUse) {
            boolean ammo = p.hasPower(makeID("Ammo"));
            if (!ammo) {
                cantUseMessage = "I don't have any ammo to fire with.";
            }
            boolean canUse = super.canUse(p, m);
            return canUse && ammo;
        }
        return super.canUse(p, m);
    }
}