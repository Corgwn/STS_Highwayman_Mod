package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHighwayman.DefaultMod;
import theHighwayman.actions.RetreatFromDiscardAction;
import theHighwayman.characters.theHighwayman;

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
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage)));
        if (AbstractDungeon.player.gold >= 12) {
            AbstractDungeon.player.loseGold(12);
            this.upgradeDamage(magicNumber);
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
