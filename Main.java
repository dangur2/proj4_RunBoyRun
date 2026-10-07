public class Main{
public static void main(String[] args) {
    CreateUI ui = new CreateUI();
    GameEngine ge = new GameEngine(ui);
    ge.startThread();
    ge.run();
}
}