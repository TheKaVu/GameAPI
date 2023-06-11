package dev.kavu.gameapi;

import org.apache.commons.lang.Validate;

import java.util.HashSet;
import java.util.Objects;

public class RuleSet extends HashSet<Rule<?>> {

    public Rule<?> getRule(String name) {
        Validate.notNull(name, "name cannot be null");

        for (Rule<?> rule : this) {
            if (Objects.equals(rule.getName(), name)) return rule;
        }
        return null;
    }

    public <E extends Enum<E>> Rule<E> getRule(String name, Class<E> clazz) {
        Validate.notNull(name, "name cannot be null");
        Validate.notNull(clazz, "clazz cannot be null");

        return (Rule<E>) getRule(name);
    }

    @Override
    public boolean add(Rule<?> rule) {
        Validate.notNull(rule, "rule cannot be null");

        if(getRule(rule.getName()) != null) return false;
        return super.add(rule);
    }
}
