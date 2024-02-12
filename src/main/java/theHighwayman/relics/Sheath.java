package theHighwayman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHighwayman.DefaultMod;
import theHighwayman.powers.Ammo;
import theHighwayman.util.TextureLoader;

import static theHighwayman.DefaultMod.*;

public class Sheath extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("Sheath");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SharpeningSheath.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SharpeningSheath.png"));
    private static final int EFFECT = 1;

    public Sheath() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
