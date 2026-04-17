package domain.status;

import domain.combat.Combatant;

public class DefendEffect extends StatusEffect {
    private static final int BONUS = 10;

    public DefendEffect() {
        super(EffectType.DEFEND_BUFF, 2);
    }

    @Override
    public void apply(Combatant target) {
        target.increaseDefense(BONUS);
    }

    @Override
    public void remove(Combatant target) {
        target.decreaseDefense(BONUS);
    }

    @Override
    public void onTurnStart(Combatant target) {
        tick();
        if (isExpired()) remove(target);
    }
}