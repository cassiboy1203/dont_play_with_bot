DROP DATABASE LostArk;
CREATE DATABASE LostArk;

USE LostArk;

CREATE TABLE Classes(
    class VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE [Servers](
    [server] VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE Stronghold(
    stronghold VARCHAR(50) NOT NULL PRIMARY KEY
)

CREATE TABLE [Character](
    [name] VARCHAR(50) NOT NULL PRIMARY KEY,
    class VARCHAR(20) NOT NULL,
    [server] VARCHAR(20) NOT NULL,
    stronghold VARCHAR(50) NOT NULL,
    reportCount INT NOT NULL DEFAULT(1),

    CONSTRAINT FK_CLASS FOREIGN KEY (class) REFERENCES Classes(class),
    CONSTRAINT FK_SERVER FOREIGN KEY ([server]) REFERENCES [Servers]([server]),
    CONSTRAINT FK_STRONGHOLD FOREIGN KEY (stronghold) REFERENCES Stronghold(stronghold)
);

CREATE TABLE Reasons(
    [character] VARCHAR(50),
    reason VARCHAR(255),

    CONSTRAINT PK_REASONS PRIMARY KEY ([character], reason),
    CONSTRAINT FK_CHARACTER FOREIGN KEY ([character]) REFERENCES [Character]([name])
)

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
    ('TORTOYK'),
    ('PUNIKA')

GO
CREATE OR ALTER PROCEDURE SP_ADD_CHARACTER 
    @name       VARCHAR(50),
    @class      VARCHAR(20),
    @server     VARCHAR(20),
    @stronghold VARCHAR(50),
    @reason     VARCHAR(255)
AS
BEGIN
    DECLARE @savepoint varchar(128)= CAST(OBJECT_NAME(@@PROCID) AS varchar(125)) + CAST(@@NESTLEVEL AS varchar(3));
	DECLARE @startTrancount int= @@TRANCOUNT;

    SET NOCOUNT ON

    BEGIN TRY
        BEGIN TRAN
        SAVE TRAN @savepoint

        IF EXISTS(SELECT 1 FROM [Character] WHERE [name] = @name)
        BEGIN
            DECLARE @reportCount INT
            SELECT @reportCount = reportCount FROM [Character] WHERE [name] = @name

            UPDATE [Character] SET [reportCount] = @reportCount + 1 WHERE [name] = @name
        END
        ELSE IF EXISTS(SELECT 1 FROM Stronghold WHERE stronghold = @stronghold)
        BEGIN
            INSERT INTO [Character] ([name], class, [server], stronghold) VALUES (@name, @class, @server, @stronghold)
        END
        ELSE
        BEGIN
            INSERT INTO Stronghold(stronghold) VALUES(@stronghold)

            INSERT INTO [Character] ([name], class, [server], stronghold) VALUES (@name, @class, @server, @stronghold)
        END
        IF @reason IS NOT NULL
        BEGIN
            IF NOT EXISTS(SELECT 1 FROM Reasons WHERE [character] = @name AND reason = @reason)
            BEGIN
                INSERT INTO Reasons([character], reason) VALUES(@name, @reason)
            END
        END

        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF XACT_STATE() = -1 AND @startTrancount = 0
        BEGIN
            ROLLBACK TRANSACTION
        END
        ELSE
        BEGIN
            IF XACT_STATE() = 1
            BEGIN
                ROLLBACK TRANSACTION @savepoint;
                COMMIT TRANSACTION;
            END;
        END;
        DECLARE @errormessage varchar(2000);
        SET @errormessage = 'Error occured in sproc ''' + OBJECT_NAME(@@procid) + '''. Original message: ''' + ERROR_MESSAGE() + '''';
        THROW 50000, @errormessage, 1;
    END CATCH
END
GO

CREATE PROCEDURE SP_CHECK_CHARACTER
    @name VARCHAR(50),
    @isInList BIT OUTPUT
AS
BEGIN
    SET NOCOUNT ON
    IF EXISTS(SELECT 1 FROM [Character] WHERE [name] = @name)
    BEGIN
        SET @isInList = 1
        RETURN
    END
    SET @isInList = 0
    RETURN
END
GO

CREATE OR ALTER PROCEDURE SP_GET_USERS_IN_LIST
    @class VARCHAR(20) = NULL,
    @server VARCHAR(20) = NULL,
    @stronghold VARCHAR(50) = NULL
AS
BEGIN
    SELECT *
    INTO #characters
    FROM [Character];

    IF @class IS NOT NULL
    BEGIN
        SELECT *
        INTO #classFilter
        FROM (
            SELECT * FROM #characters WHERE class = @class
        ) cf;

        DELETE FROM #characters

        INSERT INTO #characters SELECT * FROM #classFilter

        DROP TABLE #classFilter
    END
    IF @server IS NOT NULL
    BEGIN
        SELECT *
        INTO #serverFilter
        FROM (
            SELECT * FROM #characters WHERE [server] = @server
        ) cf;

        DELETE FROM #characters;

        INSERT INTO #characters SELECT * FROM #serverFilter

        DROP TABLE #serverFilter
    END
    IF @stronghold IS NOT NULL
    BEGIN
        SELECT *
        INTO #strongholdFilter
        FROM (
            SELECT * FROM #characters WHERE stronghold = @stronghold
        ) cf;

        DELETE FROM #characters;

        INSERT INTO #characters SELECT * FROM #strongholdFilter

        DROP TABLE #strongholdFilter
    END

    SELECT * FROM #characters

    DROP TABLE #characters
END
GO

CREATE PROCEDURE SP_GET_REASONS
    @character VARCHAR(50)
AS
BEGIN
    SELECT * FROM Reasons WHERE [character] = @character
END