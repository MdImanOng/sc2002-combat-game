package domain.status;

import domain.combat.Combatant;

public class StunEffect implements StatusEffect {
    private int duration = 2;

    @Override
    public void apply(Combatant target) {
        // isStunned() is now derived from active effects — no setter needed
    }

    @Override
    public void onTurnStart(Combatant target) {
        duration--;
    }

    @Override
    public boolean isExpired() { return duration <= 0; }

    @Override
    public String getName() { return "Stun"; }
}