package domain.combat;

public class Goblin extends Enemy {

    public Goblin() {
        super("Goblin", 55, 35, 15, 25);
    }

    public Goblin(String name) {
        super(name, 55, 35, 15, 25);
    }
    @Override
    public void useSpecialSkillWithoutCooldown() {
    // enemies don’t have special skills
}
}