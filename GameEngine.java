
public class GameEngine implements Runnable {

    private final CreateUI ui;
    private final int FPS = 60;
    private Thread gameThread;

    public GameEngine(CreateUI ui) {
        this.ui = ui;
    }
    public void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopThread() {
        gameThread = null;
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        GamePanel gp = new GamePanel();
        ui.setContentPane(gp);
        ui.pack();
        gp.requestFocusInWindow();
        ui.revalidate();
        double dt = 1.0 / FPS;
        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                gp.tick(dt);
                delta--;
            }
            if (delta < 1) { //när delta är mindre än 1 och inte uppdaterar tick() så används den inte
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }

            }

        }
    }
}
