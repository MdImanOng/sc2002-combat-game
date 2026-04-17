package domain.combat;

import java.util.*;
import domain.status.StatusEffect;

public abstract class Combatant {

    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected int speed;

    private boolean stunned = false;
    private boolean invulnerable = false;
    private List<StatusEffect> effects = new ArrayList<>();

    public Combatant(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int damage) {
    if (invulnerable) {
        System.out.println(name + " is invulnerable! No damage taken.");
        return;
    }
    int actual = Math.max(0, damage);
    hp = Math.max(0, hp - actual);
    System.out.println(name + " takes " + actual + " damage. HP: " + hp + "/" + maxHp);
}

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + Math.max(0, amount));
    }

    public void increaseDefense(int amount) {
        if (amount > 0) defense += amount;
    }

    public void decreaseDefense(int amount) {
        if (amount > 0) defense = Math.max(0, defense - amount);
    }

    // ===== STATUS EFFECT SYSTEM =====
    public void addStatusEffect(StatusEffect effect) {
        effects.add(effect);
        effect.apply(this);
    }

    public void processEffects() {
        Iterator<StatusEffect> it = effects.iterator();

        while (it.hasNext()) {
            StatusEffect e = it.next();
            e.onTurnStart(this);

            if (e.isExpired()) {
                it.remove();
            }
        }
    }

    public boolean isStunned() { return stunned; }
    public void setStunned(boolean val) { stunned = val; }

    public boolean isInvulnerable() { return invulnerable; }
    public void setInvulnerable(boolean val) { invulnerable = val; }

    @Override
    public String toString() {
        return name + " | HP: " + hp + "/" + maxHp
                + " | ATK: " + attack
                + " | DEF: " + defense
                + " | SPD: " + speed;
    }

    // REQUIRED for PowerStone
    public abstract void useSpecialSkillWithoutCooldown();
}

