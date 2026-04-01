package domain.combat;

public abstract class Combatant {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected int speed;

    public Combatant(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int damage) {
        hp = Math.max(0, hp - Math.max(0, damage));
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + Math.max(0, amount));
    }

    public void increaseDefense(int amount) {
        if (amount > 0) {
            defense += amount;
        }
    }

    public void decreaseDefense(int amount) {
        if (amount > 0) {
            defense = Math.max(0, defense - amount);
        }
    }

    @Override
    public String toString() {
        return name + " | HP: " + hp + "/" + maxHp
                + " | ATK: " + attack
                + " | DEF: " + defense
                + " | SPD: " + speed;
    }
}