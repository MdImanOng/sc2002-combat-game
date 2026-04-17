package domain.action;

import domain.combat.Combatant;
import domain.item.Item;
import engine.BattleContext;

public class UseItemAction implements Action {
    private final Item item;

    public UseItemAction(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null.");
        this.item = item;
    }

    @Override
    public ActionType getType() { return ActionType.USE_ITEM; }

    @Override
    public void execute(Combatant actor, Combatant target, BattleContext ctx) {
        item.use(actor, target, ctx);
    }
}