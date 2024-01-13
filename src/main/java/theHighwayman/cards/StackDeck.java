package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHighwayman.DefaultMod;
import theHighwayman.actions.RetreatFromDiscardAction;
import theHighwayman.actions.RetreatFromHandAction;
import theHighwayman.characters.theHighwayman;

import static theHighwayman.DefaultMod.makeCardPath;

public class StackDeck extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(StackDeck.class.getSimpleName());
    public static final String IMG = makeCardPath("StackTheDeck_250.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int RETREAT_AMOUNT = 3;


    // /STAT DECLARATION/


    public StackDeck() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = RETREAT_AMOUNT;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        System.out.println("stack deck used");
        AbstractDungeon.actionManager.addToBottom(new RetreatFromDiscardAction(p, p, magicNumber));
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
