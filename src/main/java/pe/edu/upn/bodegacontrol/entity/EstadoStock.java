package pe.edu.upn.bodegacontrol.entity;

/** Estado calculado del inventario de un producto (no se guarda en la base de datos). */
public enum EstadoStock {
    DISPONIBLE,
    STOCK_BAJO,
    AGOTADO
}
