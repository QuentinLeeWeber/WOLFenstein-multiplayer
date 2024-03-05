@echo off
echo start Compiling...
cd client
DEL "*.class"
javac Game.java
java -Xmx2048m Game
echo end of bat!
