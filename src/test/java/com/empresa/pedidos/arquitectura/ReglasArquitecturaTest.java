package com.empresa.pedidos.arquitectura;

import com.empresa.pedidos.dominio.puertos.ProcesadorPedido;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Reglas de validación arquitectónica — Post-Contenido 2, Unidad 12.
 *
 * Cada regla es una fitness function estática (sección 5.2 de la guía):
 * se ejecuta en cada build de Maven y en el pipeline de GitHub Actions.
 * Si alguna clase viola una regla, el build falla con un mensaje descriptivo.
 *
 * Las 5 reglas implementadas protegen las invariantes de la arquitectura
 * Hexagonal adoptada en ADR-001.
 */
@AnalyzeClasses(
        packages = "com.empresa.pedidos",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class ReglasArquitecturaTest {

    /**
     * Regla 1: El dominio no depende de infraestructura ni adaptadores.
     *
     * Fundamento (ADR-001): el dominio es el núcleo estable del sistema
     * (Instability = 0). Si depende de infraestructura, un cambio de JPA o
     * de canal de notificación obliga a modificar el dominio — viola la
     * Dependency Inversion Principle de la arquitectura Hexagonal.
     */
    @ArchTest
    static final ArchRule dominioAisladoDeInfraestructura = noClasses()
            .that().resideInAPackage("..dominio..")
            .should().dependOnClassesThat()
            .resideInAnyPackage(
                    "..infraestructura..",
                    "..adaptadores..",
                    "javax.persistence..",
                    "org.springframework.mail.."
            )
            .because("El dominio no debe conocer infraestructura ni adaptadores (ADR-001: Arquitectura Hexagonal)");

    /**
     * Regla 2: Los controladores REST solo acceden a la Facade.
     *
     * Fundamento (ADR-001 + Facade Pattern): limitar el acoplamiento del
     * controlador a una única dependencia — FachadaPedidos — reduce su CE
     * y lo protege de cambios internos del sistema de aplicación.
     */
    @ArchTest
    static final ArchRule controladorSoloFacade = classes()
            .that().resideInAPackage("..adaptadores.rest..")
            .should().onlyAccessClassesThat()
            .resideInAnyPackage(
                    "..adaptadores.facade..",
                    "..dominio..",
                    "org.springframework.web..",
                    "org.springframework.http..",
                    "java.."
            )
            .because("El controlador REST solo debe hablar con FachadaPedidos (Facade Pattern)");

    /**
     * Regla 3: Los puertos de dominio deben ser interfaces.
     *
     * Fundamento (ADR-001): los puertos son contratos abstractos del dominio.
     * Una clase concreta en dominio.puertos rompería la inversión de dependencias.
     */
    @ArchTest
    static final ArchRule puertosComoInterfaces = classes()
            .that().resideInAPackage("..dominio.puertos..")
            .should().beInterfaces()
            .because("Los puertos de dominio deben ser interfaces puras, sin implementación concreta (ADR-001)");

    /**
     * Regla 4: Los procesadores implementan el puerto ProcesadorPedido.
     *
     * Fundamento (ADR-002): cada estrategia debe cumplir el contrato del puerto.
     * Una clase en el paquete procesadores sin implementar ProcesadorPedido
     * es un objeto huérfano sin contrato verificable.
     */
    @ArchTest
    static final ArchRule procesadoresImplementanPuerto = classes()
            .that().resideInAPackage("..adaptadores.procesadores..")
            .should().implement(ProcesadorPedido.class)
            .because("Todo procesador debe implementar el puerto ProcesadorPedido (ADR-002: Strategy Pattern)");

    /**
     * Regla 5: La infraestructura no accede a los adaptadores REST.
     *
     * Fundamento: la infraestructura es capa de salida; acceder a los
     * adaptadores REST (capa de entrada) crearía una dependencia circular
     * que rompe el flujo unidireccional de la arquitectura Hexagonal.
     */
    @ArchTest
    static final ArchRule infraestructuraNoAccedeRest = noClasses()
            .that().resideInAPackage("..infraestructura..")
            .should().accessClassesThat()
            .resideInAPackage("..adaptadores.rest..")
            .because("La infraestructura no debe depender de los adaptadores REST (dependencia circular)");
}
