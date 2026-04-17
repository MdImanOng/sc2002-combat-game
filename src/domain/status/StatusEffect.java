package domain.status;

import domain.combat.Combatant;

public abstract class StatusEffect {
    protected EffectType type;
    protected int remainingTurns;

    public StatusEffect(EffectType type, int remainingTurns) {
        this.type = type;
        this.remainingTurns = remainingTurns;
    }

    public abstract void apply(Combatant target);
    public abstract void remove(Combatant target);
    public abstract void onTurnStart(Combatant target);

    public boolean tick() {
        if (remainingTurns > 0) remainingTurns--;
        return isExpired();
    }

    public EffectType getType() { return type; }

    public boolean isExpired() { return remainingTurns <= 0; }

    public String getName() { return type.name(); }
}