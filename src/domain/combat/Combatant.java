package domain.combat;

import domain.status.StatusEffect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Combatant {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected int speed;
    private List<StatusEffect> effects = new ArrayList<>();

    public Combatant(String name, int maxHp, int attack, int defense, int speed) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty.");
        if (maxHp <= 0) throw new IllegalArgumentException("Max HP must be positive.");
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

    public boolean isAlive() { return hp > 0; }

    public boolean isStunned() {
        for (StatusEffect e : effects)
            if (e.getName().equals("Stun")) return true;
        return false;
    }

    public boolean isInvulnerable() {
        for (StatusEffect e : effects)
            if (e.getName().equals("Smoke Bomb Invulnerability")) return true;
        return false;
    }

    public void takeDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("Damage cannot be negative.");
        if (isInvulnerable()) {
            System.out.println(name + " is invulnerable! No damage taken.");
            return;
        }
        int actual = Math.max(0, damage);
        hp = Math.max(0, hp - actual);
        System.out.println(name + " takes " + actual + " damage. HP: " + hp + "/" + maxHp);
    }

    public void heal(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Heal amount cannot be negative.");
        hp = Math.min(maxHp, hp + amount);
        System.out.println(name + " healed to " + hp + "/" + maxHp);
    }

    public void increaseDefense(int amount) {
        if (amount > 0) defense += amount;
    }

    public void decreaseDefense(int amount) {
        if (amount > 0) defense = Math.max(0, defense - amount);
    }

    public void addStatusEffect(StatusEffect effect) {
        if (effect == null) throw new IllegalArgumentException("Status effect cannot be null.");
        effects.add(effect);
        effect.apply(this);
    }

    public void processEffects() {
        Iterator<StatusEffect> it = effects.iterator();
        while (it.hasNext()) {
            StatusEffect e = it.next();
            e.onTurnStart(this);
            if (e.isExpired()) it.remove();
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