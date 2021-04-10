import hashlib
import urllib.request

for i in range(10_000):
    code = 'CH.Zermatt.' + str(i)
    hash = hashlib.md5(code.encode('utf-8')).hexdigest()
    url = 'https://ja3er.com/img/' + hash
    try:
        conn = urllib.request.urlopen(url)
        print("!!!!!!!!!! " + str(code) + " => " + url + " => " + str(conn.getcode()))
        quit()
    except urllib.error.HTTPError as e:
        print(str(code) + " => " + url + " => " + str(e), sep=' ')
