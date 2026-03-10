# Conversor de Moneda con API – Challenge ONE Java Back End

Este repositorio contiene una aplicación de consola en Java que realiza **conversiones de moneda en tiempo real** utilizando una API externa de tipos de cambio, además de incluir un **conversor de temperatura**.  
El proyecto fue desarrollado como solución al **Challenge ONE – Back End Java: Conversor de Moneda** (Oracle + Alura).

---

## Funcionalidades principales

### 1. Conversor de moneda con tasas en tiempo real

La aplicación permite convertir montos entre distintas monedas utilizando datos obtenidos en vivo desde un servicio REST de tipos de cambio (ExchangeRate-API).

- Moneda base principal: **peso mexicano (MXN)**.
- Conversiones disponibles:
  - MXN → USD (dólar estadounidense)
  - MXN → EUR (euro)
  - MXN → GBP (libra esterlina)
  - MXN → JPY (yen japonés)
  - MXN → KRW (won surcoreano)
  - USD → MXN

Flujo de uso:

1. El usuario selecciona en el menú principal la opción **“Conversor de moneda (tasas en tiempo real)”**.
2. Se muestra un submenú con los pares de monedas disponibles.
3. El usuario elige la conversión deseada y se le solicita el **monto**.
4. El programa consulta la **API de tipos de cambio**, obtiene la tasa actual para el par de monedas seleccionado y calcula el resultado.
5. Se muestra el resultado en formato legible, por ejemplo:

   ```text
   Resultado: 100.00 MXN = 5.87 USD
Al final de cada operación se pregunta si se desea realizar otra conversión de moneda o volver al menú principal.

2. Conversor de temperatura
Además del conversor de moneda, el programa incluye un módulo sencillo de conversión de temperatura, cumpliendo el requisito del challenge de ofrecer otro tipo de conversión.

Conversiones disponibles:

Celsius → Fahrenheit

Fahrenheit → Celsius

Flujo de uso:

En el menú principal se selecciona “Conversor de temperatura”.

Se elige el sentido de la conversión (Celsius a Fahrenheit o viceversa).

Se ingresa el valor numérico.

El programa aplica la fórmula correspondiente y muestra el resultado.

Ejemplos:

°F = ( ° C × 9 / 5 ) +  32 °F=(°C×9/5)+32

°C = ( ° F − 32 ) × 5 / 9 °C=(°F−32)×5/9

3. Menú interactivo y navegación
La aplicación se ejecuta completamente en consola y ofrece un menú interactivo que permite ejecutar varias operaciones sin reiniciar el programa.

Menú principal:

1. Conversor de moneda (tasas en tiempo real)

2. Conversor de temperatura

0. Salir

Dentro del módulo de monedas:

Se ofrece un submenú con las distintas combinaciones de monedas.

Tras cada conversión, el usuario puede:

Hacer otra conversión de moneda.

Volver al menú principal.

Terminar el programa desde el menú principal seleccionando la opción 0.

Arquitectura del código
El proyecto está organizado siguiendo una separación básica de responsabilidades (modelo – servicio – vista):

1. Main
Punto de entrada de la aplicación.

Crea las instancias necesarias y arranca la interfaz de consola.

java
public class Main {
    public static void main(String[] args) {
        ExchangeRateService exchangeRateService = new ExchangeRateService();
        ConsoleView view = new ConsoleView(exchangeRateService);
        view.iniciar();
    }
}
2. ConsoleView
Responsabilidad: capa de presentación en consola.

Gestiona:

Menú principal.

Menú de conversión de moneda.

Menú de conversión de temperatura.

Lectura y validación básica de entradas del usuario (enteros y decimales).

Utiliza ExchangeRateService para obtener resultados de conversión de moneda.

Puntos clave:

Usa Scanner para leer desde System.in.

Maneja opciones incorrectas mostrando mensajes amigables.

Controla cuándo repetir menús y cuándo volver o salir.

3. ExchangeRateService
Responsabilidad: capa de servicio que se comunica con la API de tipos de cambio.

Funciones principales:

convertir(String from, String to, double amount): retorna el monto convertido.

obtenerTasa(String from, String to): obtiene la tasa de cambio entre dos monedas.

Internamente:

Realiza una petición HTTP GET a la API de ExchangeRate-API usando HttpClient.

Obtiene un JSON con las tasas de conversión.

Convierte el JSON a un objeto Java mediante Gson.

Extrae la tasa correspondiente al par de monedas solicitado.

Si la API devuelve un código distinto de 200 o no encuentra la tasa solicitada, lanza una excepción con un mensaje claro.

4. CurrencyRateResponse
Responsabilidad: modelo que representa la respuesta de la API.

Contiene:

base_code: moneda base de las tasas devueltas.

conversion_rates: mapa (Map<String, Double>) con las tasas de cambio para cada moneda.

Se usa únicamente para deserializar el JSON con Gson de forma tipada.

Tecnologías utilizadas
Java (versión 17+ / 25 LTS).

Maven como gestor de dependencias y build.

Gson (com.google.code.gson:gson:2.10.1) para trabajar con JSON.

HTTP Client incorporado en Java para consumir la API REST.

Git & GitHub para control de versiones y publicación del repositorio.

IntelliJ IDEA como entorno de desarrollo.

Configuración de la API de tipos de cambio
El servicio utiliza ExchangeRate-API para obtener tasas actualizadas.

Pasos generales:

Crear una cuenta gratuita en
https://www.exchangerate-api.com/.

Obtener la API key desde el panel de usuario.

En la clase ExchangeRateService, reemplazar el valor de:

java
private static final String API_KEY = "TU_API_KEY_AQUI";
por la key real.

La URL base utilizada es similar a:

text
https://v6.exchangerate-api.com/v6/TU_API_KEY/latest/USD
La moneda base se ajusta según el parámetro que se envía al servicio.

Cómo ejecutar el proyecto
Requisitos
JDK 17 o superior instalado.

Maven configurado (IntelliJ lo maneja automáticamente si abres el proyecto como Maven).

Conexión a internet para consultar la API de tipos de cambio.

Ejecución en IntelliJ
Abrir el proyecto como “Project from Existing Sources” seleccionando el pom.xml.

Esperar a que Maven descargue las dependencias.

Asegurarse de que la clase Main esté marcada como entry point.

Clic derecho sobre Main → Run 'Main.main()'.

Operar el programa desde la consola que se abre en IntelliJ.

Posibles mejoras
Añadir más monedas y permitir que el usuario elija cualquier par dinámicamente.

Manejar mejor los errores de red (reintentos, mensajes específicos según código HTTP).

Guardar un historial de conversiones realizadas.

Crear una interfaz gráfica (JavaFX/Swing) o exponer el conversor como una API REST con Spring Boot.

Autor
Aplicación desarrollada como parte del Challenge ONE – Back End Java.
GitHub: AeonBerserker
Repositorio del proyecto: https://github.com/AeonBerserker/conversor-moneda
