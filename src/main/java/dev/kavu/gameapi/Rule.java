package dev.kavu.gameapi;

import java.io.Serializable;

public class Rule<E extends Enum<E>> implements Serializable {

    private final String name;
    private final AffectTarget target;
    private E status;

    public Rule(String name, AffectTarget target, E status) {
        this.name = name;
        this.target = target;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public E getStatus(){
        return status;
    }

    public AffectTarget getTarget() {
        return target;
    }

    public boolean check(E status) {
        return this.status.equals(status);
    }

    public void setStatus(E status) {
        this.status = status;
    }

    public enum Basic {
        ON,
        OFF;
    }
}
