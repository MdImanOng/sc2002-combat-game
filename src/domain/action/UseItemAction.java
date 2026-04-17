package domain.action;

import domain.combat.Combatant;
import domain.item.Item;
import engine.BattleEngine;

public class UseItemAction implements Action {
    private final Item item;

    public UseItemAction(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null.");
        this.item = item;
    }

    @Override
    public String getName() { return "Use Item: " + item.getName(); }

    @Override
    public void execute(Combatant actor, Combatant target, BattleEngine engine) {
        item.use(actor, target, engine);
    }
}