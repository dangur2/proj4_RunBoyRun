import java.awt.BorderLayout;
import javax.swing.JFrame;

public class CreateUI extends JFrame{
    public CreateUI(){
        setSize(800, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("run boy run");
        setVisible(true);
    }
}
