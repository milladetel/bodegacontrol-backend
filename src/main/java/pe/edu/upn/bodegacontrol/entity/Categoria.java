package pe.edu.upn.bodegacontrol.entity;

public enum Categoria {
    ABARROTES("Abarrotes"),
    BEBIDAS("Bebidas"),
    LACTEOS("Lácteos"),
    LIMPIEZA("Limpieza"),
    SNACKS("Snacks"),
    OTROS("Otros");

    private final String etiqueta;

    Categoria(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
