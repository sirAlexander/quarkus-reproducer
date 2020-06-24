DROP PROCEDURE IF EXISTS analytics_session_id_unique_index;

DELIMITER $$
CREATE PROCEDURE analytics_session_id_unique_index()
BEGIN

    IF NOT index_exists('analytics_session', 'session_id_UNIQUE') THEN
        ALTER TABLE `analytics_session` ADD UNIQUE INDEX `session_id_UNIQUE` (`session_id`);
    END IF;

END $$

DELIMITER ;
CALL analytics_session_id_unique_index();

DROP PROCEDURE IF EXISTS analytics_session_id_unique_index;