package net.jaskar;

import net.jaskar.enums.Geometry;

public interface Mappable {
    String JSON_PROPERTY = """
            "properties": {%s} """;
    default String toJSON() {
        return """
                "type": "%s", "label": "%s", "marker": "%s" """
                .formatted(Mappable.this.getShape(), Mappable.this.getLabel(), Mappable.this.getMarker());
    }
    static void mapIt(Mappable mappable) {
        System.out.println(JSON_PROPERTY.formatted(mappable.toJSON()));
    }
    String getLabel();
    Geometry getShape();
    String getMarker();
}
