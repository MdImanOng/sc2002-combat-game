package domain.status;

import domain.combat.Combatant;

public class SmokeBombEffect implements StatusEffect {
    private int duration = 2;

    @Override
    public void apply(Combatant target) {
    }

    @Override
    public void onTurnStart(Combatant target) {
        duration--;
    }

    @Override
    public boolean isExpired() { return duration <= 0; }

    @Override
    public String getName() { return "Smoke Bomb Invulnerability"; }
}