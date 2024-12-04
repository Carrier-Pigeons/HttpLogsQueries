SELECT count(*), count(*) * 1.0 / 
    (SELECT count(*) 
     FROM header AS h2 
     WHERE h2.proxy = h1.proxy 
       AND h2.request = h1.request 
       AND h2.header = h1.header 
       AND h2.headerOrder = h1.headerOrder 
       AND h2.user_agent = h1.user_agent) AS header_value_ratio, proxy, request, header, headerOrder, user_agent, headerValue FROM header h1 group by proxy, request, header, headerOrder, user_agent, headerValue;
