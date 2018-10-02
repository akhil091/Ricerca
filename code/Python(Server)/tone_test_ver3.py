#Team Members: Parush Gupta         
#              Rohit Sharma         
#              Akhil Chandail        
#              Vivek Singh Rathore  

#Problem 3   : Help me with my mood

#Description : Here we implement the backend logic.The backend is python flask app running on
#              IBM Cloud Foundry. Android app sends the Twitter userhandle via HTTP post request 
#              from which we fetch past 24hr tweets. We use IBM Tone Analyzer to analyze the emotional 
#              tones in the data and pick the tone with highest tone score in the overall doucment tones.
#              Then based on the emotion, a youtube song is picked randomly from a curated list and then
#              emotion along with the song url is sent as json back to the android app which plays it.

#dependencies
from watson_developer_cloud import ToneAnalyzerV3
from watson_developer_cloud import WatsonApiException
from flask import Flask,request,jsonify
from tweepy import OAuthHandler
from tweepy import TweepError
from tweepy import API
import datetime,time
import random
import ast
import os

# Twitter authentication
auth= OAuthHandler('9ZPpI5c3yLi8ZtSrzHAcIsppc'
,'W81EmOuPneZh39dwbK1nq0cjnc9iqpzKQqy4tu2pW8ruinsNbg')
auth.set_access_token('1044242601480675335-91v8RfyCaOJjjybdjGFYy3AhOe07Gk','DK9KGTSbvXj31DksRv3G8W09SA3eznwoYw7QJ5D1rKZge')
auth_api=API(auth)

# Watson authentication
tone_analyzer = ToneAnalyzerV3(
    version='2017-09-21',
    username='9d399029-fc96-4b05-8046-7cc971cff1be',
    password='posAk15mU3gS',
    url='https://gateway.watsonplatform.net/tone-analyzer/api'
)

#Port for the flask app
port = int(os.getenv('PORT', 8000))

#flask app
app=Flask(__name__)

#List to hold the past 24 hours tweets
tweet_data=[]

#Possible emotions
emotions=['joy','fear','sadness','anger']
 
#Handling the post request from the android app.
#Android app sends the userhandle via post request in json format   
@app.route('/',methods=['POST'])
def Sentimize():
    target=request.get_json('userhandle')['userhandle']
    try:
        user=auth_api.get_user(target)
    except TweepError as e:
        #userhandle doesn't exists
        return jsonify({'message':"Not valid"})   
    #This part fetches the past 24hr tweets
    page = 1
    flag = 1
    while True:
        tweets = auth_api.user_timeline(target, page = page,tweet_mode="extended")

        for tweet in tweets:
            if (datetime.datetime.now() - tweet.created_at).days < 1:
                tweet_data.append(tweet)
            else:
                flag=0
                break 
        if(flag==0):
            break
        page+=1
        time.sleep(500)
    #---------------------#
    
    analyzer_input=""
    for i in tweet_data:
     analyzer_input+=i.full_text    
    try:
        emotion_value=0
        #default emotion is joy, in case no emotion is detected
        emotion_name='joy'    
        #Document level tone analysis
        tone_analysis = tone_analyzer.tone({'text': analyzer_input},'application/json',False).get_result()
        cadidate_sentiments=tone_analysis['document_tone']['tones']
        #Picking the emotionl tone with highest score 
        for sentiment in cadidate_sentiments:
            if(sentiment['score']>emotion_value and (sentiment['tone_id'] in emotions)):
                emotion_name=sentiment['tone_id']
                emotion_value=sentiment['score']
        #Randomly selecting a youtube song from the detected emotion's list
        with open(emotion_name+'.txt', 'r') as f:
           songlist = ast.literal_eval(f.read())
        #Returning the emotion and song back to android app
        return jsonify({emotion_name:random.choice(songlist)})       
    except WatsonApiException as ex:
        print ("Method failed with status code " + str(ex.code) + ": " + ex.message) 

if __name__=='__main__':
    app.run(host='0.0.0.0',port=port)
