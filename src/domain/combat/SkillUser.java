package domain.combat;

import engine.BattleContext;

public interface SkillUser {
    void executeSpecialSkill(Combatant target, BattleContext ctx);
    int getSkillCooldown();
    void setSkillCooldown(int val);
}