package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHighwayman.DefaultMod;
import theHighwayman.actions.AdvanceFromDeckAction;
import theHighwayman.actions.RetreatFromDiscardAction;
import theHighwayman.characters.theHighwayman;
import theHighwayman.powers.Ammo;

import static theHighwayman.DefaultMod.makeCardPath;

public class Pivot extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Pivot.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int ADVANCE = 2;
    private static final int UPGRADED_PLUS_ADVANCE = 2;
    private static final int RETREAT = 1;

    // /STAT DECLARATION/


    public Pivot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = ADVANCE;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = RETREAT;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new AdvanceFromDeckAction(magicNumber, false));
        AbstractDungeon.actionManager.addToTop(new RetreatFromDiscardAction(p, p, defaultSecondMagicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_PLUS_ADVANCE);
            initializeDescription();
        }
    }
}
