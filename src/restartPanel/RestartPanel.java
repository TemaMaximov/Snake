package restartPanel;

import GameImages.Icons;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static game.Game.MAP_SIZE;

public class RestartPanel extends JPanel {
    JButton restartButton = new JButton();
    Font pixelFont = null;
    String str = "Game Over";
    public boolean buttonPushed = false;
    public RestartPanel(){
        setBackground(Color.black);
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("Pixel.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Pixel.ttf")));
        } catch (IOException | FontFormatException ignored) {
        }
        restartButton.setBounds(60, 120, 120, 35);
        restartButton.setText("restart");
        restartButton.setForeground(Color.WHITE);
        restartButton.setIcon(Icons.restartIcon);
        restartButton.setBackground(Color.BLACK);
        restartButton.setFont(pixelFont.deriveFont(20f));
        restartButton.setHorizontalTextPosition(JButton.LEFT);
        restartButton.setFocusable(false);
        restartButton.setBorder(new LineBorder(Color.WHITE));
        restartButton.addActionListener(e -> {
            buttonPushed = true;
        });
        this.add(restartButton);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, MAP_SIZE, MAP_SIZE);
        g.setColor(Color.white);
        g.setFont(pixelFont.deriveFont(30f));
        g.drawString(str, MAP_SIZE / 4, 120);
    }
}
