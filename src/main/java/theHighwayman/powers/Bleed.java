package theHighwayman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHighwayman.DefaultMod;
import theHighwayman.actions.BleedLoseHpAction;
import theHighwayman.actions.BleedGrowthAction;
import theHighwayman.util.TextureLoader;

import static theHighwayman.DefaultMod.makeID;
import static theHighwayman.DefaultMod.makePowerPath;

//At the start of your turn, if you have gained no additional bleed since last turn, lose #b health

public class Bleed extends AbstractPower implements CloneablePowerInterface {
    private boolean dealDamage;
    public AbstractCreature source;

    public static final String POWER_ID = makeID("Bleed");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    //TODO: add non-default images for bleed
    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("bleed84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("bleed32.png"));

    public Bleed(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        if (this.amount >= 9999) {
            this.amount = 9999;
        }
        this.dealDamage = false;
        this.source = source;

        this.description = DESCRIPTIONS[0] + Math.ceil(((double) this.amount) / 4) + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    // Note: If you want to apply an effect when a power is being applied you have 3 options:
    //onInitialApplication is "When THIS power is first applied for the very first time only."
    //onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
    //onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (int) Math.ceil(((double) this.amount) / 4) + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.dealDamage = false;
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            if (this.dealDamage) {
                this.addToBot(new BleedLoseHpAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.FIRE));


            }
            else {
                this.flashWithoutSound();
                this.addToBot(new BleedGrowthAction(this.owner, this.source));
                this.dealDamage = true;
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new Bleed(owner, source, amount);
    }
}
