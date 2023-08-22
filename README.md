# Simple CRUD Employee dengang Spring Boot 2.7.14

## Syarat
Syarat untuk mencoba aplikasi ini
1. Java versi 17.0
2. Maven minimum versi 3.9 
3. Postgresql

## Cara Install

1. Clone atau download project ini
2. Pada terminal jalankan perintah
   ```sh
   mvn install
   ```
3. Konfigurasi database, Buka application.properties src/main/resources/application.properties dan ubah url, username dan password yang sesuai
    ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/employee_db
    spring.datasource.username=postgres
    spring.datasource.password=qwerty123
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.hibernate.ddl-auto=update
    ```
4. Jalankan spring boot dengan menggunakan perintah 
   ```
   mvn spring-boot:run
   ```
