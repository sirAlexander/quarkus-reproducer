-- column_exists
-- Returns true if the given column exists, false otherwise.
-- Usage: IF column_exists('my_table', 'my_column') THEN ... END IF;

DROP FUNCTION IF EXISTS column_exists;

DELIMITER $$
CREATE FUNCTION column_exists(
  _table_name  VARCHAR(100),
  _column_name VARCHAR(100)
)
  RETURNS BOOLEAN
READS SQL DATA
  BEGIN
    RETURN 0 < (SELECT COUNT(*)
                FROM `INFORMATION_SCHEMA`.`COLUMNS`
                WHERE `TABLE_SCHEMA` = SCHEMA()
                      AND `TABLE_NAME` = _table_name
                      AND `COLUMN_NAME` = _column_name);
  END $$
DELIMITER ;

-- drop_fk_if_exists
-- Idempotent, drops all the foreign keys where the given column is the "from" column.
-- Usage: CALL drop_fk_if_exists('my_table', 'my_column');

DROP PROCEDURE IF EXISTS drop_fk_if_exists;

DELIMITER $$
CREATE PROCEDURE drop_fk_if_exists(
  _table_name  VARCHAR(100),
  _column_name VARCHAR(100)
)
  BEGIN
    DECLARE _fk_name VARCHAR(100);
    DECLARE _fk_name_loop_done INT DEFAULT FALSE;
    DECLARE _fk_name_cursor CURSOR FOR SELECT `CONSTRAINT_NAME`
                                       FROM `INFORMATION_SCHEMA`.`KEY_COLUMN_USAGE`
                                       WHERE `REFERENCED_TABLE_SCHEMA` = SCHEMA()
                                             AND `TABLE_NAME` = _table_name
                                             AND `COLUMN_NAME` = _column_name;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET _fk_name_loop_done = TRUE;
    OPEN _fk_name_cursor;
    _fk_name_loop: LOOP
      FETCH _fk_name_cursor
      INTO _fk_name;
      IF _fk_name_loop_done
      THEN
        LEAVE _fk_name_loop;
      END IF;
      SET @_query = CONCAT('ALTER TABLE `', _table_name, '` DROP FOREIGN KEY `', _fk_name, '`');
      PREPARE _statement FROM @_query;
      EXECUTE _statement;
    END LOOP;
    CLOSE _fk_name_cursor;
  END $$
DELIMITER ;

-- drop_column_if_exists
-- Idempotent, drops a column, and possibly all the foreign keys where the given column is the "from" column.
-- Usage: CALL drop_column_if_exists('my_table', 'my_column');

DROP PROCEDURE IF EXISTS drop_column_if_exists;

DELIMITER $$
CREATE PROCEDURE drop_column_if_exists(
  _table_name  VARCHAR(100),
  _column_name VARCHAR(100)
)
  BEGIN
    IF column_exists(_table_name, _column_name)
    THEN
      CALL drop_fk_if_exists(_table_name, _column_name);
      SET @_query = CONCAT('ALTER TABLE `', _table_name, '` DROP COLUMN `', _column_name, '`');
      PREPARE _statement FROM @_query;
      EXECUTE _statement;
    END IF;
  END $$
DELIMITER ;

-- column_data_type_is
-- Returns true if the given column is of the specified type, false otherwise.
-- Usage: IF column_data_type_is('my_table', 'my_column', 'my_type') THEN ... END IF;

DROP FUNCTION IF EXISTS column_data_type_is;

DELIMITER $$
CREATE FUNCTION column_data_type_is(
  _table_name  VARCHAR(100),
  _column_name VARCHAR(100),
  _data_type VARCHAR(100)
)
  RETURNS BOOLEAN
READS SQL DATA
  BEGIN
    RETURN 0 < (SELECT COUNT(*)
                FROM `INFORMATION_SCHEMA`.`COLUMNS`
                WHERE `TABLE_SCHEMA` = SCHEMA()
                      AND `TABLE_NAME` = _table_name
                      AND `COLUMN_NAME` = _column_name
                      AND LOWER(`DATA_TYPE`) = LOWER(_data_type));
  END $$
DELIMITER ;

-- index_exists
-- Returns true if the index with given name exists, false otherwise.
-- Usage: IF index_exists('my_table', 'index_name') THEN ... END IF;

DROP FUNCTION IF EXISTS index_exists;

DELIMITER $$
CREATE FUNCTION index_exists(
  _table_name  VARCHAR(100),
  _index_name VARCHAR(100)
)
  RETURNS BOOLEAN
READS SQL DATA
  BEGIN
    RETURN 0 < (SELECT COUNT(*)
                FROM `INFORMATION_SCHEMA`.`STATISTICS`
                WHERE `TABLE_SCHEMA` = SCHEMA()
                      AND `TABLE_NAME` = _table_name
                      AND `INDEX_NAME` = _index_name);
  END $$
DELIMITER ;

-- truncate_table_if_exists
-- Idempotent, truncates the given table if it exists.
-- Usage: CALL truncate_table_if_exists('my_table');

DROP PROCEDURE IF EXISTS truncate_table_if_exists;

DELIMITER $$
CREATE PROCEDURE truncate_table_if_exists(
  _table_name  VARCHAR(100)
)
  BEGIN
    IF EXISTS(
        SELECT 1 FROM `INFORMATION_SCHEMA`.`TABLES`
        WHERE `TABLE_NAME` = _table_name
        AND `TABLE_SCHEMA` = SCHEMA()
    )
    THEN
        SET @_query = CONCAT('TRUNCATE `', _table_name, '`');
        PREPARE _statement FROM @_query;
        EXECUTE _statement;
        DEALLOCATE PREPARE _statement;
    END IF;
  END $$
DELIMITER ;


