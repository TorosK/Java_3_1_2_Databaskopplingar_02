Console command to compile all java files in a directory:

javac *.java

For javadoc execution into 'doc' folder:

javadoc -d doc *.java

___

The Database:

https://people.dsv.su.se/phpmyadmin/index.php?db=db_24282270&table=guestbook

Login using credentials from:

https://people.dsv.su.se/...

___

run program by opening terminal in project directory and:

java GuestbookApp

___

sensetive variable values (Database credentials) are stored in: 
config.properties

___

To create a runnable .jar file:

jar cmf manifest.txt SQLGuestbook_Toros.jar *.class org com config.properties

___

To run .jar file:

java -jar SQLGuestbook_Toros.jar