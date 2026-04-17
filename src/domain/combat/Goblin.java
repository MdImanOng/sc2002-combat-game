package domain.combat;

import engine.BasicAttackStrategy;

public class Goblin extends Enemy {
    public Goblin() { super("Goblin", 55, 35, 15, 25, new BasicAttackStrategy()); }
    public Goblin(String name) { super(name, 55, 35, 15, 25, new BasicAttackStrategy()); }
}