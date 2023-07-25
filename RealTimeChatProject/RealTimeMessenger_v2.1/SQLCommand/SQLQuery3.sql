/*List<Room> findByUserId(int userId);*/

SELECT r.[room_id]
     , [room_name]
     , [room_type]
     , [latest_update_date]
     , [user_1]
     , [user_2]
     , [room_image]
FROM [RealTimeChatVer2].[dbo].[room] r
    JOIN user_room ur
ON ur.room_id = r.room_id
WHERE ur.[user_id] = 1


/*select List<Integer> findRelatedUsers(int userId);*/

SELECT *
FROM [room] r
    JOIN [user_room] ur
On r.room_id = ur.room_id


SELECT r.room_id
FROM [room] r
    JOIN [user_room] ur
On r.room_id = ur.room_id
WHERE ur.[user_id] = 1


SELECT DISTINCT [user_id]
FROM (
    SELECT ur.[user_id] FROM [room] r
    JOIN [user_room] ur
    On r.room_id = ur.room_id
    WHERE r.room_id IN (
    SELECT r.room_id FROM [room] r
    JOIN [user_room] ur
    On r.room_id = ur.room_id
    WHERE ur.[user_id] = 1)
    ) as us
WHERE [user_id] != 1

/* Get List<UserRoom> getUserRoomByRoomId(int roomId) */
SELECT [id]
FROM [RealTimeChatVer2].[dbo].[user_room]
WHERE [room_id] = 1


SELECT [message_id]
        , [message]
        , [created_date_time]
        , [room_id]
        , [user_id]
FROM [dbo].[message]
WHERE [room_id] = 1
ORDER BY created_date_time DESC

SELECT *
FROM (SELECT [message_id]
              , [message]
              , [created_date_time]
              , [room_id]
              , [user_id]
      FROM [dbo].[message]
      WHERE [room_id] = 1 AND created_date_time < '2023-06-27 10:20:00'
      ORDER BY created_date_time DESC
      OFFSET 0 ROW FETCH FIRST 5 ROW ONLY) AS [d]
ORDER BY [d].[created_date_time]

SELECT *
FROM (SELECT [message_id]
              , [message]
              , [created_date_time]
              , [room_id]
              , [user_id]
      FROM [dbo].[message]
      WHERE [room_id] = 1 AND created_date_time < '2023-06-27 10:20:00'
      ORDER BY created_date_time DESC
      OFFSET 5 ROW FETCH FIRST 5 ROW ONLY) AS [d]
ORDER BY [d].[created_date_time]

SELECT *
FROM (SELECT [message_id]
              , [message]
              , [created_date_time]
              , [room_id]
              , [user_id]
      FROM [dbo].[message]
      WHERE [room_id] = 1 AND created_date_time < '2023-06-27 10:20:00'
      ORDER BY created_date_time DESC
      OFFSET 10 ROW FETCH FIRST 5 ROW ONLY) AS [d]
ORDER BY [d].[created_date_time]

SELECT *
FROM (SELECT [message_id]
              , [message]
              , [created_date_time]
              , [room_id]
              , [user_id]
      FROM [dbo].[message]
      WHERE [room_id] = 1 AND created_date_time < '2023-06-27 10:20:00'
      ORDER BY created_date_time DESC
      OFFSET 15 ROW FETCH FIRST 5 ROW ONLY) AS [d]
ORDER BY [d].[created_date_time]

SELECT *
FROM (SELECT [message_id]
              , [message]
              , [created_date_time]
              , [room_id]
              , [user_id]
      FROM [dbo].[message]
      WHERE [room_id] = 1 AND created_date_time < '2023-06-27 10:20:00'
      ORDER BY created_date_time DESC
      OFFSET 20 ROW FETCH FIRST 5 ROW ONLY) AS [d]
ORDER BY [d].[created_date_time]

SELECT *
FROM (SELECT [message_id]
              , [message]
              , [created_date_time]
              , [room_id]
              , [user_id]
      FROM [dbo].[message]
      WHERE [room_id] = 1 AND created_date_time < '2023-06-27 10:20:00'
      ORDER BY created_date_time DESC
      OFFSET 25 ROW FETCH FIRST 5 ROW ONLY) AS [d]
ORDER BY [d].[created_date_time]

SELECT *
FROM (SELECT [message_id]
              , [message]
              , [created_date_time]
              , [room_id]
              , [user_id]
      FROM [dbo].[message]
      WHERE [room_id] = 1 AND created_date_time < '2023-06-27 10:20:00'
      ORDER BY created_date_time DESC
      OFFSET 30 ROW FETCH FIRST 5 ROW ONLY) AS [d]
ORDER BY [d].[created_date_time]