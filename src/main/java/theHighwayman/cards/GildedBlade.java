package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHighwayman.DefaultMod;
import theHighwayman.characters.theHighwayman;

import java.util.Iterator;

import static theHighwayman.DefaultMod.makeCardPath;

public class GildedBlade extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(GildedBlade.class.getSimpleName());
    public static final String IMG = makeCardPath("GildedBlade.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int DAMAGE_PLUS = 3;
    private static final int UPGRADE_PLUS_DAMAGE_PLUS = 1;


    // /STAT DECLARATION/


    public GildedBlade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = DAMAGE_PLUS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (AbstractDungeon.player.gold >= 12) {
            Iterator<AbstractCard> var1 = AbstractDungeon.player.masterDeck.group.iterator();

            AbstractCard c;
            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (c.uuid.equals(this.uuid)) {
                    c.baseDamage += magicNumber;
                    c.isDamageModified = false;
                }
            }

            this.baseDamage += magicNumber;
            AbstractDungeon.player.loseGold(12);
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DAMAGE_PLUS);
            initializeDescription();
        }
    }
}
