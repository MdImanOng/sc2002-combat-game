package domain.action;

import domain.combat.Combatant;
import domain.combat.Player;
import domain.item.Item;
import engine.BattleEngine;
import java.util.List;
import java.util.Scanner;

public class UseItemAction implements Action {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String getName() { return "Use Item"; }

    @Override
    public void execute(Player actor, Combatant target, BattleEngine engine) {
        List<Item> inventory = actor.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("No items left!");
            return;
        }
        System.out.println("Choose an item:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + inventory.get(i).getName());
        }
        int choice = -1;
        while (choice < 1 || choice > inventory.size()) {
            System.out.print("Enter choice: ");
            try { choice = Integer.parseInt(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { choice = -1; }
        }
        Item chosen = inventory.get(choice - 1);
        chosen.use(actor, target, engine);
        inventory.remove(choice - 1);
        System.out.println(chosen.getName() + " used and removed from inventory.");
    }
}