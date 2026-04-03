package domain.status;

import domain.combat.Combatant;

public class DefendEffect implements StatusEffect {

    private int duration = 2;
    private int bonus = 10;

    @Override
    public void apply(Combatant target) {
        target.increaseDefense(bonus);
    }

    @Override
    public void onTurnStart(Combatant target) {
        duration--;

        if (duration <= 0) {
            target.decreaseDefense(bonus);
        }
    }

    @Override
    public boolean isExpired() {
        return duration <= 0;
    }

    @Override
    public String getName() {
        return "Defend";
    }
}

