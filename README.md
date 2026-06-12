# Sistema Saber Pro - UTS

Proyecto académico para la gestión y seguimiento de resultados de las pruebas Saber Pro.

## Ajuste visual agregado

Se aplicó una capa de diseño con identidad institucional inspirada en las Unidades Tecnológicas de Santander:

- Paleta principal en verde institucional.
- Login más moderno con identificación UTS.
- Menú lateral con degradado verde.
- Tarjetas con sombras suaves y animación al pasar el cursor.
- Botones, tablas y formularios ajustados al mismo estilo.
- Diseño responsivo básico para pantallas pequeñas.

## Tecnologías

- Java
- Spring Boot
- Thymeleaf
- Spring Security
- Bootstrap
- MySQL
- Docker
- Render

## Nota

La lógica del proyecto no fue modificada. Los cambios realizados son principalmente visuales para mejorar la presentación del parcial.

## Ajuste funcional adicional

- El estudiante puede cargar su recibo de pago Saber Pro desde el módulo **Cargar Comprobante**.
- El comprobante puede ser PDF o imagen.
- Coordinación puede revisar el recibo desde la lista de estudiantes.
- Coordinación solo puede aprobar al estudiante para Saber Pro si ya existe un recibo cargado.
- Si el estudiante vuelve a subir un recibo, la aprobación queda pendiente nuevamente para revisión.
