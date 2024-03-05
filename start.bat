@echo off
echo start Compiling...
cd client
DEL "*.class"
javac Game.java
java Game
echo end of bat!
