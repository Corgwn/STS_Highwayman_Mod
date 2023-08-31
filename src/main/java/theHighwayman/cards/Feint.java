package theHighwayman.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHighwayman.characters.theHighwayman;
import theHighwayman.powers.Dodge;
import theHighwayman.powers.RipostePower;

import static theHighwayman.DefaultMod.makeCardPath;
import static theHighwayman.DefaultMod.makeID;

@AutoAdd.Ignore
public class Feint extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = makeID(Feint.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DODGE = 4;
    private static final int UPGRADE_PLUS_DODGE = 2;
    private static final int RIPOSTE = 1;


    // /STAT DECLARATION/


    public Feint() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = DODGE;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = RIPOSTE;

        this.tags.add(CardTags.EMPTY); //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Dodge(p, p, magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RipostePower(p, p, defaultSecondMagicNumber)));
        //if (p.hasPower(makeID("Riposte"))) {
        //    p.getPower(makeID("Riposte")).amount += 1;
        //}
        //else {
        //    p.addPower(new Riposte(p, p, 1));
        //}
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DODGE);
            initializeDescription();
        }
    }
}
