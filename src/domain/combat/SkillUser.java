package domain.combat;

import engine.BattleEngine;

public interface SkillUser {
    void executeSpecialSkill(Combatant target, BattleEngine engine);
    int getSkillCooldown();
    void setSkillCooldown(int val);
}