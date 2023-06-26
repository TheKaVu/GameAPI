package dev.kavu.gameapi;

import org.apache.commons.lang.Validate;

import java.io.Serializable;

/**
 * Dynamic enum property of any game influencing its specific behaviour.
 * @param <E> Enumeration type declaring this rule's values
 */
public class Rule<E extends Enum<E>> implements Serializable {

    private final String name;
    private E status;

    /**
     * Creates new instance of <tt>Rule</tt> class with given name and initial status.
     * @param name Name of this rule
     * @param status Status this rule is going to be set
     */
    public Rule(String name, E status) {
        Validate.notNull(name, "name cannot be null");
        Validate.notNull(status, "status cannot be null");

        this.name = name;
        this.status = status;
    }

    /**
     * @return Name of this rule
     */
    public String getName() {
        return name;
    }
    /**
     * @return Current status of this rule
     */
    public E getStatus(){
        return status;
    }

    /**
     * Checks if current status matches the specified one.
     * @param status Status to be checked
     * @return {@code true} if both statuses match, {@code false} otherwise
     */
    public boolean check(E status) {
        Validate.notNull(status, "status cannot be null");
        return this.status.equals(status);
    }

    /**
     * Sets status of this rule to specified one.
     * @param status New status value
     */
    public void setStatus(E status) {
        Validate.notNull(status, "status cannot be null");
        this.status = status;
    }
}
