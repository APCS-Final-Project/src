# src
Source Code for <Insert Name> Project

# Introduction
In this project, we create a QR code generator and scanner with the help of
[zebra crossing] (https://github.com/zxing/zxing) and [NyARToolkit]
(http://nyatla.jp/nyartoolkit/wp/). We use NyARToolkit's marker recognition
system to identify certain QR codes and decrypt them using zebra crossing. Of
course, NyARToolkit is capable of much more than simply recognizing markers. We
wish to further develop this project by creating a system of augmented reality
using QR codes as markers themselves.

# Change Log
Change Date    |Commiter   |Description
--------|-----------|------------
05-22|yw|created repository, split task --> marker recognition = jake + yicheng, encryption/decryption = ari + joseph
05-27|jg|added links useful for encryption/decryption
05-28|ah|found/added zxing
05-29|jw|pattern recognition for corners, then link them together, preliminary code added. Doesn't quite work.
06-01|yw|added bash compile script
06-01|jw|redid corner patterns (use gray squares for location) and sorting, still doesn't quite work
06-03|ah|finished encryption, fully functional, moving on to GUI for encryption
06-03|jw|updated gray patterns used for sorting
06-05|yw|changed corner drawing code
06-05|jw|new coordinate system sorted by sign...
06-07|yw|finally fixed getting the correct qr image, moving on to decryption
06-10|jw|added new code for making the pattern to cooperate with processing
06-10|yw|new code for creating QR code finished
06-10|yw|now Driver renders the decrypted message on top of the detected qr code, also minor fix on add square
