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

- JDK 21 o superior. Puede seleccionarse como SDK del proyecto en IntelliJ IDEA.
- Git para el control de versiones, si el proyecto se va a clonar desde GitHub.
- Maven no es necesario: el proyecto incluye Maven Wrapper.

La ejecución desde IntelliJ IDEA utiliza el JDK seleccionado para el proyecto y no requiere
que Java esté configurado globalmente en el `PATH`. Para ejecutar Maven desde una terminal
externa, el JDK debe estar disponible mediante `JAVA_HOME` o `PATH`.

Para verificar la versión de Java:

```powershell
java -version
```

Maven valida automáticamente que el JDK sea 21 o superior antes de compilar. Si se detecta
una versión anterior, la ejecución se detiene con un mensaje indicando cómo seleccionar o
configurar el JDK correcto.

## Ejecución de la aplicación

La clase de entrada es `org.universidad.app.Main`.

### Desde Windows PowerShell

Ejecutar desde la carpeta raíz del proyecto:

```powershell
.\mvnw.cmd compile
java -cp target\classes org.universidad.app.Main
```

### Desde macOS o Linux

Ejecutar desde la carpeta raíz del proyecto:

```bash
./mvnw compile
java -cp target/classes org.universidad.app.Main
```

### Desde IntelliJ IDEA

1. Abrir la carpeta raíz del proyecto.
2. Confirmar que el proyecto utiliza un JDK 21.
3. Abrir `src/main/java/org/universidad/app/Main.java`.
4. Ejecutar el método `main` mediante el botón verde de IntelliJ IDEA.

La aplicación mostrará el menú principal y esperará la selección de una opción.

## Organización del código

- `app`: punto de entrada de la aplicación.
- `model`: entidades y reglas del dominio.
- `service`: operaciones del sistema.
- `ui`: lectura de datos y presentación por consola.

## Opciones de la aplicación

1. Listar profesores con información contractual y salario calculado.
2. Listar clases y consultar el detalle de una clase.
3. Registrar un estudiante y asociarlo a una clase existente.
4. Registrar una clase con un profesor y estudiantes existentes.
5. Consultar las clases asociadas a un estudiante seleccionándolo de una lista numerada.
6. Salir de la aplicación.

## Validaciones de registro

- El ID de un nuevo estudiante se genera automáticamente a partir del mayor ID existente.
- El nombre del estudiante y de la clase debe contener al menos una letra.
- La edad debe ser un número entero entre 15 y 120 años.
- El aula se selecciona desde una lista de aulas disponibles; no se solicita manualmente.
- Al registrar una clase, se pueden seleccionar varios estudiantes y se utiliza `0` para finalizar.
- Las listas de profesores, clases y estudiantes presentan sus columnas alineadas para facilitar la lectura.

## Pruebas

Para compilar el proyecto y ejecutar las pruebas automatizadas:

### Windows PowerShell

```powershell
.\mvnw.cmd clean test
```

### macOS o Linux

```bash
./mvnw clean test
```

Este comando ejecuta las pruebas, pero no inicia el menú de la aplicación.

## Diagrama de diseño

El diseño de clases está disponible en [docs/diagrama.md](docs/diagrama.md).
