# WannaComics App

WannaComics App es una aplicación de Android que permite a los usuarios explorar una lista de cómics obtenida desde la API REST de Marvel. Los usuarios pueden ver los detalles de cada cómic, agregarlos a un carrito de compras y realizar una compra simulada (fake).

## Características

- **Explorar cómics**: Muestra una lista de cómics obtenidos de la API REST de Marvel.
- **Detalle del cómic**: Permite ver información detallada de cada cómic.
- **Carrito de compras**: Agregar cómics al carrito y simular una compra.
- **Persistencia local**: Los datos del carrito se almacenan localmente usando Room.
- **Interfaz moderna**: UI desarrollada con Jetpack Compose y Material 3.

## Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **API REST**: Ktor para consumir la API de Marvel
- **Inyección de dependencias**: Hilt
- **Almacenamiento local**: Room
- **Interfaz de usuario**: Jetpack Compose y Material 3
- **Gestión de imágenes**: Coil
- **Pruebas unitarias**: MockK y JUnit 4

## Arquitectura

- **MVVM (Model-View-ViewModel)**: Para la separación de la lógica de negocio y la UI.
- **MVI (Model-View-Intent)**: Para el manejo de eventos y estados en la UI.
- **Clean Architecture**: División clara en capas (Data, Domain, Presentation).
- **Modularización**: Código modularizado por funcionalidad y capas (Data, Domain, Presentation).
- **Inyección de dependencias**: Uso de Hilt para gestionar la inyección de dependencias.
- **State Hoisting**: Administración del estado de Compose de manera escalable.
- **Principios SOLID, KISS, DRY**: Seguir las mejores prácticas de desarrollo de software.

## Requisitos Previos

- **Android Studio** (4.2 o superior recomendado)
- **Kotlin** (1.5 o superior)
- **Gradle** (Versión 7.0 o superior)

## Configuración del Entorno

### Clonar el repositorio

Clona el repositorio a tu máquina local utilizando el siguiente comando:

```bash
git clone https://github.com/usuario/marvel-comics-app.git
```
## Configuración de Android Studio

	1.	Abre Android Studio.
	2.	Selecciona File > Open y navega hasta el directorio donde clonaste el repositorio.
	3.	Asegúrate de sincronizar las dependencias de Gradle. Si Android Studio no lo hace automáticamente, selecciona File > Sync Project with Gradle Files.
	4.	Configura el emulador o dispositivo físico con al menos API Level 21.

## Ejecutar las pruebas
Este proyecto incluye pruebas unitarias utilizando MockK y JUnit 4. Para ejecutar las pruebas:

1.	Haz clic derecho en el directorio test y selecciona Run Tests o ejecuta las pruebas directamente desde la terminal de Android Studio con:

```
./gradlew test
```

## Estructura del Proyecto

```
.
├── app/                        # Módulo de aplicación principal
├── network/                    # Módulo para el manejo de los request a la API REST
├── database/                   # Módulo para el manejo del almacenamiento local
├── core/                       # Módulo para el manejo de utilidades en comun
└── feature/                    # Módulo donde se encuentran todos los features
└───── detail/                  # Módulo para el feature del detalle del comic
└───────── data/                
└───────── domain/
└───────── presentation/
└───────── di/
└───── home/                    # Módulo para el feature del home de la aplicacion
└───────── data/
└───────── domain/
└───────── presentation/
└───────── di/
└───── shopping/                # Módulo para el feature del carrito de compras
└───────── data/
└───────── domain/
└───────── presentation/
└───────── di/


/data                           # Módulo para el manejo de los repositorios y los llamados a la api
/domain                         # Módulo para el manejo de la logica de negocio y los use case
/presentation                   # Módulo para el manejo de la UI (viewModels, Events y Status)
/di                             # Módulo para el manejo de la inyeccion de dependencias de cada modulo
```


### Herramientas Utilizadas

	•	Ktor: Para realizar llamadas a la API REST de Marvel.
	•	Hilt: Para la inyección de dependencias.
	•	Room: Para el almacenamiento local de los datos del carrito.
	•	Jetpack Compose y Material 3: Para la construcción de la interfaz gráfica.
	•	Coil: Para cargar y mostrar imágenes de los cómics.
	•	MockK y JUnit 4: Para pruebas unitarias.

### Principios de Diseño

	•	Clean Architecture: Separación de capas para mejorar la mantenibilidad y la escalabilidad.
	•	SOLID: Aplicación de principios para mantener un código robusto y flexible.
	•	KISS y DRY: Mantener el código simple y evitar la duplicación innecesaria.
	•	Modularización: División del código por características y capas, mejorando la escalabilidad del proyecto.

### Resumen del contenido:
- **Características** del proyecto y las tecnologías principales utilizadas.
- **Requisitos previos** y cómo configurar el entorno de desarrollo.
- **Estructura del proyecto** para que los desarrolladores comprendan la organización modular.
- **Principios de diseño** aplicados en el código (SOLID, KISS, DRY).
- **Instrucciones para ejecutar las pruebas**.

Esto proporciona una visión clara y precisa de cómo funciona el proyecto y cómo configurarlo correctamente.