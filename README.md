# Contact Manager – Backend

Microservicio REST para la gestión de contactos (crear, consultar, actualizar y eliminar), desarrollado con **Spring Boot 3** y **arquitectura hexagonal**, con persistencia en **PostgreSQL**.

## Tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje |
| Spring Boot | 3.3.13 | Framework base |
| Spring Data JPA | (Boot) | Persistencia |
| PostgreSQL | 17 | Base de datos |
| Spring Validation | (Boot) | Validación de requests |
| MapStruct | 1.5.3.Final | Mapeo entre capas |
| Lombok | 1.18.24 | Reducción de código repetitivo |
| springdoc-openapi | 2.7.0 | Documentación OpenAPI y Swagger UI |
| Spring Boot Actuator | (Boot) | Monitoreo (health) |
| Log4j 2 | (BOM 2.25.3) | Logging |
| JUnit 5 y Mockito | – | Pruebas unitarias |
| JaCoCo | 0.8.10 | Cobertura de pruebas |
| OWASP Dependency-Check | 11.1.0 | Análisis de vulnerabilidades |
| Maven | – | Build y dependencias |

## Requisitos previos

- **Java 17**
- **Maven 3.6.3 o superior**
- **PostgreSQL** en ejecución, con una base de datos creada para el servicio

## Base de datos

Script de creación de la tabla de contactos:

```sql
CREATE TABLE cnt_contact (
	contact_id serial4 NOT NULL,
	first_name varchar(150) NOT NULL,
	last_name varchar(150) NULL,
	phone varchar(30) NULL,
	email varchar(150) NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	CONSTRAINT cnt_contact_pkey PRIMARY KEY (contact_id),
	CONSTRAINT cnt_contact_email_key UNIQUE (email)
);
```

## Configuración

Define la conexión a la base de datos en `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/<nombre_bd>
    username: <usuario>
    password: <contraseña>
```

Se recomienda no versionar credenciales reales y suministrarlas mediante variables de entorno.

## Ejecución

Desde la raíz del proyecto:

```bash
mvn spring-boot:run
```

O generando y ejecutando el jar:

```bash
mvn clean package
java -jar target/contact-manager-backend-0.0.1-SNAPSHOT.jar
```

Por defecto el servicio queda disponible en <http://localhost:8277>.

## Documentación de la API

Con el servicio en ejecución:

| Recurso | URL |
|---|---|
| Swagger UI | `/swagger-ui.html` |
| Especificación OpenAPI | `/v3/api-docs` |
| Health check | `/actuator/health` |

## Endpoints

Recurso base: `/api/contacts`

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| `POST` | `/api/contacts` | Crea un contacto | `201`, `400`, `409` |
| `GET` | `/api/contacts` | Lista todos los contactos | `200` |
| `GET` | `/api/contacts/{id}` | Consulta un contacto por id | `200`, `404` |
| `PUT` | `/api/contacts/{id}` | Actualiza un contacto | `200`, `400`, `404`, `409` |
| `DELETE` | `/api/contacts/{id}` | Elimina un contacto | `204`, `404` |

El front consume estas rutas bajo el prefijo `/api/v1/contact-manager`, es decir, `/api/v1/contact-manager/api/contacts`.

### Significado de los códigos de respuesta

| Código | Significado |
|---|---|
| `200` | Operación exitosa |
| `201` | Contacto creado (incluye el header `Location`) |
| `204` | Contacto eliminado, sin cuerpo de respuesta |
| `400` | Datos de entrada inválidos |
| `404` | El contacto no existe |
| `409` | Ya existe un contacto con ese correo electrónico |

### Cuerpo de la petición (POST y PUT)

```json
{
  "firstName": "Ana",
  "lastName": "Pérez",
  "email": "ana.perez@ejemplo.com",
  "phone": "3001234567"
}
```

| Campo | Obligatorio | Validación |
|---|---|---|
| `firstName` | Sí | Máximo 150 caracteres |
| `lastName` | No | Máximo 150 caracteres |
| `email` | Sí | Formato de correo válido, máximo 150 caracteres |
| `phone` | No | Máximo 30 caracteres |

### Cuerpo de la respuesta

```json
{
  "id": 1,
  "firstName": "Ana",
  "lastName": "Pérez",
  "email": "ana.perez@ejemplo.com",
  "phone": "3001234567",
  "createdAt": "2026-09-21T16:20:54.536522",
  "updatedAt": "2026-09-21T16:20:54.536522"
}
```

## Reglas de negocio

- El correo electrónico es único. Se normaliza antes de guardarse (sin espacios y en minúsculas), por lo que `Ana@Ejemplo.com` y `ana@ejemplo.com` se consideran el mismo correo.
- Los espacios sobrantes de nombre, apellido y teléfono se eliminan. Un apellido o teléfono vacío se guarda como `null`.
- Al actualizar un contacto se conservan su `id` y su fecha de creación; `updatedAt` se actualiza automáticamente.
- Actualizar un contacto con el correo de otro contacto existente devuelve `409`.

## Arquitectura

El proyecto sigue una **arquitectura hexagonal** (puertos y adaptadores). Las dependencias apuntan siempre hacia el centro: la infraestructura conoce a la aplicación y al dominio, pero nunca al revés.

```
Cliente HTTP
    │
    ▼
Adaptador de entrada (ContactApi / ContactController)
    │  usa
    ▼
Puerto de entrada (ContactUseCase)
    ▲  implementa
Servicio de aplicación (ContactUseCaseImpl)
    │  usa
    ▼
Puerto de salida (ContactPort)
    ▲  implementa
Adaptador de persistencia (JPA)
    │
    ▼
PostgreSQL
```

| Capa | Contenido |
|---|---|
| **Dominio** | Modelo `Contact` y excepciones de negocio |
| **Aplicación** | Puertos de entrada y salida (`ContactUseCase`, `ContactPort`) y la implementación de los casos de uso |
| **Infraestructura – entrada** | Interfaz `ContactApi` con la documentación OpenAPI, controller REST, DTOs de request y response, y mapper |
| **Infraestructura – salida** | Adaptador de persistencia, entidad JPA, repositorio Spring Data y mapper de entidad |

Los mappers están hechos con MapStruct: uno convierte request → dominio y dominio → response, y otro convierte dominio ↔ entidad.

## Pruebas y cobertura

Ejecutar las pruebas unitarias:

```bash
mvn test
```

El reporte de cobertura de JaCoCo se genera en `target/site/jacoco/index.html`. Se excluyen del cálculo los mappers, DTOs, entidades, clientes Feign, el dominio y las clases `*Application`.

## Análisis de vulnerabilidades

El plugin OWASP Dependency-Check está configurado pero deshabilitado en el build normal (`skip=true`). Para un escaneo manual:

```bash
mvn dependency-check:check
```

En integración continua puede activarse cambiando `skip` a `false`; el build falla ante vulnerabilidades con CVSS 9 o superior.
