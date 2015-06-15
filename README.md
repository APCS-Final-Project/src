# src
Source Code for AR Promposer Project

Written by (in alphabetical order by last name):

Ari H.

Joseph G.

Jake W.

Yicheng W.

# Introduction
For our project, we created a QR code generator and scanner with the help of
[zebra crossing] (https://github.com/zxing/zxing) and [NyARToolkit]
(http://nyatla.jp/nyartoolkit/wp/). We used NyARToolkit's marker recognition
system to identify certain QR codes and decrypt them using zebra crossing. Of
course, NyARToolkit is capable of much more than simply recognizing markers. We
wished to further develop this project by creating a system of augmented reality
using modified QR codes (or AR codes) as markers themselves.

Currently, augmented reality markers must be hard coded into the recognition
system. So it is very had for users to create their own markers without
knowledge about programming. However, if we use these AR codes as general marker
templates, anyone can create augmented reality marker without knowledge of
programming.

The project has two main parts, the scanner/renderer of AR codes (developed
mostly by Yicheng and Jake) and the generator of AR codes (developped mostly by
Ari and Joseph).

## Future Development
However, currently, the AR codes are just plain QR codes so it is not yet
particularly useful. However, it is worth noting that QR code can have non-qr
section built in. We may put a picture inside the AR code and make it render the
word, which would be a useful tool for child learning.

We can/probably will also port the project to android so that it can be used on
a mobile platform.

# Instructions to Run

This project comes with all the libraries that it needs in the lib folder. To
compile the code, one simply runs "compile.sh."

Afterwards, to launch the GUI that creates QR codes, run "createQR.sh," then you
can either print out those codes or put them on a phone or any mobile device.

To use the scanner to scan and render QR codes, simply use "runScanner.sh". Have fun :D

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
06-11|ah/jg/jw|text field completed, moving onto combining everything
06-11|yw|box drawn on qr then draw text... life is good :D
06-11|yw|finished GUI... Looks wonderful...
06-13|yw|bug fix - gui/recognition of version 2-3 markers added
06-14|jw|bug fix - text positioning fixed with new marker file
06-14|yw|bug fix - new pattern gray square addition code fixed
06-14|yw|bug fix - updated ControlP5 library...

# Known Bugs
* [x] cannot make version 2/3 qr codes
* [x] text positioning can sometimes be orthogonally rotated
* [x] createQR breaks when the input exceeds the limit of the box
* [ ] cannot make qr codes for versions > 3
* [ ] text square doesn't cover the entire qr code for larger codes
* [ ] poor pattern recognition under poor lighting
* [ ] incompatible with multiple markers

# Sources Cited
- Built with [processing] (https://github.com/processing/processing)
- Encryption/decryption done with [zxing] (https://github.com/zxing/zxing)
- Pattern recognition with [NyARToolkit] (http://nyatla.jp/nyartoolkit/wp/)
- GUI built with [controlP5] (https://github.com/sojamo/controlp5)
