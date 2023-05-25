package dev.kavu.gameapi;

import java.util.HashSet;
import java.util.Objects;

public class RuleSet extends HashSet<Rule<?>> {

    public Rule<?> getRule(String name) {
        for (Rule<?> rule : this) {
            if (Objects.equals(rule.getName(), name)) return rule;
        }
        return null;
    }

    public <E extends Enum<E>> Rule<E> getRule(String name, Class<E> clazz) {
        return (Rule<E>) getRule(name);
    }

    @Override
    public boolean add(Rule<?> rule) {
        if(getRule(rule.getName()) != null) return false;
        return super.add(rule);
    }
}
