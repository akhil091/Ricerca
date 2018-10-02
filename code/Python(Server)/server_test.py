import requests

r=requests.post('http://localhost:8000',json={"userhandle":"narendramodi"})
print(r.text)
 