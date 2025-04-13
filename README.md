# TCS Technical Test - Backend Junior Developer

Este proyecto es una API RESTful desarrollada como parte de la prueba técnica para el puesto de **Backend Junior Developer** en **Tata Consultancy Services (TCS)**. La solución está construida con **Spring Boot**, usa una base de datos **PostgreSQL** y está completamente **contenedorizada con Docker** para facilitar su ejecución.

## 📦 Tecnologías utilizadas

- Java 17 (Spring Boot 3)
- PostgreSQL 13.1
- Spring Data JPA
- Spring Web
- Docker & Docker Compose
- Maven
- Lombok

## 🚀 Funcionalidades implementadas

### ✅ Funcionalidades obligatorias

- **F1:** CRUD de Cliente, Cuenta y Movimiento.
- **F2:** Manejo de saldos automáticos al registrar movimientos.
- **F3:** Validación de saldo insuficiente al realizar retiros.

### 🌟 Funcionalidades opcionales (adicionales)

- **F4:** Reporte de estado de cuenta por cliente y rango de fechas.
- **F7:** Despliegue de la solución en contenedores (Docker & Docker Compose).



## ⚙️ Cómo ejecutar el proyecto

### Requisitos previos

- Docker y Docker Compose instalados
- JDK 17+ instalado si deseas correrlo localmente fuera de contenedores

### 1. Clonar el repositorio

```bash
git clone https://github.com/Janka312/tcstest.git
cd tcstest
```

### 2. Construir la imagen y levantar los contenedores

```bash
docker-compose build
```
```bash
docker-compose up
```

## 🌐 Acceso al proyecto

Una vez levantado:

- La API estará disponible en: [http://localhost:8080](http://localhost:8080)

- Base de datos PostgreSQL (Dockerizada):
    - **Base de datos:** `db_postgres`
    - **Usuario:** `postgres`
    - **Contraseña:** `code`

- Se adjunta un archivo `.json` de Postman con las colecciones para probar todos los endpoints de la API.