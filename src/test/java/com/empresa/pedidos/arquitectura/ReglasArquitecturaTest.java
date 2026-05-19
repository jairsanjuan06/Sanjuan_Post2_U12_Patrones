package com.empresa.pedidos.arquitectura;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Reglas de arquitectura verificadas automáticamente con ArchUnit.
 *
 * <p>Estas reglas actúan como fitness functions estáticas (sección 5.2
 * de la guía): se ejecutan en cada build de Maven y fallan el build
 * si alguna restricción arquitectónica es violada.</p>
 *
 * <p>Reglas verificadas:
 * <ul>
 *   <li>El dominio no depende de infraestructura ni adaptadores.</li>
 *   <li>Los controladores solo acceden a la fachada.</li>
 *   <li>Las interfaces de puerto residen en el paquete dominio.puertos.</li>
 *   <li>La infraestructura no es llamada directamente desde el dominio.</li>
 * </ul>
 * </p>
 */
@AnalyzeClasses(
        packages = "com.empresa.pedidos",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class ReglasArquitecturaTest {

    /**
     * El dominio NO debe depender de infraestructura ni de adaptadores.
     * Garantiza que el núcleo del negocio es independiente de la tecnología.
     */
    @ArchTest
    static final ArchRule dominioAisladoDeInfraestructura = noClasses()
            .that().resideInAPackage("..dominio..")
            .should().dependOnClassesThat()
            .resideInAnyPackage(
                    "..infraestructura..",
                    "..adaptadores.."
            )
            .because("El dominio no debe conocer detalles de infraestructura (Hexagonal Architecture)");

    /**
     * Los controladores REST solo deben depender de la FachadaPedidos.
     * Verifica que el controlador no accede directamente a servicios o repositorios.
     */
    @ArchTest
    static final ArchRule controladoresSoloAccedenAFachada = classes()
            .that().resideInAPackage("..adaptadores.rest..")
            .should().onlyAccessClassesThat()
            .resideInAnyPackage(
                    "..adaptadores.facade..",
                    "..adaptadores.rest..",
                    "..dominio..",
                    "java..",
                    "org.springframework.."
            )
            .because("El controlador REST solo debe hablar con la FachadaPedidos (Facade Pattern)");

    /**
     * Los puertos (interfaces) deben estar en el paquete dominio.puertos.
     * Verifica la correcta ubicación de las abstracciones del dominio.
     */
    @ArchTest
    static final ArchRule puertosEnPaqueteDominioPuertos = classes()
            .that().resideInAPackage("..dominio.puertos..")
            .should().beInterfaces()
            .because("El paquete dominio.puertos debe contener solo interfaces de puerto");

    /**
     * El dominio NO debe importar clases de jakarta.persistence directamente
     * en el paquete de puertos (las interfaces de puerto deben ser puras).
     */
    @ArchTest
    static final ArchRule puertosNoDependenDeJpa = noClasses()
            .that().resideInAPackage("..dominio.puertos..")
            .should().dependOnClassesThat()
            .resideInAPackage("jakarta.persistence..")
            .because("Las interfaces de puerto deben ser independientes de JPA");
}
