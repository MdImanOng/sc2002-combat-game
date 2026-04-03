package domain.combat;

public class Wizard extends Player {

    public Wizard(String name) {
        super(name, 200, 50, 10, 20);
    }

    @Override
    public void useSpecialSkillWithoutCooldown() {
        System.out.println(getName() + " uses Arcane Blast!");
    }
}