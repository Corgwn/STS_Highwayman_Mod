package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHighwayman.DefaultMod;
import theHighwayman.actions.BleedLoseHpAction;
import theHighwayman.characters.theHighwayman;
import theHighwayman.powers.Ammo;
import theHighwayman.powers.Bleed;
import theHighwayman.powers.DoubleShot;

import static theHighwayman.DefaultMod.makeCardPath;
import static theHighwayman.DefaultMod.makeID;

public class BleedOut extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * For Each Loop x2" "Apply 1 Vulnerable to all enemies, 2(3) times.
     */

    // TEXT DECLARATION


    public static final String ID = DefaultMod.makeID(BleedOut.class.getSimpleName());
    public static final String IMG = makeCardPath("BleedOut_250.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public BleedOut() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(makeID("Bleed"))) {
            int bleedAmount = m.getPower(makeID("Bleed")).amount;
            addToBot(new BleedLoseHpAction(m, p, bleedAmount, AbstractGameAction.AttackEffect.FIRE));
            if (p.hasPower(makeID("TriTip"))) {
                addToBot(new ApplyPowerAction(m, p, new Bleed(m, p, bleedAmount / 2)));
            }
            else {
                addToBot(new ApplyPowerAction(m, p, new Bleed(m, p, bleedAmount)));
            }

        }
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}