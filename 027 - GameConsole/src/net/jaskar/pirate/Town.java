package net.jaskar.pirate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public record Town(String name, String island, int level, List<Loot> loots, List<Feature> features, List<Combatant> opponents) {
    public Town {
        loots = randomReduced(new ArrayList<>(EnumSet.allOf(Loot.class)), level + 2);
        features = randomReduced(new ArrayList<>(EnumSet.allOf(Feature.class)), level + 3);
        opponents = new ArrayList<>();
        if (level == 0) {
            opponents.add(new Islander("Joe", Weapon.KNIFE));
        } else {
            opponents.add(new Islander("Joe", Weapon.MACHETE));
            opponents.add(new Soldier("John", Weapon.PISTOL));
        }
    }

    public Town(String name, String island, int level) {
        this(name, island, level, List.of(), List.of(), List.of());
    }

    private <T> List<T> randomReduced(List<T> list, int size) {
        Collections.shuffle(list);
        return list.subList(0, size);
    }

    @Override
    public String toString() {
        return name + ", " + island;
    }

    public String information() {
        return "Town: " + this + "\n\tloot=" + loots +
                "\n\tfeatures=" + features +
                "\n\topponents=" + opponents;
    }

    public List<Loot> loot() {
        return (loots == null) ? null : new ArrayList<>(loots);
    }

    public List<Combatant> opponents() {
        return (opponents == null) ? null : new ArrayList<>(opponents);
    }

    public List<Feature> features() {
        return (features == null) ? null : new ArrayList<>(features);
    }
}
