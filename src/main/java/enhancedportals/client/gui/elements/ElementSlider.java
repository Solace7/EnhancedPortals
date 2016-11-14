package enhancedportals.client.gui.elements;

import enhancedportals.client.gui.BaseGui;

import java.util.List;

public class ElementSlider extends BaseElement
{
    public ElementSlider(BaseGui gui, int x, int y, int w, int h)
    {
        super(gui, x, y, w, h);
    }

    public ElementSlider(BaseGui gui, int x, int y)
    {
        super(gui, x, y, 100, 20);
    }

    public ElementSlider(BaseGui gui, int x, int y, int w)
    {
        super(gui, x, y, w, 20);
    }

    @Override
    public void addTooltip(List<String> list)
    {

    }

    @Override
    protected void drawContent()
    {

    }

    @Override
    public void update()
    {

    }
}
