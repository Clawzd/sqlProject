DROP PROCEDURE IF EXISTS GetHorsesByOwnerLastName;
DROP PROCEDURE IF EXISTS GetWinningTrainers;
DROP PROCEDURE IF EXISTS GetTrainerWinnings;
DROP PROCEDURE IF EXISTS GetTrackStatistics;
DROP PROCEDURE IF EXISTS AddRaceWithResults;
DROP PROCEDURE IF EXISTS DeleteOwner;
DROP PROCEDURE IF EXISTS MoveHorseStable;
DROP PROCEDURE IF EXISTS ApproveTrainer;


CREATE PROCEDURE GetHorsesByOwnerLastName(IN owner_last_name VARCHAR(15))
BEGIN
SELECT h.horseName, h.age, t.fname, t.lname
FROM Horse h
         JOIN Owns o ON h.horseId = o.horseId
         JOIN Owner ow ON o.ownerId = ow.ownerId
         JOIN Trainer t ON h.stableId = t.stableId
WHERE ow.lname = owner_last_name;
END;

CREATE PROCEDURE GetWinningTrainers()
BEGIN
SELECT t.fname, t.lname, h.horseName, r.raceName, rr.prize
FROM Trainer t
         JOIN Horse h ON t.stableId = h.stableId
         JOIN RaceResults rr ON h.horseId = rr.horseId
         JOIN Race r ON rr.raceId = r.raceId
WHERE rr.results = 'first';
END;

CREATE PROCEDURE GetTrainerWinnings()
BEGIN
SELECT t.fname, t.lname, SUM(rr.prize) as total_winnings
FROM Trainer t
         JOIN Horse h ON t.stableId = h.stableId
         JOIN RaceResults rr ON h.horseId = rr.horseId
GROUP BY t.trainerId, t.fname, t.lname
ORDER BY total_winnings DESC;
END;

CREATE PROCEDURE GetTrackStatistics()
BEGIN
SELECT r.trackName,
       COUNT(DISTINCT r.raceId) as race_count,
       COUNT(DISTINCT rr.horseId) as horse_count
FROM Race r
         JOIN RaceResults rr ON r.raceId = rr.raceId
GROUP BY r.trackName;
END;

CREATE PROCEDURE AddRaceWithResults(
    IN p_race_id VARCHAR(15),
    IN p_race_name VARCHAR(30),
    IN p_track_name VARCHAR(30),
    IN p_race_date DATE,
    IN p_race_time TIME,
    IN p_horse_id VARCHAR(15),
    IN p_results VARCHAR(15),
    IN p_prize DECIMAL(10, 2)
)
BEGIN
INSERT INTO Race (raceId, raceName, trackName, raceDate, raceTime)
VALUES (p_race_id, p_race_name, p_track_name, p_race_date, p_race_time);

INSERT INTO RaceResults (raceId, horseId, results, prize)
VALUES (p_race_id, p_horse_id, p_results, p_prize);
END;

CREATE PROCEDURE DeleteOwner(IN p_owner_id VARCHAR(15))
BEGIN
DELETE FROM Owns WHERE ownerId = p_owner_id;
DELETE FROM Owner WHERE ownerId = p_owner_id;
END;

CREATE PROCEDURE MoveHorseStable(
    IN p_horse_id VARCHAR(15),
    IN p_new_stable_id VARCHAR(15)
)
BEGIN
UPDATE Horse SET stableId = p_new_stable_id WHERE horseId = p_horse_id;
END;

CREATE PROCEDURE ApproveTrainer(
    IN p_trainer_id VARCHAR(15),
    IN p_last_name VARCHAR(30),
    IN p_first_name VARCHAR(30),
    IN p_stable_id VARCHAR(30)
)
BEGIN
INSERT INTO Trainer (trainerId, lname, fname, stableId)
VALUES (p_trainer_id, p_last_name, p_first_name, p_stable_id);
END;