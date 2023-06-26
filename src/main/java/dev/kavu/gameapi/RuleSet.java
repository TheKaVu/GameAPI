package dev.kavu.gameapi;

import org.apache.commons.lang.Validate;

import java.util.HashSet;
import java.util.Objects;

/**
 * Set of {@link Rule} objects, used to store the rules for a game. Those can be found by their name. <br/>
 * <b>Note:</b> There cannot be two or more rules of the same name, even if they are two different objects.
 */
public class RuleSet extends HashSet<Rule<?>> {

    /**
     * Fetches the rule of given name.
     * @param name Name of the desired rule
     * @return {@link Rule} object of unknown type, {@code null} if no matches found
     */
    public Rule<?> getRule(String name) {
        Validate.notNull(name, "name cannot be null");

        for (Rule<?> rule : this) {
            if (Objects.equals(rule.getName(), name)) return rule;
        }
        return null;
    }

    /**
     * Fetches the rule of given name and tries to cast it to parametrized {@link Rule} object.
     * @param name Name of the desired rule
     * @param clazz Enum class of {@link E} type
     * @param <E> Type of enum returned <tt>Rule</tt> object will be parametrized with
     * @return {@link Rule} object of unknown type, {@code null} if no matches found
     */
    public <E extends Enum<E>> Rule<E> getRule(String name, Class<E> clazz) {
        Validate.notNull(name, "name cannot be null");
        Validate.notNull(clazz, "clazz cannot be null");

        return (Rule<E>) getRule(name);
    }

    /**
     * Adds new rule to the set if it is not already present and no of equal name exists.
     * @param rule {@link Rule} object to be added
     * @return {@code true} if the rule was added successfully, {@code false} if it is already present or there is a rule of similar name
     */
    @Override
    public boolean add(Rule<?> rule) {
        Validate.notNull(rule, "rule cannot be null");

        if(getRule(rule.getName()) != null) return false;
        return super.add(rule);
    }
}
