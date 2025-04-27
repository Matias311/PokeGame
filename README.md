# Estructura del Proyecto

- `App.java`: Punto de entrada de la aplicación.
- `gui/`: Todo lo relacionado con la interfaz gráfica. Aquí van `JFrame`, `JPanel`, `JDialog`, etc.
- `dao/`: Data Access Object. Contiene las clases que se encargan de la comunicación con la base de datos usando JDBC.
- `modelo/`: Clases de entidad o modelos, por ejemplo: `Usuario`, `Producto`, etc.
- `servicio/`: Lógica de negocio que conecta la GUI con el DAO.

Dao / data access information object / un objeto Pokemon 3 tres archivos 

Repository / es lo mismo pero mejor / interfas -> dao / servio  -> te obliga a seguir una logica

# Funcionamiento de la Conexión

1. Dentro de `src/main/resources/`, crear un archivo llamado `db.properties`.
2. Configurar las siguientes variables en `db.properties`:

    ```properties
    db.host=Nombre del host base de datos
    db.port=Puerto del sql server
    db.user=Usuario de sql server
    db.pass=Password de sql server
    db.basedatos=Nombre de la base de datos
    ```

3. Utilizar siempre la clase estática para la conexión a la base de datos.  
   Esto permite evitar múltiples instancias y seguir mejores prácticas.

4. HOLAAAAA