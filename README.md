# GarminAuth_Android
Garmin Authentication for android developement.

The above code lets you to authenticate user from mobile side and provide access to back end on user data resite on garmin server.
For Example: You can have users health data(step count, calories, distance, heart rate, etc), general information like Height, Weigh, gender etc.

Steps to follow
1. Register application on Gardmin dev portal
2. Provide all necessary information which requred and compulsary.
3. Copy 'Consumer Key', 'Consumer Secrete' and 'Redirect Url' and keep it in Constant File against respective variable

    const val CONSUMER_KEY = "update your consumer key from Garmin Developer Portal"
    
    const val CONSUMER_SECRET = "update your consumer secrete from Garmin Developer Portal"
    
    const val GARMIN_REDIRECT_URL = "update your redirect url from backend"

4. All done. Run app and you are good to go.

Happy Coding :)
