DROP DATABASE LostArk;
CREATE DATABASE LostArk;

USE LostArk;

CREATE TABLE Classes(
    class VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE [Servers](
    [server] VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE [Character](
    [name] VARCHAR(50) NOT NULL PRIMARY KEY,
    class VARCHAR(20) NOT NULL,
    [server] VARCHAR(20) NULL,
    reportCount INT NOT NULL DEFAULT(1),

    CONSTRAINT FK_CLASS FOREIGN KEY (class) REFERENCES Classes(class),
    CONSTRAINT FK_SERVER FOREIGN KEY ([server]) REFERENCES [Servers]([server])
);

INSERT INTO Classes VALUES
    ('BERSERKER'),
    ('PALADIN'),
    ('GUNLANCER'),
    ('DESTROYER'),
    ('SORCERESS'),
    ('BARD'),
    ('ARCANA'),
    ('SUMMONER'),
    ('SHADOWHUNTER'),
    ('DEATHBLADE'),
    ('REAPER'),
    ('STRIKER'),
    ('WARDANCER'),
    ('SCRAPPER'),
    ('SOULFIST'),
    ('GLAIVIER'),
    ('GUNSLINGER'),
    ('ARTILLERRIST'),
    ('DEADEYE'),
    ('SHARPSHOOTER');

INSERT INTO Servers VALUES
    ('RETHRAMIS'),
    ('MOONKEEP'),
    ('SHADESPIRE'),
    ('PETRANIA'),
    ('TORTOYK'),
    ('STONEHEARTH'),
    ('TRAGON'),
    ('PUNIKA')

GO
CREATE PROCEDURE SP_ADD_CHARACTER 
    @name VARCHAR(50),
    @class VARCHAR(20),
    @server VARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON

    IF EXISTS(SELECT 1 FROM [Character] WHERE [name] = @name)
    BEGIN
        DECLARE @reportCount INT
        SELECT @reportCount = reportCount FROM [Character] WHERE [name] = @name

        UPDATE [Character] SET [reportCount] = @reportCount
    END
    ELSE
    BEGIN
        INSERT INTO [Character] ([name], class, [server]) VALUES (@name, @class, @server)
    END
END