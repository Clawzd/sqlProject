DROP TABLE IF EXISTS old_horse_info;
DROP TRIGGER IF EXISTS BeforeHorseDelete


CREATE TABLE old_horse_info (
                                horseId VARCHAR(15),
                                horseName VARCHAR(15),
                                age INT,
                                gender CHAR,
                                registration INT,
                                stableId VARCHAR(30),
                                deleted_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TRIGGER BeforeHorseDelete
    BEFORE DELETE ON Horse
    FOR EACH ROW
    INSERT INTO old_horse_info
    (horseId, horseName, age, gender, registration, stableId)
    VALUES
        (OLD.horseId, OLD.horseName, OLD.age, OLD.gender, OLD.registration, OLD.stableId);