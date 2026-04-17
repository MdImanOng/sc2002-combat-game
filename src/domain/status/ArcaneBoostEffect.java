package domain.status;

import domain.combat.Combatant;

public class ArcaneBoostEffect extends StatusEffect {
    private final int boostAmount;

    public ArcaneBoostEffect(int boostAmount) {
        super(EffectType.ARCANE_BOOST, Integer.MAX_VALUE); // permanent until level ends
        this.boostAmount = boostAmount;
    }

    @Override
    public void apply(Combatant target) {
        target.increaseAttack(boostAmount);
        System.out.println(target.getName() + " gains +" + boostAmount + " ATK from Arcane Blast!");
    }

    @Override
    public void remove(Combatant target) {
        target.decreaseAttack(boostAmount);
    }

    @Override
    public void onTurnStart(Combatant target) {
        // permanent — never ticks down during battle
    }

    @Override
    public boolean isExpired() { return false; }
}