# Webes asztalfoglalási rendszer útmutatója

## Alkalmazás használata interneten keresztül
A kész alkalmazás elérhető ezen a linken: https://asztalfoglalas.up.railway.app
#### *Az alkalmazás funkcióinak eléréséhez regisztráció szükséges, ez bármilyen kitalált adattal megtehető, mivel az email-cím valódiságát a rendszer nem vizsgálja.* 
## Alkalmazás használata lokálisan
### 1)	Lokális futtatáshoz szükséges szoftverek: 
  -	Java Development Kit (JDK) 21 vagy újabb verzió
  -	MySQL 8.4 adatbázis-szerver
  -	Git
### 2)	Project letöltése: git clone https://github.com/szbence03/TableReservation_thesis.git
### 3)	MySQL szerver elindítása, majd egy MySQL connection/profile létrehozása, amelyen belül az adatbázis kerül létrehozásra.
### 4)	Adatbázis létrehozása:
`CREATE DATABASE etterem CHARACTER SET utf8mb4 COLLATE utf8mb4_hungarian_ci;`
### 5)	Ezután a 3.3.4-es fejezetben bemutatott SQL utasítások segítségével létre kell hozni az adatbázis tábláit:
```sql 
CREATE TABLE `asztal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ferohely` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci

INSERT INTO `asztal` (`ferohely`) VALUES (2);
INSERT INTO `asztal` (`ferohely`) VALUES (2);
INSERT INTO `asztal` (`ferohely`) VALUES (2);
INSERT INTO `asztal` (`ferohely`) VALUES (4);
INSERT INTO `asztal` (`ferohely`) VALUES (4);
INSERT INTO `asztal` (`ferohely`) VALUES (4);
INSERT INTO `asztal` (`ferohely`) VALUES (6);
INSERT INTO `asztal` (`ferohely`) VALUES (6);
INSERT INTO `asztal` (`ferohely`) VALUES (8);
INSERT INTO `asztal` (`ferohely`) VALUES (8);

CREATE TABLE `felhasznalo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `keresztnev` varchar(45) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `vezeteknev` varchar(45) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `email` varchar(45) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `jelszo` varchar(72) COLLATE utf8mb4_hungarian_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_idx` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci


CREATE TABLE `foglalas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `felhasznalo_id` int NOT NULL,
  `asztal_id` int NOT NULL,
  `vendegek` int NOT NULL,
  `mettol` datetime NOT NULL,
  `meddig` datetime NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `felhasznalo_id_idx` (`felhasznalo_id`),
  INDEX `asztalId_idx` (`asztal_id`),
  INDEX `mettol_meddig_idx` (`mettol`, `meddig`),
  CONSTRAINT `asztalId` FOREIGN KEY (`asztal_id`) REFERENCES `asztal` (`id`),
  CONSTRAINT `felhasznaloId` FOREIGN KEY (`felhasznalo_id`) REFERENCES `felhasznalo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci

```
### 6)	Adatbázis kapcsolat konfigurálása:
A **src/main/resources/application.properties** fájl tartalma nézzen ki a következőképpen, a *username* és *password* értékeket pedig a lokális MySQL beállításoknak megfelelően kell megadni:
```properties 
spring.application.name=asztalfoglalas
spring.datasource.url=jdbc:mysql://localhost:3306/etterem 
spring.datasource.username=felhasznaloneved
spring.datasource.password= jelszavad
spring.jpa.hibernate.ddl-auto=update
```
### 7)	Az alkalmazás elindítása
Az alkalmazás kétféleképpen indítható el a projekt gyökérkönyvtárából:
Parancssorból a következő parancs futtatásával: `mvnw spring-boot:run` 
vagy IDE-ből az AsztalfoglalasApplication osztály futtatásával.
#### Fontos, hogy az alkalmazás futása közben a MySQL szerver is fusson!
### 8)	Alkalmazás elérése sikeres elindítás után: http://localhost:8080/
