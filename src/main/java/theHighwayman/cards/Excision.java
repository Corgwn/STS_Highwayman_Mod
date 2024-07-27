package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHighwayman.DefaultMod;
import theHighwayman.actions.BleedGrowthAction;
import theHighwayman.characters.theHighwayman;
import theHighwayman.powers.Bleed;
import theHighwayman.powers.FutureBleed;

import static theHighwayman.DefaultMod.makeCardPath;

public class Excision extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Excision.class.getSimpleName());
    public static final String IMG = makeCardPath("Excision.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int BLEED = 2;
    private static final int FUTURE_BLEED = 4;
    private static final int UPGRADE_PLUS_BLEED = 2;

    // /STAT DECLARATION/


    public Excision() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = BLEED;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = FUTURE_BLEED;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Bleed(m, p, magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new FutureBleed(m, p, defaultSecondMagicNumber)));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_BLEED);
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_BLEED);
            initializeDescription();
        }
    }
}