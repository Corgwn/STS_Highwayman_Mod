package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHighwayman.DefaultMod;
import theHighwayman.actions.AdvanceFromDeckAction;
import theHighwayman.characters.theHighwayman;

import static theHighwayman.DefaultMod.makeCardPath;

public class Ambush extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * For Each Loop x2" "Apply 1 Vulnerable to all enemies, 2(3) times.
     */

    // TEXT DECLARATION


    public static final String ID = DefaultMod.makeID(Ambush.class.getSimpleName());
    public static final String IMG = makeCardPath("Ambush.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DAMAGE = 3;
    // /STAT DECLARATION/


    public Ambush() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, baseDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void onChoseThisOption() {
        AbstractDungeon.actionManager.addToBottom(new AdvanceFromDeckAction(1, false));
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