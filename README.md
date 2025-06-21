## Estructura del Proyecto

- **`App.java`**:  
  Punto de entrada de la aplicación. Desde aquí se inicializa y se ejecuta todo el flujo principal del programa.

- **`gui/`**:  
  Todo lo relacionado con la interfaz gráfica de usuario (GUI). Aquí se encuentran las ventanas (`JFrame`), paneles (`JPanel`), diálogos (`JDialog`) y otros componentes visuales.

- **`repository/`**:  
  Contiene las **interfaces** que definen las operaciones de acceso a datos (por ejemplo, `PokemonRepository`).

- **`repositoryImpl/`**:  
  Contiene las **implementaciones concretas** de las interfaces de `repository/`. Aquí se encuentra el código JDBC que conecta con la base de datos.

- **`modelo/`**:  
  Contiene las clases de entidad o modelo. Representan objetos del mundo real o conceptos del sistema, como `Pokemon`, `Usuario`, `Producto`, etc.

- **`servicio/`**:  
  Contiene la lógica de negocio de la aplicación. Se encarga de coordinar operaciones entre la GUI y los Repositories, aplicando reglas de negocio cuando sea necesario.

## ¿Cómo se trabaja con Repository?

El patrón Repository separa la lógica de negocio de la lógica de acceso a datos.

- La **GUI** no se conecta directamente a la base de datos, sino que se comunica con los **Servicios**.
- Los **Servicios** usan las **interfaces Repository** para acceder a los datos de forma desacoplada.
- Las **Implementaciones de los Repositories** (`repositoryImpl/`) contienen el código real para interactuar con la base de datos.
- Si algún día cambiamos la base de datos o la forma de acceso, solo debemos modificar las clases dentro de `repositoryImpl/`, sin afectar la lógica de servicios o la GUI.

### Flujo simplificado:
```bash
GUI → Servicio → Repository (Interface) → RepositoryImpl (JDBC) → Base de Datos
```

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


### Configuracion de formateo 
1. Asegurate de tener el archivo `.java-format`.
2. instalar plugin google-java-format
3. Settings > Tools > Google Java Format
4. Seleccionar "use project specific settings"
5. apuntar al archivo `.java-format`. 
6. Activar format on save (opcional): file > settings > Tools > actions on save > reformat code 

### Configuracion de linter
1. instala plugin Checkstyle-IDEA
2. asegurate de tener el archivo checkstyle.xml
3. file > tools > checkstyle > click en add > use local checkstyle file > selecciona el archivo checkstyle.xml
4. marcar la config como activa
5. Opcional: En "scan scope", selecciona only java sources 
6. aplica los cambio


### Creacion de nueva tabla
Mensaje
```sql
        IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Mensaje')
        BEGIN
            CREATE TABLE Mensaje (
                id int IDENTITY(1,1) PRIMARY KEY,
                tipo_mensaje varchar(20) DEFAULT 'texto',
                contenido_mensaje NVARCHAR(MAX),
                fecha_hora DATETIME DEFAULT GETDATE(),
                id_cliente int foreign key (id_cliente) references Cliente(id)
            );
        END
```
