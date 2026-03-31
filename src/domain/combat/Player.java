package domain.combat;

public abstract class Player extends Combatant {
    protected int skillCooldown;
    protected int defendTurnsRemaining;
    protected int bonusAttack;

    public Player(String name, int hp, int attack, int defense) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.skillCooldown = 0;
        this.defendTurnsRemaining = 0;
        this.bonusAttack = 0;
    }

    public int getSkillCooldown() {
        return skillCooldown;
    }

    public void setSkillCooldown(int skillCooldown) {
        this.skillCooldown = skillCooldown;
    }

    public void reduceCooldown() {
        if (skillCooldown > 0) {
            skillCooldown--;
        }
    }

    public void activateDefend() {
        defendTurnsRemaining = 2;
    }

    public void updateDefendStatus() {
        if (defendTurnsRemaining > 0) {
            defendTurnsRemaining--;
        }
    }

    @Override
    public int getDefense() {
        if (defendTurnsRemaining > 0) {
            return defense + 10;
        }
        return defense;
    }

    @Override
    public int getAttack() {
        return attack + bonusAttack;
    }

    public void addBonusAttack(int amount) {
        bonusAttack += amount;
    }
}