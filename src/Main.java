package ui;

import domain.action.BasicAttackAction;
import domain.action.DefendAction;
import domain.action.SpecialSkillAction;
import domain.combat.Goblin;
import domain.combat.Player;
import domain.combat.Warrior;
import domain.combat.Wizard;
import domain.level.Level;
import domain.level.LevelFactory;
import engine.BattleEngine;

public class Main {
  public static void main(String[] args) {
    Player warrior = new Warrior("Kai");
    Goblin goblin = new Goblin("Goblin_1");

    BasicAttackAction basicAttack = new BasicAttackAction();
    DefendAction defend = new DefendAction();

    basicAttack.execute(warrior, goblin, null);
    defend.execute(warrior, null, null);

    Level medium = LevelFactory.createLevel("medium");
    System.out.println("Loaded level: " + medium.getDifficulty());
    System.out.println("Initial enemies: " + medium.getInitialWave().size());

    Player wizard = new Wizard("Ezra");
    BattleEngine engine = new BattleEngine(medium.getInitialWave());

    SpecialSkillAction specialSkill = new SpecialSkillAction();
    specialSkill.execute(wizard, null, engine);
  }
}