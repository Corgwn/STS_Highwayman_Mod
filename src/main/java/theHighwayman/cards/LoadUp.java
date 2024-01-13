package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHighwayman.DefaultMod;
import theHighwayman.actions.BleedGrowthAction;
import theHighwayman.characters.theHighwayman;
import theHighwayman.powers.Ammo;
import theHighwayman.powers.Bleed;

import static theHighwayman.DefaultMod.makeCardPath;

public class LoadUp extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(LoadUp.class.getSimpleName());
    public static final String IMG = makeCardPath("LoadUp.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int AMMO = 2;
    private static final int UPGRADE_PLUS_AMMO = 1;

    // /STAT DECLARATION/


    public LoadUp() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = AMMO;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Ammo(p, p, magicNumber)));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_AMMO);
            initializeDescription();
        }
    }
}