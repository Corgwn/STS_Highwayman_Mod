package theHighwayman.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theHighwayman.DefaultMod;
import theHighwayman.characters.theHighwayman;

import java.util.Iterator;

import static theHighwayman.DefaultMod.makeCardPath;

@AutoAdd.Ignore
public class DoubleCross extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * For Each Loop x2" "Apply 1 Vulnerable to all enemies, 2(3) times.
     */

    // TEXT DECLARATION


    public static final String ID = DefaultMod.makeID(DoubleCross.class.getSimpleName());
    public static final String IMG = makeCardPath("DoubleCross_250.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int UPGRADED_PLUS_BLOCK = 2;
    private static final int VULNERABLE = 1;
    private static final int UPGRADED_PLUS_VULNERABLE = 1;

    // /STAT DECLARATION/


    public DoubleCross() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = VULNERABLE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null && m.getIntentBaseDmg() >= 0) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, magicNumber, false)));
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.getIntentBaseDmg() >= 0) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADED_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADED_PLUS_VULNERABLE);
            initializeDescription();
        }
    }
}