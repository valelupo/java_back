# Propuesta TP Java

## Grupo
### Integrantes
* 52109 - Lentino, Magali
* 52868 - Lupo, Valeria


## Nombre: GETEACHER
### Enunciado General
Se desarrollará un sistema en el que los estudiantes podrán encontrar profesores particulares de las materias y nivel que necesiten, ya sea en su ciudad o bien, que realicen consultas virtuales. Allí podrán encontrar información del contacto. Por otro lado, los profesores particulares podrán darse de alta en el sistema para así ser visualizados y conseguir estudiantes. 
<br>Además, los estudiantes podrán puntuar y dejar reseñas luego de realizada la consulta. Estos aparecerán en la información de cada profesor, para así ayudar a los próximos estudiantes a elegir su profesor particular ideal.


### Modelo DER
![DER](https://github.com/user-attachments/assets/744fdfa9-ba46-4001-9de2-64cbce9fff3f)



## Checklist 

### Regularidad

|Requerimiento|cant. mín. 1 o 2 integ|
|:-|:-|
|ABMC simple|- Alumno<br>- Materia|
|ABMC dependiente|- Profesor (depende de Materia y Nivel)|
|CU NO-ABMC|- Dejar reseñas a profesores|
|Listado simple|- Listado de Materias|
|Listado complejo|0|


### Aprobación Directa
|Requerimiento|cant. mín. 1 o 2 integ|
|:-|:-|
|ABMC|- Alumno<br>- Materia<br>- Nivel<br>- Profesor<br>- Localidad<br>- Provincia<br>- Material|
|CU<br>"Complejo"(nivel resumen)|- Publicar nuevo material|
|Listado complejo|- Listado de profesores ordenados por mejor valoración|
|Nivel de acceso|- Profesor<br>- Alumno|
|Manejo de errores|Obligatorio|
|Requerimiento extra<br>obligatorio (**)|0|
|Publicar el sitio|Obligatorio|

