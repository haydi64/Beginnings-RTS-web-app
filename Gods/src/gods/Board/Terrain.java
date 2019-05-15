package gods.Board;

import java.awt.*;

public enum Terrain {
    Forrest(new Color(34,177,76)),
    Water(new Color(63,72,204)),
    Hill(new Color(255,127,39)),
    Mountain(new Color(127,127,127)),
    Plain(new Color(181,230,29)),
    Bridge(new Color(0,0,0)),
    Road(new Color(146,90,61));

    private final Color color;
    Terrain(Color color){this.color = color;}

    public Color getColor() {
        return color;
    }
}
