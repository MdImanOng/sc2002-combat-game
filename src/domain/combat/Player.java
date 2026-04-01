package domain.combat;

public abstract class Player extends Combatant {
    private int skillCooldown;
    private int defendTurnsLeft;

    public Player(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
        this.skillCooldown = 0;
        this.defendTurnsLeft = 0;
    }

    public int getSkillCooldown() {
        return skillCooldown;
    }

    public void setSkillCooldown(int skillCooldown) {
        this.skillCooldown = Math.max(0, skillCooldown);
    }

    public void reduceSkillCooldown() {
        if (skillCooldown > 0) {
            skillCooldown--;
        }
    }

    public void activateDefend() {
        if (defendTurnsLeft == 0) {
            defense += 10;
        }
        defendTurnsLeft = 2;
    }

    public void updateDefendStatus() {
        if (defendTurnsLeft > 0) {
            defendTurnsLeft--;
            if (defendTurnsLeft == 0) {
                defense -= 10;
            }
        }
    }

    public boolean isDefending() {
        return defendTurnsLeft > 0;
    }

    public int getDefendTurnsLeft() {
        return defendTurnsLeft;
    }

    public void addBonusAttack(int amount) {
        if (amount > 0) {
            attack += amount;
        }
    }
}