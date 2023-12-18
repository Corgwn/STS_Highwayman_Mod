package theHighwayman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import theHighwayman.DefaultMod;
import theHighwayman.characters.theHighwayman;

import static theHighwayman.DefaultMod.makeCardPath;
import static theHighwayman.DefaultMod.makeID;

public class Grapeshot extends AbstractShotCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * TOUCH Deal 30(35) damage.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Grapeshot.class.getSimpleName());
    public static final String IMG = makeCardPath("GrapeShot_250.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = theHighwayman.Enums.COLOR_GRAY;

    private static final int COST = 2;

    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 4;
    // /STAT DECLARATION/


    public Grapeshot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;

        this.tags.add(CustomTags.SHOT);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(makeID("Ammo"))) {
            this.numberShots = p.getPower(makeID("Ammo")).amount;
        }
        if (!purgeOnUse) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, makeID("Ammo"), this.numberShots));
        }
        for (int i = 0; i < this.numberShots; i++) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, baseDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }
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