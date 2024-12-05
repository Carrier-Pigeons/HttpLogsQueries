USAGE:
This project makes comparisons between proxy and non-proxy http requests in mysql.

Note that this only currently works for evilginx proxies. Every new proxy will need to be manually added to this project. We will need to determine how to parse the logs to find out if it is a proxy or not.

curl -O https://dev.mysql.com/downloads/connector/j/
sudo apt-get install ./(file downloaded)

In the terminal, set the USER and PASSWORD variables. Furthermore set this in the terminal startup file for persistennce when rebooting (~/.bashrc, or ~/.zshrc depending on terminal type).
export SQL_USERNAME=<USER>
export SQL_PASSWORD=<PASS>

To create the database and table for logging:
sudo mysql
source {path to CreateTable.sql};

javac InsertHeaders.java
java -cp .:/usr/share/java/mysql-connector-j-9.1.0.jar InsertHeaders

every time edit is made, will need to rerun javac to recompile

Currently this code does not log cookies, as the cookie values are too long for the headerValue limit of 255 charactes.

There will also be a unique constraint error on the primary key if the same logs are tried to be added to mysql more than once. This will prevent us from double-counting logs.

Use ./restart.sh to recompile the code and run it.
Use ./start.sh to run the code without recompiling it.




TODO:
Further filters on User-Agent will need, and on device type.