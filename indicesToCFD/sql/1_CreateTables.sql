CREATE SCHEMA `indexyahoocfd` ;

-- Tabla que me indica la industria a la que pertence la compañia


-- Tabla que me indica la bolsa a la que pertenece la compañia, uk, francia, usa
		--LSE: London Stock Exchange

-- Pagina web de la compañia.


-- Nombre de compañias en la bolsa
CREATE TABLE `indexyahoocfd`.`iyc_stock_companies` (
  -- identificador unico de la compañia
  `SCN_CODIGO` INT NOT NULL AUTO_INCREMENT,
  -- Nombre de la compañia
  `SCN_NAME` VARCHAR(500) NOT NULL,
  -- Pagina web de la compañia, puede que tenga diferentes, tenerlo en cuenta en el diseño
  --`SCN_WEB_PAGE
  -- Sigla en la bolsa, puede que tenga otra sigla en una bolsa diferentes uk, tenerlo en cuenta en el dieño
  --`SCN_SIGLA
  PRIMARY KEY (`SCN_CODIGO`),
  UNIQUE INDEX `SCN_NAME_UNIQUE` (`SCN_NAME` ASC));

-- indice yahoo de las compañias 
CREATE TABLE `indexyahoocfd`.`iyc_stack_company_index` (
--identificador unico de la tabla 
`SCI_CODIGO` INT NOT NULL AUTO_INCREMENT,
--identificador que relaciona con el nombre de la compañia 
`SCN_CODIGO` INT NOT NULL,
 --Link del indicador
`SCI_URL_INDEX` VARCHAR(5000) NOT NULL,
-- Fecha creación del indice 
`SCI_FECHA_CREACION` DATE NULL,
  PRIMARY KEY (`SCI_CODIGO`));

--DROP TABLE  `indexyahoocfd`.`iyc_quote_company_history`  
 -- almacena el historico de las quotes de la diferentes compañias 
CREATE TABLE `indexyahoocfd`.`iyc_quote_company_history` (
--identificador unico de la tabla 
`QCH_CODIGO` INT NOT NULL AUTO_INCREMENT,
--identificador que relaciona con el nombre de la compañia 
`SCN_CODIGO` INT NOT NULL, 
-- Fecha creación del indice 
`QHC_FECHA_CREACION` DATE NOT NULL,
`name` VARCHAR(1000),
`symbol` VARCHAR(1000),
`ts` VARCHAR(1000),
`type` VARCHAR(1000),
`utctime` VARCHAR(1000),
`volume` VARCHAR(1000),
--Se modifica nombre de tabla porque cuando se inserta, genera error de syntaxis
`syntaxis_change` VARCHAR(1000),
`chg_percent` VARCHAR(1000),
`day_high` VARCHAR(1000),
`day_low` VARCHAR(1000),
`issuer_name` VARCHAR(1000),
`issuer_name_lang` VARCHAR(1000),
`year_high` VARCHAR(1000),
`year_low` VARCHAR(1000),
PRIMARY KEY (`QCH_CODIGO`));

 
 


 




