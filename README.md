\# Procesador de Secuencias FASTA - Arquitectura Onion



\## Descripción del Proyecto

Esta es una herramienta de escritorio local desarrollada en Java para institutos de investigación bioinformática. El sistema permite registrar y catalogar secuencias genómicas a partir de archivos con formato FASTA simplificado. 



El núcleo de la aplicación extrae los metadatos del paciente (documento, nombre y apellido) junto con la secuencia genética, persiste la información en una base de datos relacional (MySQL) y retorna el identificador único (ID) generado para actualizar la interfaz de usuario de manera desglosada.



\## Diseño Arquitectónico

Para garantizar la mantenibilidad, escalabilidad y la futura reutilización del motor de procesamiento en aplicaciones web o móviles, este proyecto se ha construido bajo el estricto cumplimiento de la \*\*Arquitectura Onion (Cebolla)\*\*.



El proyecto está dividido en las siguientes capas, respetando la regla de dependencia hacia el centro:



1\. \*\*Domain (`domain`) - El Núcleo:\*\*

&nbsp;  \* Contiene la entidad de negocio `RegistroGenomico` pura, sin anotaciones de frameworks externos.

&nbsp;  \* Define los "Puertos" a través de la interfaz `RegistroRepository`, estableciendo el contrato de lo que la aplicación necesita guardar sin importar la tecnología subyacente.



2\. \*\*Application (`application`) - Casos de Uso:\*\*

&nbsp;  \* Contiene el servicio `ProcesadorFastaService`, encargado de orquestar el flujo de negocio.

&nbsp;  \* Valida estrictamente el formato del archivo FASTA y ensambla las entidades. Solo depende del Dominio.



3\. \*\*Infrastructure (`infrastructure`) - Adaptadores Externos:\*\*

&nbsp;  \* Implementa los contratos del Dominio. 

&nbsp;  \* La clase `RegistroRepositoryMySQL` utiliza JDBC puro para comunicarse con la base de datos MySQL y recuperar las llaves autogeneradas (`Statement.RETURN\_GENERATED\_KEYS`).



4\. \*\*Presentation (`presentation`) - Interfaz de Usuario:\*\*

&nbsp;  \* Desarrollada en Java Swing.

&nbsp;  \* Totalmente desacoplada de la lógica de extracción o validación. Interactúa con el usuario mediante un `JFileChooser` para restringir la entrada exclusivamente a archivos físicos `.fasta`.



\## Tecnologías Utilizadas

\* \*\*Lenguaje:\*\* Java (JDK 17 o superior recomendado)

\* \*\*Gestor de Dependencias:\*\* Maven

\* \*\*Base de Datos:\*\* MySQL 8.x

\* \*\*Driver JDBC:\*\* `mysql-connector-j` (v8.3.0)

\* \*\*Interfaz Gráfica:\*\* Java Swing nativo



\## Configuración y Despliegue



\### 1. Preparación de la Base de Datos

Ejecuta el siguiente script en tu servidor MySQL para crear el esquema necesario:



```sql

CREATE DATABASE instituto\_genomica;

USE instituto\_genomica;



CREATE TABLE secuencias\_pacientes (

&nbsp;   id INT AUTO\_INCREMENT PRIMARY KEY,

&nbsp;   documento VARCHAR(50) NOT NULL,

&nbsp;   nombre VARCHAR(100) NOT NULL,

&nbsp;   apellido VARCHAR(100) NOT NULL,

&nbsp;   secuencia TEXT NOT NULL

);

