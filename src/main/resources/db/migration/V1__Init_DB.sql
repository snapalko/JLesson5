-- Создание таблиц для 5-го задания
--SET search_path TO products;

CREATE TABLE tpp_ref_product_register_type (
	internal_id         SERIAL NOT NULL PRIMARY KEY,
	value               TEXT,
	register_type_name  TEXT,
	product_class_code  TEXT,
	account_type        TEXT
);
INSERT INTO tpp_ref_product_register_type (
        value, register_type_name, product_class_code, account_type)
    VALUES ('03.012.002_47533_ComSoLd', 'Хранение ДМ.', '03.012.002', 'Клиентский'),
        ('02.001.005_45343_CoDowFF', 'Серебро. Выкуп.', '02.001.005_45343', 'Клиентский');


CREATE TABLE tpp_ref_account_type (
	internal_id SERIAL NOT NULL PRIMARY KEY,
	value       TEXT
);
INSERT INTO tpp_ref_account_type (value)
    VALUES ('Клиентский'), ('Внутрибанковский');

CREATE TABLE tpp_ref_product_class (
	internal_id         SERIAL NOT NULL PRIMARY KEY,
	value               TEXT,
	gbl_code            TEXT,
	gbl_name            TEXT,
	product_row_code    TEXT,
	product_row_name    TEXT,
	subclass_code       TEXT,
	subclass_name       TEXT
);
INSERT INTO tpp_ref_product_class (
        value, gbl_code, gbl_name, product_row_code, product_row_name, subclass_code, subclass_name)
    VALUES ('03.012.002', '03', 'Розничный бизнес', '012', 'Драг. металлы', '002', 'Хранение'),
        ('02.001.005', '02', 'Розничный бизнес', '001', 'Сырье', '005', 'Продажа');

CREATE TABLE tpp_product (
	id                  SERIAL NOT NULL PRIMARY KEY,
	product_code_id     INT,
	client_id           INT,
	type                TEXT,
	number              TEXT,
	priority            BIGINT,
	date_of_conclusion  TIMESTAMP, --дата заключения
	start_date_time     TIMESTAMP,
	end_date_time       TIMESTAMP,
	days                INT,
	penalty_rate        NUMERIC(18, 2), --штрафная ставка
	nso                 NUMERIC(18, 2), --неснижаемый остаток
	threshold_amount    NUMERIC(18, 2), --пороговая сумма
	requisite_type      TEXT, --тип реквизита
	interest_rate_type  TEXT, --тип процентной ставки
	tax_rate            NUMERIC(18, 2), --ставка налога
	reason_close        TEXT,
	state               TEXT
);

CREATE TABLE tpp_product_register (
	id              SERIAL NOT NULL PRIMARY KEY,
	product_id      INT NOT NULL,
	type            TEXT,
	account_id      INT,
	currency_code   TEXT,
	state           TEXT,
	account_number  TEXT
);

ALTER TABLE tpp_product_register
	ADD CONSTRAINT fk_product_product_register FOREIGN KEY (product_id)
	REFERENCES tpp_product(id) ON DELETE CASCADE ON UPDATE CASCADE;


CREATE TABLE agreements (
	id                                      SERIAL NOT NULL PRIMARY KEY,
	product_id                              INT,
	generalagreementid                      INT,
	arrangementtype                         TEXT,
	shedulerjobid                           INT,
	number                                  TEXT NOT NULL,
	openingdate                             TIMESTAMP NOT NULL,
	closingdate                             TIMESTAMP,
	canceldate                              TIMESTAMP,
	validityduration                        INT,
	cancellationreason                      TEXT,
	status                                  TEXT,
	interestcalculationdate                 TIMESTAMP,
	interestrate                            NUMERIC(18, 2),
	coefficient                             NUMERIC(18, 2),
	coefficientaction                       TEXT,
	minimuminterestrate                     NUMERIC(18, 2),
	minimuminterestratecoefficient          TEXT,
	minimuminterestratecoefficientaction    TEXT,
	maximalnterestrate                      NUMERIC(18, 2),
	maximalnterestratecoefficient           NUMERIC(18, 2),
	maximalnterestratecoefficientaction     TEXT
);

ALTER TABLE agreements
	ADD CONSTRAINT fk_product_agreements FOREIGN KEY (product_id)
	REFERENCES tpp_product(id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE account_pool (
	id 					SERIAL NOT NULL PRIMARY KEY,
	branchCode			TEXT,
	currencyCode		TEXT,
	mdmCode				TEXT,
	priorityCode		TEXT,
	registryTypeCode	TEXT,
	accounts			TEXT[]
);

INSERT INTO account_pool (
		branchCode, currencyCode, mdmCode, priorityCode, registryTypeCode, accounts)
	VALUES ('0022', '800', '15', '00', '03.012.002_47533_ComSoLd', ARRAY['475335516415314841861', '4753321651354151', '4753352543276345'] ),
		('0021', '500', '13', '00', '02.001.005_45343_CoDowFF', ARRAY['453432352436453276', '45343221651354151', '4534352543276345']);
