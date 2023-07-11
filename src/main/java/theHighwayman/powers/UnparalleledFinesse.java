package theHighwayman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHighwayman.actions.ChooseForBottomAction;
import theHighwayman.util.TextureLoader;

import java.util.Arrays;

import static theHighwayman.DefaultMod.makeID;
import static theHighwayman.DefaultMod.makePowerPath;

//At the start of your turn, if you have gained no additional bleed since last turn, lose #b health

public class UnparalleledFinesse extends AbstractPower implements CloneablePowerInterface {

    public AbstractCreature source;

    public static final String POWER_ID = makeID("UnparalleledFinesse");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    //TODO: add non-default images for ammo
    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public UnparalleledFinesse(final AbstractCreature owner, final AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        this.updateDescription();

        type = PowerType.BUFF;
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
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[3] + DESCRIPTIONS[4] + DESCRIPTIONS[6];
        } else {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + DESCRIPTIONS[5] + DESCRIPTIONS[6];
        }
    }

    public void atStartOfTurnPostDraw() {
        this.addToBot(new DrawCardAction(this.amount));
        this.addToBot(new ChooseForBottomAction(owner, owner, amount));
    }
    @Override
    public AbstractPower makeCopy() {
        return new UnparalleledFinesse(owner, source, amount);
    }
}