ALGORITHMS IMPLEMENTED: CAESAR, VIGENERE, SHA256

# BUILT USING JAVA JDK 25 USE THAT

## RUN GUI

```
$ java -jar path/Encryptor.jar
```

OPENING THE JAR WITH NO PARAMETERS OPENS THE GUI (> PRONOUNCED GUY) 

## RUN CLI:

```
$ java -jar path/Encryptor.jar [encryptionMethod] [inputFilePath] [outputFilePath(optional)] [mode(optional)] [key(optional]
```

MODE AND KEY ARE ONLY AVAILABLE FOR TWO WAY ALGORITHMS (CAESAR, VIGENERE).

CAESAR KEY MUST BE AN INTEGER

VIGENERE KEY CAN BE ANY CHARACTERS

### MODE OPTIONS:
**Encrypt**

  encrypt, en, e

**Decrypt**

  decrypt, de, d
