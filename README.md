# BodegaControl – Backend (API REST)

API REST del sistema web de inventario y ventas **BodegaControl** (caso: Bodega Santa Rosa).
Proyecto del curso *Soluciones Web y Aplicaciones Distribuidas* – Ingeniería de Sistemas Computacionales, UPN.

El frontend en Angular está en: https://github.com/milladetel/bodegacontrol-frontend

## Tecnologías

- Java 17 · Spring Boot 3.5.9 (Web, Data JPA, Validation, Security)
- MySQL 8.4 (perfil `mysql`, por defecto) · H2 en memoria (perfil `h2`, para pruebas)
- JWT (jjwt 0.12.6) · BCrypt · Lombok · Maven Wrapper
- Postman / Newman para las pruebas de los endpoints

## Ejecución

Requisitos: JDK 17 y MySQL en `localhost:3306` (la base `bodegacontrol` se crea sola).

```bash
# Windows
mvnw.cmd spring-boot:run
# Linux / macOS
./mvnw spring-boot:run
```

Si el usuario `root` de MySQL tiene contraseña, defínala antes de ejecutar:

```bash
set DB_PASSWORD=su_clave        # Windows (cmd)
$env:DB_PASSWORD="su_clave"     # Windows (PowerShell)
export DB_PASSWORD=su_clave     # Linux / macOS
```

Para probar **sin MySQL** (base H2 en memoria):

```bash
mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```

La API queda en `http://localhost:8080/api`. Al iniciar por primera vez se cargan datos de ejemplo:

| Usuario | Contraseña | Rol |
|---------|-----------|-----|
| admin   | admin123  | ADMIN (administrador) |
| cajero  | cajero123 | CAJERO |

Variables de entorno opcionales: `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`, `CORS_ORIGINS` (por defecto `http://localhost:4200`).

## Estructura

```
src/main/java/pe/edu/upn/bodegacontrol
├── config        SecurityConfig, DataInitializer (datos de ejemplo)
├── security      JwtService, JwtAuthFilter, UsuarioDetailsService
├── controller    Auth, Producto, Venta, IngresoStock, General
├── service       Lógica de negocio (@Transactional)
├── repository    Spring Data JPA
├── entity        Usuario, Producto, Venta, DetalleVenta, IngresoStock
├── dto           Records de entrada (con validaciones) y salida
└── exception     GlobalExceptionHandler y excepciones del negocio
```

## Endpoints

Todos requieren `Authorization: Bearer <token>`, excepto el login y `/api/health`.

| Método | Ruta | Rol | Descripción |
|--------|------|-----|-------------|
| POST | `/api/auth/login` | Público | Inicia sesión y devuelve el token JWT |
| GET | `/api/auth/me` | Ambos | Datos del usuario autenticado |
| GET | `/api/productos?buscar=&categoria=&estado=` | Ambos | Lista y filtra productos |
| GET | `/api/productos/{id}` | Ambos | Obtiene un producto |
| GET | `/api/productos/alertas` | Ambos | Agotados, stock bajo o por vencer (30 días) |
| POST | `/api/productos` | ADMIN | Crea un producto (201) |
| PUT | `/api/productos/{id}` | ADMIN | Actualiza un producto |
| DELETE | `/api/productos/{id}` | ADMIN | Baja lógica (204) |
| POST | `/api/ventas` | Ambos | Registra una venta y descuenta stock (201) |
| GET | `/api/ventas?desde=&hasta=` | Ambos | Historial de ventas |
| GET | `/api/ventas/{id}` | Ambos | Detalle de una venta |
| POST | `/api/ingresos` | ADMIN | Registra reposición de stock (201) |
| GET | `/api/ingresos` | ADMIN | Historial de ingresos |
| GET | `/api/dashboard` | Ambos | Indicadores del día y del mes |
| GET | `/api/categorias` | Ambos | Categorías disponibles |
| GET | `/api/health` | Público | Estado de la API |

Errores con formato único:

```json
{
  "timestamp": "2026-11-20T14:37:52",
  "status": 409,
  "error": "Conflict",
  "mensaje": "Stock insuficiente para Inca Kola 1.5 L: disponible 3, solicitado 5",
  "ruta": "/api/ventas"
}
```

## Pruebas con Postman

Importe `postman/BodegaControl.postman_collection.json` en Postman y ejecute la colección en orden
(43 peticiones, 83 aserciones). También se puede ejecutar por consola con Newman:

```bash
npx newman run postman/BodegaControl.postman_collection.json
```

## Autores

- De la Cruz Huaranca, Luis Cristian
- Milla Guerrero, Epifanio Eulogio
- Ruiz Robles, Elianni Jhoselin
- Lizana Romero, Nilton
