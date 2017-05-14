-- Delete everything from previous run
USE HeroTrackerTest;
DELETE FROM OrganizationHero WHERE OrgHeroID > 0;
DELETE FROM HeroSighting WHERE HeroSightingID > 0;
DELETE FROM Sighting WHERE SightingID > 0;
DELETE FROM Hero WHERE HeroID > 0;
DELETE FROM Organization WHERE OrgID > 0;
DELETE FROM Location WHERE LocationID > 0;
DELETE FROM Address WHERE AddressID > 0;
DELETE FROM SuperPower WHERE SuperPowerID > 0;


-- Re-add test data
USE HeroTrackerTest;

-- Addresses
INSERT INTO `HeroTrackerTest`.`Address` (`AddressID`, `Street1`, `City`, `State`, `Zip`) VALUES ('1', '123 Street', 'Chicago', 'IL', '60602');
INSERT INTO `HeroTrackerTest`.`Address` (`AddressID`, `Street1`, `Street2`, `City`, `State`, `Zip`) VALUES ('2', '987 Road', '#123', 'New York', 'NY', '10001');
INSERT INTO `HeroTrackerTest`.`Address` (`AddressID`, `Street1`, `City`, `State`, `Zip`) VALUES ('3', 'OrganizationOnly', 'Atlanta', 'GA', '55767');
INSERT INTO `HeroTrackerTest`.`Address` (`AddressID`, `Street1`, `City`, `State`, `Zip`) VALUES ('4', 'LocationOnly', 'Chicago', 'IL', '60615');
INSERT INTO `HeroTrackerTest`.`Address` (`AddressID`, `Street1`, `Street2`, `City`, `State`, `Zip`) VALUES ('5', 'Orphan Address', 'Orphan Data', 'Topeka', 'KS', '33276');

-- Locations
INSERT INTO `HeroTrackerTest`.`Location` (`LocationID`, `AddressID`, `LocationName`, `LatitudeDegrees`, `LatitudeDirection`, `LongitudeDegrees`, `LongitudeDirection`) VALUES ('1', '1', 'Something Snappy', '123.45', 'N', '55.43', 'W');
INSERT INTO `HeroTrackerTest`.`Location` (`LocationID`, `AddressID`, `LocationName`, `Description`, `LatitudeDegrees`, `LatitudeDirection`, `LongitudeDegrees`, `LongitudeDirection`) VALUES ('2', '2', 'Name #2', 'DescriptionText', '111.11', 'S', '11.11', 'E');
INSERT INTO `HeroTrackerTest`.`Location` (`LocationID`, `AddressID`, `LocationName`, `LatitudeDegrees`, `LatitudeDirection`, `LongitudeDegrees`, `LongitudeDirection`) VALUES ('3', '4', 'Orphan Location', '33.33', 'S', '88.88', 'W');

-- Organizations
INSERT INTO `HeroTrackerTest`.`Organization` (`OrgID`, `OrgName`, `Description`, `HQAddressID`, `ContactName`, `ContactPhone`) VALUES ('1', 'Justice League', 'Good guys club', '1', 'Mario', '123-456-7890');
INSERT INTO `HeroTrackerTest`.`Organization` (`OrgID`, `OrgName`, `Description`, `HQAddressID`) VALUES ('2', 'League of Doom', 'Bad guys club', '2');
INSERT INTO `HeroTrackerTest`.`Organization` (`OrgID`, `OrgName`, `Description`, `HQAddressID`, `ContactName`) VALUES ('3', 'Avengers', 'Like Justice League but for Marvel', '1', 'Nick Fury');
INSERT INTO `HeroTrackerTest`.`Organization` (`OrgID`, `OrgName`, `Description`, `HQAddressID`) VALUES ('4', 'Orphan Organization', 'This can be deleted.', '3');

-- Super Powers
INSERT INTO `HeroTrackerTest`.`SuperPower` (`SuperPowerID`, `PowerName`) VALUES ('1', 'Super Strength');
INSERT INTO `HeroTrackerTest`.`SuperPower` (`SuperPowerID`, `PowerName`, `Description`) VALUES ('2', 'Super Speed I', 'Kinda fast');
INSERT INTO `HeroTrackerTest`.`SuperPower` (`SuperPowerID`, `PowerName`, `Description`) VALUES ('3', 'Super Speed II', 'like next level fast');
INSERT INTO `HeroTrackerTest`.`SuperPower` (`SuperPowerID`, `PowerName`, `Description`) VALUES ('4', 'Orphan Superpower', 'Not attached to any heroes, can be deleted.');

-- Heroes
INSERT INTO `HeroTrackerTest`.`Hero` (`HeroID`, `HeroName`, `SuperPowerID`, `Description`) VALUES ('1', 'Superman', '1', 'Classic');
INSERT INTO `HeroTrackerTest`.`Hero` (`HeroID`, `HeroName`, `SuperPowerID`) VALUES ('2', 'Wonder Woman', '1');
INSERT INTO `HeroTrackerTest`.`Hero` (`HeroID`, `HeroName`, `SuperPowerID`) VALUES ('3', 'Quicksilver', '2');
INSERT INTO `HeroTrackerTest`.`Hero` (`HeroID`, `HeroName`, `SuperPowerID`) VALUES ('4', 'The Flash', '3');
INSERT INTO `HeroTrackerTest`.`Hero` (`HeroID`, `HeroName`, `SuperPowerID`, `Description`) VALUES ('5', 'Orphan Hero', '3', 'This one can be safely deleted.');

-- Sightings
INSERT INTO `HeroTrackerTest`.`Sighting` (`SightingID`, `LocationID`, `SightingDate`, `SightingTime`) VALUES ('1', '1', '2017-03-22', '2017-03-22 01:02:03');
INSERT INTO `HeroTrackerTest`.`Sighting` (`SightingID`, `LocationID`, `SightingDate`) VALUES ('2', '1', '2016-12-12');
INSERT INTO `HeroTrackerTest`.`Sighting` (`SightingID`, `LocationID`, `SightingDate`) VALUES ('3', '2', '2017-03-22');

-- HeroSightings
INSERT INTO `HeroTrackerTest`.`HeroSighting` (`HeroSightingID`, `HeroID`, `SightingID`) VALUES ('1', '1', '1');
INSERT INTO `HeroTrackerTest`.`HeroSighting` (`HeroSightingID`, `HeroID`, `SightingID`) VALUES ('2', '4', '1');
INSERT INTO `HeroTrackerTest`.`HeroSighting` (`HeroSightingID`, `HeroID`, `SightingID`) VALUES ('3', '1', '2');
INSERT INTO `HeroTrackerTest`.`HeroSighting` (`HeroSightingID`, `HeroID`, `SightingID`) VALUES ('4', '2', '2');
INSERT INTO `HeroTrackerTest`.`HeroSighting` (`HeroSightingID`, `HeroID`, `SightingID`) VALUES ('5', '2', '1');
INSERT INTO `HeroTrackerTest`.`HeroSighting` (`HeroID`, `SightingID`) VALUES ('3', '3');

-- OrganizationHeroes
INSERT INTO `HeroTrackerTest`.`OrganizationHero` (`OrgHeroID`, `HeroID`, `OrgID`) VALUES ('1', '1', '1');
INSERT INTO `HeroTrackerTest`.`OrganizationHero` (`OrgHeroID`, `HeroID`, `OrgID`) VALUES ('2', '3', '3');
INSERT INTO `HeroTrackerTest`.`OrganizationHero` (`OrgHeroID`, `HeroID`, `OrgID`) VALUES ('3', '2', '1');
INSERT INTO `HeroTrackerTest`.`OrganizationHero` (`OrgHeroID`, `HeroID`, `OrgID`) VALUES ('4', '1', '3');