package domain.combat;

import engine.BasicAttackStrategy;

public class Wolf extends Enemy {
    public Wolf() { super("Wolf", 40, 45, 5, 35, new BasicAttackStrategy()); }
    public Wolf(String name) { super(name, 40, 45, 5, 35, new BasicAttackStrategy()); }
}