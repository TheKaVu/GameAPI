package dev.kavu.gameapi;

import org.apache.commons.lang.Validate;

import java.io.Serializable;

public class Rule<E extends Enum<E>> implements Serializable {

    private final String name;
    private E status;

    public Rule(String name, E status) {
        Validate.notNull(name, "name cannot be null");
        Validate.notNull(status, "status cannot be null");

        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public E getStatus(){
        return status;
    }

    public boolean check(E status) {
        Validate.notNull(status, "status cannot be null");
        return this.status.equals(status);
    }

    public void setStatus(E status) {
        Validate.notNull(status, "status cannot be null");
        this.status = status;
    }
}
