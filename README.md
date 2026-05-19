# Sanjuan_Post2_U12_Patrones

**Unidad 12 — Validación Arquitectónica con ArchUnit y ADR**  
Patrones de Diseño de Software · Ingeniería de Sistemas · UDES 2026

> Este proyecto extiende `Sanjuan_Post1_U12_Patrones` con validación arquitectónica
> automatizada (ArchUnit + GitHub Actions) y documentación de decisiones (ADR).

---

## Descripción

Sistema de gestión de pedidos en Spring Boot que integra cuatro patrones GoF sobre
arquitectura Hexagonal. En este Post-Contenido 2 se agregan:

- **5 reglas ArchUnit** que convierten las restricciones de diseño en pruebas ejecutables.
- **Pipeline de GitHub Actions** que valida la arquitectura en cada push y pull request.
- **3 ADRs** que documentan las decisiones de diseño más importantes del sistema.

---

## Arquitectura del Sistema

```
com.empresa.pedidos/
├── dominio/                     ← Núcleo de negocio (Instability = 0.00)
│   ├── Pedido.java
│   ├── TipoPedido.java
│   ├── EstadoPedido.java
│   └── puertos/                 ← Interfaces (contratos abstractos)
│       ├── RepositorioPedidos.java
│       ├── ProcesadorPedido.java
│       └── ServicioNotificacion.java
├── aplicacion/                  ← Casos de uso (Instability = 0.29)
│   ├── ServicioPedidos.java
│   ├── ProcesadorPedidoFactory.java
│   └── PedidoProcesadoEvent.java
├── infraestructura/             ← Implementaciones técnicas (Instability = 1.00)
│   ├── persistencia/
│   └── notificaciones/
└── adaptadores/                 ← Entrada/salida (Instability = 0.67)
    ├── procesadores/
    ├── facade/
    └── rest/
```

---

## Validación Arquitectónica

Las reglas se encuentran en:
`src/test/java/com/empresa/pedidos/arquitectura/ReglasArquitecturaTest.java`

```bash
mvn test -Dtest=ReglasArquitecturaTest
# Salida esperada: Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
```

### Las 5 reglas implementadas

| # | Nombre de la regla | Qué protege |
|---|---|---|
| 1 | `dominioAisladoDeInfraestructura` | El dominio no importa clases de infraestructura, adaptadores, JPA ni JavaMail |
| 2 | `controladorSoloFacade` | El controlador REST solo accede a `FachadaPedidos` y al dominio |
| 3 | `puertosComoInterfaces` | Todos los elementos de `dominio.puertos` son interfaces |
| 4 | `procesadoresImplementanPuerto` | Todo componente en `adaptadores.procesadores` implementa `ProcesadorPedido` |
| 5 | `infraestructuraNoAccedeRest` | La infraestructura no accede a los adaptadores REST (evita ciclos) |

### Ejemplo de violación detectada

Si alguien agrega en `Pedido.java`:
```java
import com.empresa.pedidos.infraestructura.persistencia.PedidoJpaRepository;
```

ArchUnit falla el build con:
```
Architecture Violation [Priority: MEDIUM] - Rule
'no classes that reside in a package '..dominio..'
should depend on classes that reside in '..infraestructura..'
because El dominio no debe conocer infraestructura ni adaptadores (ADR-001)'
was violated (1 times)
```

### Prueba de violación intencional (Paso 6)

```bash
# 1. Introducir violación temporal en Pedido.java
# 2. Commit y push a develop
git add . && git commit -m "test: violacion de arquitectura intencional para verificar pipeline"
git push origin develop
# 3. GitHub Actions falla el job "arquitectura"
# 4. Revertir
git revert HEAD && git push origin develop
# 5. Pipeline vuelve a verde
```

---

## Pipeline de GitHub Actions

Archivo: `.github/workflows/arquitectura.yml`

Se ejecuta en cada push a `main` y `develop`, y en cada PR a `main`:

```
push/PR → Checkout → Java 17 → Cache Maven
        → mvn test -Dtest=ReglasArquitecturaTest   ← falla si hay violación
        → mvn verify                                ← suite completa
```

---

## Decisiones de Diseño (ADRs)

Los Architecture Decision Records se encuentran en `docs/adr/`:

| ADR | Decisión | Estado |
|---|---|---|
| [ADR-001](docs/adr/ADR-001.md) | Adopción de Arquitectura Hexagonal | Aceptado |
| [ADR-002](docs/adr/ADR-002.md) | Factory + Strategy para selección de procesador | Aceptado |
| [ADR-003](docs/adr/ADR-003.md) | Spring Events (Observer) para notificaciones | Aceptado |

---

## Patrones implementados (Post-Contenido 1)

| Patrón | Clase principal | Problema que resuelve |
|---|---|---|
| Strategy | `ProcesadorPedido` + 3 implementaciones | Elimina el `if/else if` de cálculo de costo |
| Factory | `ProcesadorPedidoFactory` | Selección dinámica de estrategia por tipo |
| Observer | `PedidoProcesadoEvent` + 2 listeners | Notificaciones desacopladas |
| Facade | `FachadaPedidos` | El controller tiene 1 sola dependencia |

---

## Ejecución

```bash
# Compilar y ejecutar todas las pruebas (incluye ArchUnit)
mvn clean package

# Solo reglas de arquitectura
mvn test -Dtest=ReglasArquitecturaTest

# Levantar la aplicación
mvn spring-boot:run

# POST de prueba con curl
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"cliente":"Jair","subtotal":100.0,"tipo":"EXPRESS"}'
```


## Tecnologías

- Java 17 · Spring Boot 3.2.5 · Spring Data JPA · H2 · Maven
- ArchUnit 1.2.1 · JUnit 5 · AssertJ
- GitHub Actions (CI/CD)
