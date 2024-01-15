package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHighwayman.DefaultMod;
import theHighwayman.characters.theHighwayman;

import static theHighwayman.DefaultMod.makeCardPath;
import static theHighwayman.DefaultMod.makeID;

public class CrimsonCurse extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(CrimsonCurse.class.getSimpleName());
    public static final String IMG = makeCardPath("CrimsonCurse.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DAMAGE = 4;
    private static final int HEAL = 1;
    private static final int BLEED_PER_HEAL = 2;

    // /STAT DECLARATION/


    public CrimsonCurse() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = HEAL;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = BLEED_PER_HEAL;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
        if (m.hasPower(makeID("Bleed"))) {
            int bleedAmount = m.getPower(makeID("Bleed")).amount;
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(m, p, makeID("Bleed"), bleedAmount));
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, Math.floorDiv(bleedAmount, defaultSecondMagicNumber)));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}
