package theHighwayman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHighwayman.util.TextureLoader;

import static theHighwayman.DefaultMod.makeID;
import static theHighwayman.DefaultMod.makePowerPath;

//At the start of your turn, if you have gained no additional bleed since last turn, lose #b health

public class Vigorous extends AbstractPower implements CloneablePowerInterface {

    public AbstractCreature source;

    public static final String POWER_ID = makeID("Vigorous");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("vigorous84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("vigorous32.png"));

    public Vigorous(final AbstractCreature owner, final AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        this.description = DESCRIPTIONS[0];

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


    @Override
    public void atStartOfTurn() {
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new Ammo(this.owner, this.owner, this.amount)));
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }

    @Override
    public AbstractPower makeCopy() {
        return new Vigorous(owner, source, this.amount);
    }
}
