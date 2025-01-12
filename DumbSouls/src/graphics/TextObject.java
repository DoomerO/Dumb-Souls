package graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

import java.awt.Color;
import java.awt.Font;

public class TextObject {
    private Rectangle2D box;
    private int x, y;
    public String txt;
    private Font font;
    private Color c;
    private boolean hovered = false;
    
    public TextObject(String fontName, int fontStyle, int fontSize, String text, int textX, int textY, Color color){
        font = new Font(fontName, fontStyle, fontSize);
        txt = text;
        c = color;
        x = textX;
        y = textY;
        Game.gameGraphics.setFont(font);
        box = Game.gameGraphics.getFontMetrics().getStringBounds(txt, Game.gameGraphics);
        box.setRect(textX, textY - Game.gameGraphics.getFontMetrics().getAscent(), box.getWidth(), box.getHeight());
    }

    public void render(){
        Game.gameGraphics.setColor(c);
        Game.gameGraphics.setFont(font);
        Game.gameGraphics.drawString(txt, x, y);
    }

    public void updateText(String text){
        txt = text;
        box = Game.gameGraphics.getFontMetrics().getStringBounds(txt, Game.gameGraphics);
        box.setRect(x, y - Game.gameGraphics.getFontMetrics().getAscent(), box.getWidth(), box.getHeight());
    }

    public void updateColor(Color color){
        c = color;
    }

    public boolean hover(){
        int deltaX = Game.mx / Game.scale, deltaY = Game.my / Game.scale;
        boolean temp = box.contains(deltaX, deltaY) ^ hovered;
        hovered = box.contains(deltaX, deltaY);
        return temp;
    }

    public boolean click(){
        return Game.clickController.contains(1) && hovered;
    }

    public boolean isColliding(int targetX, int targetY){
        return box.contains(targetX, targetY);
    }

    public int getWidth(){
        return (int)box.getWidth();
    }

    public int getHeight(){
        return (int)box.getHeight();
    }
}
