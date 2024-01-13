package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHighwayman.DefaultMod;
import theHighwayman.actions.BleedLoseHpAction;
import theHighwayman.characters.theHighwayman;
import theHighwayman.powers.Bleed;

import static theHighwayman.DefaultMod.makeCardPath;
import static theHighwayman.DefaultMod.makeID;

public class GetReady extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * For Each Loop x2" "Apply 1 Vulnerable to all enemies, 2(3) times.
     */

    // TEXT DECLARATION


    public static final String ID = DefaultMod.makeID(GetReady.class.getSimpleName());
    public static final String IMG = makeCardPath("GetReady.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = -2;
    private static final int ENERGY = 2;
    private static final int UPGRADE_PLUS_ENERGY = 1;


    // /STAT DECLARATION/


    public GetReady() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = ENERGY;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(magicNumber));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        //this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_ENERGY);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}