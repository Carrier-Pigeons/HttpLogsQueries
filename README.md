USAGE:
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




TODO:
Further filters on User-Agent will need, and on device type.