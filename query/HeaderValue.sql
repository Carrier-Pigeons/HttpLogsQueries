WITH t AS (
SELECT 
count(*) AS valCount, 
SUM(CASE WHEN NOT proxy THEN 1 ELSE 0 END) AS notProxyCount,
SUM(CASE WHEN proxy THEN 1 ELSE 0 END) AS proxyCount,
    request, user_agent, header, headerValue 
    FROM header h1 
    group by request, user_agent, header, headerValue)

SELECT notProxyCount / valCount AS notProxyPercent, 
proxyCount / valCount AS proxyPercent,
t.* 
FROM t