# Sistema de Gestión de Biblioteca "El Chipy"

Un proyecto de escritorio en Java Swing para la gestión integral de una biblioteca. Permite administrar el catálogo de artículos (libros y vinilos), gestionar a los socios y controlar el sistema de préstamos y devoluciones.

## 📚 Características Principales

Este sistema está compuesto por varios módulos de gestión:

* **Gestión de Libros:**
    * Agregar, actualizar y eliminar libros del catálogo.
    * Campos: Autor, Título, Categoría, Páginas, Año, Estante y Cantidad.
* **Gestión de Vinilos:**
    * Agregar, actualizar y eliminar vinilos de la colección.
    * Campos: Autor, Nombre (Álbum), Canciones, Año y Cantidad.
* **Gestión de Socios:**
    * Agregar, actualizar y eliminar socios de la biblioteca.
    * Campos: Nombre, DNI, Teléfono y Email.
* **Sistema de Préstamos:**
    * Crear nuevos préstamos asociando un socio con un libro y/o un vinilo.
    * Registrar devoluciones de préstamos.
    * **Control de Stock:** El stock de libros y vinilos se descuenta al prestar y se repone al devolver.
    * **Lógica de Negocio:** Un socio no puede tener más de 3 préstamos activos simultáneamente.
    * **Estados de Préstamo:** Los préstamos se marcan como 'activo', 'devuelto' o 'vencido' (se actualiza automáticamente si la fecha fin es anterior a la actual).
    * **Filtrado:** Permite filtrar libros y vinilos disponibles por categoría o autor al momento de crear un préstamo.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java
* **Interfaz Gráfica:** Java Swing
* **Base de Datos:** MySQL
* **Conector:** `mysql-connector-j`
* **Gestión de Dependencias:** Apache Maven
