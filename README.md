# Sistema de clases universitarias

Aplicación de consola para administrar profesores, estudiantes y clases universitarias mediante programación orientada a objetos.

## Funcionalidades

- Registrar profesores de tiempo completo y medio tiempo.
- Calcular el salario según el tipo de contrato.
- Registrar estudiantes y clases universitarias.
- Asociar profesores y estudiantes a las clases.
- Consultar las clases en las que participa un estudiante.

## Reglas de salario

- Tiempo completo: `salarioBase * (1 + 0.10 * aniosDeExperiencia)`.
- Medio tiempo: `salarioBase * horasActivasPorSemana`.

## Tecnologías

- Java 21
- Maven Wrapper
- IntelliJ IDEA
- Persistencia en memoria

## Requisitos

- JDK 21 configurado en `JAVA_HOME`.
- Git para el control de versiones.

## Ejecución

Desde Windows PowerShell:

```powershell
.\mvnw.cmd clean test
```

La clase de entrada es `org.universidad.app.Main`.

## Organización del código

- `app`: punto de entrada de la aplicación.
- `model`: entidades y reglas del dominio.
- `service`: operaciones del sistema.
- `ui`: lectura de datos y presentación por consola.
