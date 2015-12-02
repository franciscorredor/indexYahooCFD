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


 
 


 




