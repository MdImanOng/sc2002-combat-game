package domain.combat;

public class Warrior extends Player {

    public Warrior(String name) {
        super(name, 260, 40, 20, 30);
    }

    @Override
    public void useSpecialSkillWithoutCooldown() {
        System.out.println(getName() + " uses Shield Bash!");
    }
}



