# Card-Info-finder
 This is an application that checks information about your ATM card using the binlist API.

# Approach
I created five activities, four models and a network check provider class.

# Activities.
1. CardInformationDisplay Activity:  This is the activity that holds all the information from the Binlist API to show the Card brand, Card type, Bank Name and Country Name.

2. CardOptionSelection Activity: This is the activity for the selection of entering card number either by typing in the card number or using an OCR scan.

3. CardProcessor Activity: This is the activity for entering the card information directly from the keyboard.

4. OCRconfirm Activity: This is the activity for showing the card number you previously scanned.

5. SplashScreen Activity: This is the activity that shows the introduction screen for the app.

# Models.
1. CardBankInfo Class: This is a model to set and get the bank name.

2. CardCountryInfo Class: This is a model to set and get the Country name.

3. CardInfoPage Class: This is a model to set and get the Card brand, Card type, Bank name and Country name.

4. Urls Object: This is a model that holds the binlist Api Url link.

# Provider.
1. checkNetwork Object: This is the object that checks for internet connection.


# Dependencies.
1. fuel: To manage and handle HTTP requests.
2. Brain tree payments: To get the card number from the keyboard.
3. play services vision: To scan the card for a card number.
4. Gson: to handle the JSON requests and split necessary data.
5. theArtofCrop: To crop images from the camera URI
6. Dexter: To request runtime permission in the app.

