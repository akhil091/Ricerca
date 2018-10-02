# Tech Army
## Ricerca  

Logo: ![Alt](/logo.jpeg "Title")  

An Android app which will take the user twitter handle as input and recommend them something based on the sentiments received from
the analysis of the tweets that can be joy, anger, sadness and more.

## Working demo 

Here is the app download link [Ricerca](https://drive.google.com/open?id=1dX47271mP0Ze6Fd5bwkIMt80RcXE_ams). To run, you will need android 6.0 or more.  

Here is video [link](https://youtu.be/Nmaqvk_t7TQ) of working app.  


## Code  

The code directory contains all the requires code. It it divided into android and python.  

**Android code**  

Just import the code into android studio, build and run.  

**Python code**    

### Prerequisites for python code

Python version used is 3.5 . To install other dependencies run:  

**pip install -r requirements.txt** in python code directory.

### Running Flask server locally  

In python code directory run:  

**python tone_test_ver3.py**  

This will start flask local server for our python code.

### Running tests on server  

Just run the test_server.py script that makes a Http post request to our local flask server. If everything went fine you'll  

see the json response printed to the terminal.


### Installing

Download the app from link and then click on install anyway option

Enter the user twitter handle and then click on Enter button to get results.

## Running the tests


## Deployment

The python code is deployed using the IBM CloudFoundry platform, for more details [look](https://www.ibm.com/cloud/cloud-foundry)

## Built With

* [IBM Tone Analyzer](https://www.ibm.com/watson/services/tone-analyzer/) - For sentiment analysis
* [Tweepy](http://www.tweepy.org/) - All the tweet work
* [IBM Cloud Foundry](https://www.ibm.com/cloud/cloud-foundry) - Deployment
* [Python 3.5](https://www.python.org/downloads/) - The backend programming language
* [Flask](http://flask.pocoo.org/) - Web framework used
* [Json](https://developer.android.com/reference/org/json/JSONObject) - Used to parse the data from server 
* [Materials design](terial.google.com) - for android studio


## Authors
### Tech Army  
Here is the list of team members and their roles:
* **Rohit Sharma** -   Java code, Front end 
* **Akhil Chandail** -   Java code, Front end
* **Parush Gupta** -     Python code, Back end 
* **Vivek Singh Rahtore** -   Python code, Back end 

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

This project was created for IBM HackChallenge 2018.

