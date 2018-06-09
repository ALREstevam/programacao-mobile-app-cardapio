
import json
import requests
from pprint import pprint

serverkey = "AAAA6Pfis6I:APA91bGyI5dYtWEUxibEPLeVqM2TpuCbTQ1oihFCC8mLnf5TAA0KMJ1flAE7lnIuz6CBizXAvcLVAqnYvAIEMunuVbEp26MnNjhVrDGvliwqDZBGnDJuDJaBjglfHPlw2M1yhM0TGs8i"
userToken = "f9DIIUKi2eg:APA91bHYAWqDX2RGks5Scqrahj08HJg_DtyDTucCAq3wKB8emD97naYzo1_11YV1BiblhvyFogIDVKNJ0GXNBt67sJEk_AMBxpR6Y8NF644ui3R6U2O7UKJ4O39Pn68uetsan53dI2BB"
url = 'https://fcm.googleapis.com/fcm/send'

body = {  
"data":{  
   "title":"mytitle",
   "body":"mybody",
   "url":"myurl"
},
"notification":{  
  "title":"My web app name",
  "body":"message",
  "content_available": "true"
},
 "to":userToken
 }


headers = {"Content-Type":"application/json",
        "Authorization": "key={}".format(serverkey)}

result = requests.post(url, data=json.dumps(body), headers=headers)

pprint(result)