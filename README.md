\# Procesador de Secuencias FASTA - Arquitectura Onion



\## Descripción del Proyecto

El sistema permite registrar y catalogar secuencias genómicas a partir de archivos con formato FASTA simplificado. 


Para garantizar la mantenibilidad, escalabilidad y la futura reutilización del motor de procesamiento en aplicaciones web o móviles, este proyecto se ha construido bajo el estricto cumplimiento de la \*\*Arquitectura Onion (Cebolla)\*\*.


El proyecto está dividido en las siguientes capas, respetando la regla de dependencia hacia el centro:



1\. \*\*Domain (`domain`) - El Núcleo:\*\*

\* Contiene la entidad de negocio `RegistroGenomico` pura, sin anotaciones de frameworks externos.

\* Define los "Puertos" a través de la interfaz `RegistroRepository`, estableciendo el contrato de lo que la aplicación necesita guardar sin importar la tecnología subyacente.



2\. \*\*Application (`application`) - Casos de Uso:\*\*

\* Contiene el servicio `ProcesadorFastaService`, encargado de orquestar el flujo de negocio.

\* Valida estrictamente el formato del archivo FASTA y ensambla las entidades. Solo depende del Dominio.



3\. \*\*Infrastructure (`infrastructure`) - Adaptadores Externos:\*\*

\* La clase `RegistroRepositoryMySQL` utiliza JDBC puro para comunicarse con la base de datos MySQL y recuperar las llaves autogeneradas (`Statement.RETURN\_GENERATED\_KEYS`).



4\. \*\*Presentation (`presentation`) - Interfaz de Usuario:\*\*

\* Desarrollada en Java Swing.

\* Interactúa con el usuario mediante un `JFileChooser` para restringir la entrada exclusivamente a archivos físicos `.fasta`.



\## Tecnologías Utilizadas

\* \*\*Lenguaje:\*\* Java (JDK 17 o superior recomendado)

\* \*\*Gestor de Dependencias:\*\* Maven

\* \*\*Base de Datos:\*\* MySQL 8.x

\* \*\*Driver JDBC:\*\* `mysql-connector-j` (v8.3.0)

\* \*\*Interfaz Gráfica:\*\* Java Swing nativo




