package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theHighwayman.DefaultMod;
import theHighwayman.characters.theHighwayman;
import theHighwayman.powers.Bleed;

import static theHighwayman.DefaultMod.makeCardPath;

public class BloodEyes extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BloodEyes.class.getSimpleName());
    public static final String IMG = makeCardPath("BloodintheEyes.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int BLEED = 2;
    private static final int UPGRADE_PLUS_BLEED = 1;
    private static final int WEAK = 1;
    private static final int UPGRADE_PLUS_WEAK = 1;


    // /STAT DECLARATION/


    public BloodEyes() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BLEED;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = WEAK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Bleed(m, p, magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, defaultSecondMagicNumber, false)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_BLEED);
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_WEAK);
            initializeDescription();
        }
    }
}
