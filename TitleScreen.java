
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleScreen extends JPanel{
    private BufferedImage title, backGround;
    private final GameEngine ge;
    public TitleScreen(GameEngine ge){
        this.ge = ge;
        loadTitle();
        loadBackGround();
        setPreferredSize(new Dimension(800,480));
        setLayout(new GridBagLayout());

        JPanel centerCol = new JPanel();
        centerCol.setLayout(new BoxLayout(centerCol, BoxLayout.Y_AXIS));
        centerCol.setOpaque(false);

        addTitle(centerCol);
        centerCol.add(Box.createVerticalStrut(20));
        createButton(centerCol);
        add(centerCol);
    }
    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, null);
    }
    private void loadBackGround() {
        try {
            backGround = ImageIO.read(getClass().getResourceAsStream("/Background/Background_1.png"));
            throw new UnsupportedOperationException("Unimplemented method 'loadBackGround'");
        } catch (Exception e) {
        }
    }

    private void loadTitle() {
        try {
            title = ImageIO.read(getClass().getResourceAsStream("/Font/Title.png"));
            if (title == null) {
                System.out.println("Ingen bild hittades");
            }
        } catch (Exception e) {
        }
    }

    private void addTitle(JPanel column) {
        JLabel titleLabel = new JLabel(new ImageIcon(title));
        titleLabel.setPreferredSize(new Dimension(300,200));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        column.add(titleLabel);
    }

    private void createButton(JPanel centerCol){
        for (MapType x : MapType.values()) {
            JButton button = new JButton();
            buttonStyling(button, x.getName());
            button.addActionListener((ActionEvent e) -> {
                ge.setMapType(x);
                ge.setCurrentState(GameState.PLAYING);
                ge.startThread();
            });
            centerCol.add(button);
            centerCol.add(Box.createVerticalStrut(10));
        }
    }

    private void buttonStyling(JButton button, String x){
            button.setText("Play "+x+" map");
            button.setFocusable(false);
            button.setPreferredSize(new Dimension(250,125));
            button.setMaximumSize(new Dimension(250,50));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFont(new Font("Times Roman", Font.PLAIN,20));
            button.setForeground(Color.black);
            button.setBackground(Color.white);
    }

}
