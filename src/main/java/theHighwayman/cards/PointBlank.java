package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import theHighwayman.DefaultMod;
import theHighwayman.characters.theHighwayman;

import static theHighwayman.DefaultMod.makeCardPath;
import static theHighwayman.DefaultMod.makeID;

public class PointBlank extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * TOUCH Deal 30(35) damage.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(PointBlank.class.getSimpleName());
    public static final String IMG = makeCardPath("Point_Blank_Shot_250.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 2;

    private static final int DAMAGE = 24;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int VULNERABLE = 1;
    // /STAT DECLARATION/


    public PointBlank() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = VULNERABLE;

        this.tags.add(CustomTags.SHOT);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, makeID("Ammo"), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
        int actualDamage = (int) Math.floor(damage * 1.5);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, actualDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (canUse && p.hasPower(makeID("Ammo"))) {
            return p.getPower(makeID("Ammo")).amount > 0;
        }
        return false;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}