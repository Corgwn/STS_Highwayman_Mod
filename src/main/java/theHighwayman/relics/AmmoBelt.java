package theHighwayman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHighwayman.DefaultMod;
import theHighwayman.powers.Ammo;
import theHighwayman.util.TextureLoader;

import static theHighwayman.DefaultMod.*;

public class AmmoBelt extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("AmmoBelt");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CartridgeBox.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CartridgeBox.png"));

    public AmmoBelt() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
        AbstractDungeon.player.hand.addToHand(new theHighwayman.cards.Cartridge());
        AbstractDungeon.player.hand.addToHand(new theHighwayman.cards.Cartridge());
        AbstractDungeon.player.hand.addToHand(new theHighwayman.cards.Cartridge());
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
