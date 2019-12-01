package net.runelite.client.plugins.bloodrunes;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.client.graphics.ModelOutlineRenderer;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;

public class RunestoneOverlay extends Overlay
{

    private final Client client;
    private final BloodRunesPlugin plugin;
    private final ModelOutlineRenderer outlineRenderer;

    @Inject
    private RunestoneOverlay(final Client client, final BloodRunesPlugin plugin, final ModelOutlineRenderer outlineRenderer)
    {
        super(plugin);
        setPosition(OverlayPosition.TOP_RIGHT);
        this.outlineRenderer = outlineRenderer;
        this.client = client;
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {

        GameObject north = plugin.getRunestoneNorth();
        GameObject south = plugin.getRunestoneSouth();

        if (north == null || south == null)
        {
            return null;
        }

        if (plugin.isChipping())
        {
            renderRunestoneOverlay(graphics, north, Color.YELLOW);
            renderRunestoneOverlay(graphics, south, Color.YELLOW);
            return null;
        }

        if (plugin.isMineable(Runestones.NORTH))
        {
            renderRunestoneOverlay(graphics, north, Color.GREEN);
        } else
        {
            renderRunestoneOverlay(graphics, north, Color.RED);
        }

        if (plugin.isMineable(Runestones.SOUTH))
        {
            renderRunestoneOverlay(graphics, south, Color.GREEN);
        } else
        {
            renderRunestoneOverlay(graphics, south, Color.RED);
        }


        return null;
    }

    private void renderRunestoneOverlay(Graphics2D graphics2D, GameObject object, Color color)
    {
        Shape runeStone = object.getConvexHull();

        if (runeStone != null)
        {
            OverlayUtil.renderPolygon(graphics2D, runeStone, color);
        }
    }
}
