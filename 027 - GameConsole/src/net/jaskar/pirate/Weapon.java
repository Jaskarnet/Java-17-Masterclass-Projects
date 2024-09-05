package net.jaskar.pirate;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum Weapon {
    KNIFE(0, 10),
    AXE(0, 30),
    MACHETE(1, 40),
    PISTOL(1, 50);

    private int minLevel;
    private int hitPoints;

    {
        if (hitPoints < 0) hitPoints = 0;
        if (minLevel < 0) minLevel = 0;
    }

    Weapon(int minLevel, int hitPoints) {
        this.minLevel = minLevel;
        this.hitPoints = hitPoints;
    }

    public static Weapon getWeaponByChar(char firstInitial) {
        for (Weapon weapon : Weapon.values()) {
            if (weapon.name().charAt(0) == firstInitial) {
                return weapon;
            }
        }
        return values()[0];
    }

    public static List<Weapon> getWeaponsByLevel(int levelOfPlay) {
        List<Weapon> weapons = new ArrayList<>(EnumSet.allOf(Weapon.class));
        weapons.removeIf(w -> w.minLevel > levelOfPlay);
        return weapons;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getHitPoints() {
        return hitPoints;
    }
}
