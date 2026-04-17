package domain.combat;

import domain.item.Item;
import engine.BattleEngine;
import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Combatant {
    private int skillCooldown;
    private List<Item> inventory = new ArrayList<>();

    public Player(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
        this.skillCooldown = 0;
    }

    public int getSkillCooldown() { return skillCooldown; }

    public void setSkillCooldown(int val) { this.skillCooldown = Math.max(0, val); }

    public void reduceSkillCooldown() {
        if (skillCooldown > 0) skillCooldown--;
    }

    public void addBonusAttack(int amount) {
        if (amount > 0) attack += amount;
    }

    public List<Item> getInventory() { return inventory; }

    public void addItem(Item item) { inventory.add(item); }

    public abstract void executeSpecialSkill(Combatant target, BattleEngine engine);

    @Override
    public void useSpecialSkillWithoutCooldown() {
        // Default no-op; overridden in Warrior/Wizard if needed
    }
}