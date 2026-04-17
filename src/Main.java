import engine.GameController;
import ui.BattleUI;
import ui.GameMenuUI;
import domain.level.LevelFactory;

public class Main {
    public static void main(String[] args) {
        new GameController(
                new GameMenuUI(),
                new BattleUI(),
                new LevelFactory()
        ).start();
    }
}