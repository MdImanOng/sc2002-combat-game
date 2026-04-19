package domain.combat;

import domain.action.*;
import domain.item.Item;
import engine.BattleContext;
import ui.BattleUI;
import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Combatant implements SkillUser {
    private int specialCooldown;
    private List<Item> items = new ArrayList<>();

    public Player(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
        this.specialCooldown = 0;
    }

    @Override
    public int getSkillCooldown() { return specialCooldown; }

    @Override
    public void setSkillCooldown(int val) { this.specialCooldown = Math.max(0, val); }

    public void reduceSkillCooldown() { if (specialCooldown > 0) specialCooldown--; }

    public boolean isSpecialReady() { return specialCooldown == 0; }

    public List<Item> getInventory() { return items; }

    public void addItem(Item item) { items.add(item); }

    public void useItem(int idx, Combatant target, BattleContext ctx) {
        if (idx < 0 || idx >= items.size()) throw new IllegalArgumentException("Invalid item index.");
        Item item = items.get(idx);
        new UseItemAction(item).execute(this, target, ctx);
        items.remove(idx);
    }

    public ActionType chooseAction(BattleUI ui) {
        return ui.promptActionChoice(!items.isEmpty());
    }

    @Override
    public abstract void executeSpecialSkill(Combatant target, BattleContext ctx);

    @Override
    public void performAction(BattleContext ctx) {
        // Decrement cooldown at START of turn before action choice.
        // Matches spec: "cooldown 3 turns including current round"
        // meaning set=3 on use turn, tick each subsequent turn start.
        reduceSkillCooldown();

        ActionType actionType = chooseAction(ctx.getUI());
        List<Enemy> alive = ctx.getAliveEnemies();

        switch (actionType) {
            case BASIC_ATTACK -> {
                Combatant target = ctx.getUI().promptTargetChoice(alive);
                new BasicAttackAction().execute(this, target, ctx);
            }
            case DEFEND -> new DefendAction().execute(this, null, ctx);
            case SPECIAL_SKILL -> {
                Combatant target = ctx.getUI().promptTargetChoice(alive);
                new SpecialSkillAction().execute(this, target, ctx);
            }
            case USE_ITEM -> {
                ctx.getUI().displayItemList(items);
                int idx = ctx.getUI().promptItemIndex(items.size());
                Combatant target = ctx.getUI().promptTargetChoice(alive);
                useItem(idx, target, ctx);
            }
        }
    }
}