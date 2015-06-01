javac -cp ".:lib/*:lib/processing-core/*" Driver.java
java -cp ".:lib/*:lib/processing-core/*" -Djna.nosys=true Driver
